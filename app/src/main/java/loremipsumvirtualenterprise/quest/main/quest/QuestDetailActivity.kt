package loremipsumvirtualenterprise.quest.main.quest

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_quest_detail.*
import kotlinx.android.synthetic.main.toolbar_main.*
import loremipsumvirtualenterprise.quest.R
import loremipsumvirtualenterprise.quest.generic.QuestGenericActivity
import loremipsumvirtualenterprise.quest.model.Quest
import loremipsumvirtualenterprise.quest.model.QuestLike
import loremipsumvirtualenterprise.quest.model.QuestResponse
import loremipsumvirtualenterprise.quest.model.QuestUser
import loremipsumvirtualenterprise.quest.util.FirebaseDatabaseUtil

class QuestDetailActivity : QuestGenericActivity(), TextWatcher
{
    //region Attributes

    private var mResponses: ArrayList<String> = ArrayList<String>()
    private var mQuestResponsesArrayAdapter: QuestResponsesArrayAdapter? = null
    private var mQuest : Quest? = null

    //endregion

    //region Constructor

    companion object {
        private val QUEST_ID: String = "QUEST_ID"
        fun getActivityIntent(context: Context, questId: String) : Intent {
            val intent = Intent(context, QuestDetailActivity::class.java)
            intent.putExtra(QUEST_ID, questId)
            return intent
        }
    }

    //endregion

    //region Overridden Methods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quest_detail)
        changeStatusBarColor()
        setUpToolbar(questDetailToolbar as Toolbar, resources.getString(R.string.detail_title))
        initViews()
        getExtrasAndLoadQuestData()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        onBackPressed()
        return true
    }

    override fun afterTextChanged(p0: Editable?) { }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        if (p0?.trim()?.length!! > 0) {
            questDetailSendResponseButton.isEnabled = true
            questDetailSendResponseButton.backgroundTintList = ContextCompat.getColorStateList(this, R.color.colorAccent)
        } else {
            questDetailSendResponseButton.isEnabled = false
            questDetailSendResponseButton.backgroundTintList = ContextCompat.getColorStateList(this, R.color.colorDivider)
        }
    }

    //endregion

    //region Private Methods

    private fun initViews() {
        mQuestResponsesArrayAdapter = QuestResponsesArrayAdapter(this, mResponses,
                downvoteClickListener = {
                    //TODO: call service to register downvoting
                }, upvoteClickListener = {
                    //TODO: call service to register upvoting
        })
        questResponsesRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        questResponsesRecyclerView.itemAnimator = DefaultItemAnimator()
        questResponsesRecyclerView.adapter = mQuestResponsesArrayAdapter
        questDetailResponseEditText.addTextChangedListener(this)
        questDetailSendResponseButton.setOnClickListener {
            if (questDetailResponseEditText.text != null) {

                var questResponse = QuestResponse.create()
                questResponse.userUid = FirebaseAuth.getInstance().currentUser?.uid
                questResponse.text = questDetailResponseEditText.text.toString()

                if(mQuest?.responses == null){
                    mQuest?.responses = ArrayList<QuestResponse>()
                }

                mQuest?.responses?.add(questResponse)

                val questAsMap = mQuest?.asMap()

                FirebaseDatabaseUtil.questsNode?.child(mQuest?.id)?.updateChildren(questAsMap)

                questDetailResponseEditText.text = null
            }

        }
    }

    // Listeners
    private fun configureListenerForQuestWithID(questId: String) {
        if (FirebaseDatabaseUtil.questsNode != null) {
            FirebaseDatabaseUtil.questsNode.child(questId).addValueEventListener(questItemListener)
        }
    }

    var questItemListener: ValueEventListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            loadQuestFromDataSnapshot(dataSnapshot)
        }

        override fun onCancelled(databaseError: DatabaseError) {}
    }

    private fun getExtrasAndLoadQuestData() {
        val questID = intent.getStringExtra(QUEST_ID)
        configureListenerForQuestWithID(questID)
    }

    private fun loadQuestFromDataSnapshot(snapshot: DataSnapshot) {

        mQuest = Quest.createFromDataSnapshot(snapshot)
        mQuest?.id = snapshot.key

        //  configure view with quest data
        questDetailTitleTextView.text = mQuest?.title
        questDetailDescriptionTextView.text = mQuest?.description
        questDetailDateTextView.text = mQuest?.publishedAt
        loadAuthor()
        questLikeResponsesCountTextView.text = resources.getString(R.string.board_item_responses_text)
                .replace("{likes}", if (mQuest?.likes != null) mQuest?.likes?.size.toString() else "0")
                .replace("{responses}", if (mQuest?.responses != null) mQuest?.responses?.size.toString() else "0")

        mResponses = mQuest?.responsesAsStringArray()!!

        mQuestResponsesArrayAdapter?.notifyDataSetChanged()

        if (mResponses.size == 0) {
            questDetailNoResponsesTextView.visibility = View.VISIBLE
            questResponsesRecyclerView.visibility = View.GONE
        } else {
            questDetailNoResponsesTextView.visibility = View.GONE
            questResponsesRecyclerView.visibility = View.VISIBLE
        }
        updateResponsesCounterText()
    }

    // Helpers
    fun loadAuthor() {

        val publisherDatabaseReference = FirebaseDatabaseUtil.usersNode?.child(mQuest?.publisherUID!!)

        publisherDatabaseReference?.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.value != null) {
                    val questUser = QuestUser.createFromDataSnapshot(snapshot)
                    questDetailAuthorEditText.setText(resources.getString(R.string.generic_username).replace("{username}", questUser.name.toString()))
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })

    }

    private fun updateResponsesCounterText() {
        questLikeResponsesCountTextView.text = resources.getString(R.string.board_item_responses_text)
                .replace("{likes}", "0")
                .replace("{responses}", mResponses.size.toString())
    }

    //endregion
}
