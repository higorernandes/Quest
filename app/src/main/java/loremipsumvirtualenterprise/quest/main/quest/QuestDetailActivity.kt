package loremipsumvirtualenterprise.quest.main.quest

import android.app.ActionBar
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_quest_detail.*
import kotlinx.android.synthetic.main.toolbar_detail.*
import loremipsumvirtualenterprise.quest.R
import loremipsumvirtualenterprise.quest.generic.QuestGenericActivity
import loremipsumvirtualenterprise.quest.model.*
import loremipsumvirtualenterprise.quest.util.FirebaseDatabaseUtil
import java.text.SimpleDateFormat
import java.util.*
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.collections.ArrayList
import kotlinx.android.synthetic.main.toolbar_detail.*
import kotlinx.android.synthetic.main.toolbar_detail.view.*
import loremipsumvirtualenterprise.quest.util.DateHelper


class QuestDetailActivity : QuestGenericActivity(), TextWatcher
{
    //region Attributes

    private var mResponses: ArrayList<QuestResponse> = ArrayList<QuestResponse>()
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
        getExtrasAndLoadQuestData()
        initViews()
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

    private fun configureRecicleView() {
        mQuestResponsesArrayAdapter = QuestResponsesArrayAdapter(this, mResponses!!,
                downvoteClickListener = { holder: QuestResponsesArrayAdapter.Holder?, position: Int ->
                    updateVotesForHolder(holder, position, -1)
                },
                upvoteClickListener = {  holder: QuestResponsesArrayAdapter.Holder?, position: Int ->
                    updateVotesForHolder(holder, position, 1)
                })
        questResponsesRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        questResponsesRecyclerView.itemAnimator = DefaultItemAnimator()
        questResponsesRecyclerView.adapter = mQuestResponsesArrayAdapter
    }

    private fun updateVotesForHolder(holder: QuestResponsesArrayAdapter.Holder?, position: Int, value: Int) {

        val questResponse = mQuest?.responses?.get(position)
        if (questResponse != null){

            if (questResponse.votes == null) {
                questResponse.votes = ArrayList<QuestResponseVote>()
            }

            var questResponseVote: QuestResponseVote? = questResponse.votes?.find { it.userUID == FirebaseAuth.getInstance().currentUser!!.uid!! }
            if (questResponseVote == null) {
                questResponseVote = QuestResponseVote.create()
                questResponseVote.userUID = FirebaseAuth.getInstance().currentUser?.uid
            }
            questResponseVote?.value = value

            if (!questResponse.votes!!.contains(questResponseVote)) {
                questResponse.votes!!.add(questResponseVote!!)
            }

            val questAsMap = mQuest?.asMap()

            FirebaseDatabaseUtil.questsNode?.child(mQuest?.id)?.updateChildren(questAsMap)

            mResponses = mQuest?.responses!!
            mQuestResponsesArrayAdapter?.notifyDataSetChanged()
        }

    }

    private fun initViews() {
        questDetailResponseEditText.addTextChangedListener(this)
        questDetailSendResponseButton.setOnClickListener {
            if (questDetailResponseEditText.text != null) {

                var questResponse = QuestResponse.create()
                questResponse.publisherUid = FirebaseAuth.getInstance().currentUser?.uid
                questResponse.text = questDetailResponseEditText.text.toString()
                questResponse.publishedAt = currentDateTime()
                questResponse.votes = ArrayList<QuestResponseVote>()

                if(mQuest?.responses == null){
                    mQuest?.responses = ArrayList<QuestResponse>()
                }

                mQuest?.responses?.add(questResponse)

                val questAsMap = mQuest?.asMap()

                FirebaseDatabaseUtil.questsNode?.child(mQuest?.id)?.updateChildren(questAsMap)

                questDetailResponseEditText.text = null

                mResponses = mQuest?.responses!!
                mQuestResponsesArrayAdapter?.notifyDataSetChanged()
            }

        }
        mainToolbarActionButton.visibility = View.VISIBLE
        mainToolbarSecondActionButton.visibility = View.VISIBLE
        mainToolbarActionIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_trash))
        mainToolbarSecondActionIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_edit))

        //Configuring toolbar icons
        val layoutParams = Toolbar.LayoutParams(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT)
        layoutParams.marginEnd = 0
        layoutParams.rightMargin = 0
        toolbarDetailContainerRelativeLayout.layoutParams = layoutParams

        val buttonLayoutParams  = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT)
        buttonLayoutParams.marginStart = 0
        buttonLayoutParams.marginEnd = 150
        mainToolbarTitleTextView.layoutParams = buttonLayoutParams

        mainToolbarActionButton.setOnClickListener { confirmDeleteQuest() }
        mainToolbarSecondActionButton.setOnClickListener {
            if (mQuest != null) {
                startActivity(CreateQuestActivity.getActivityIntentForUpdate(this, mQuest!!.id!!))
            }
        }
    }

    private fun confirmDeleteQuest() {
        val publisherUID = mQuest?.publisherUID
        val currentUserUID = FirebaseAuth.getInstance().currentUser?.uid
        if (publisherUID != null && currentUserUID != null && currentUserUID == publisherUID) {
            val alertDialog: AlertDialog = AlertDialog.Builder(this).setMessage(resources.getString(R.string.delete_delete_confirmation)).create()
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, resources.getString(R.string.generic_yes)) { _, _ -> deleteQuest() }
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, resources.getString(R.string.generic_no)) { dialog, _ -> dialog.dismiss() }

            val text = alertDialog.window.findViewById<TextView>(android.R.id.message)
            val button1 = alertDialog.findViewById<TextView>(android.R.id.button1)
            alertDialog.show()

            text?.typeface = QuestGenericActivity.sAvenirNextRegular
            button1?.typeface = QuestGenericActivity.sAvenirNextRegular
        } else {
            Toast.makeText(this, "Você não pode deletar uma quest de outro usuário!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToEditQuest() {

    }

    private fun deleteQuest() {
        FirebaseDatabaseUtil.questsNode?.child(mQuest?.id)?.removeValue()
        finish()
    }

    // Helpers
    private fun currentDateTime(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault())
        val date : Date = Date()
        return dateFormat.format(date)
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
        if (mQuest != null) {
            mQuest?.id = snapshot.key

            //  configure view with quest data
            questDetailTitleTextView.text = mQuest?.title
            questDetailDescriptionTextView.text = mQuest?.description
            questDetailDateTextView.text = DateHelper.formatDate(mQuest?.publishedAt)
            loadAuthor()
            questLikeResponsesCountTextView.text = resources.getString(R.string.board_item_responses_text)
                    .replace("{likes}", if (mQuest?.likes != null) mQuest?.likes?.size.toString() else "0")
                    .replace("{responses}", if (mQuest?.responses != null) mQuest?.responses?.size.toString() else "0")

            mResponses = mQuest?.responses ?: ArrayList<QuestResponse>() //mQuest?.responsesAsStringArray()!!

            configureRecicleView()
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
