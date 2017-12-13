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
import kotlinx.android.synthetic.main.activity_quest_detail.*
import kotlinx.android.synthetic.main.toolbar_main.*
import loremipsumvirtualenterprise.quest.R
import loremipsumvirtualenterprise.quest.generic.QuestGenericActivity
import loremipsumvirtualenterprise.quest.model.Quest
import loremipsumvirtualenterprise.quest.model.QuestLike
import loremipsumvirtualenterprise.quest.model.QuestResponse
import loremipsumvirtualenterprise.quest.util.FirebaseDatabaseUtil

class QuestDetailActivity : QuestGenericActivity(), TextWatcher
{
    //region Attributes

    private val mResponses: ArrayList<String> = ArrayList<String>()
    private var mQuestResponsesArrayAdapter: QuestResponsesArrayAdapter? = null
    private var mQuest : Quest? = null

    //endregion

    //region Constructor

    companion object {
        private val QUEST_ID: String = "QUEST_ID"
        private val QUEST_TITLE: String = "QUEST_TITLE"
        private val QUEST_DESCRIPTION: String = "QUEST_DESCRIPTION"
        private val QUEST_DATE: String = "QUEST_DATE"
        private val QUEST_AUTHOR: String = "QUEST_AUTHOR"
        private val QUEST_AUTHOR_ID: String = "QUEST_AUTHOR_ID"
        private val QUEST_LIKES: String = "QUEST_LIKES"
        private val QUEST_RESPONSES: String = "QUEST_RESPONSES"

        fun getActivityIntent(context: Context, questId: String, title: String, description: String, date: String, /*author:String,*/ authorId:String, likes: ArrayList<QuestLike>?, responses: ArrayList<QuestResponse>?) : Intent {
            val intent = Intent(context, QuestDetailActivity::class.java)
            intent.putExtra(QUEST_ID, questId)
            intent.putExtra(QUEST_TITLE, title)
            intent.putExtra(QUEST_DESCRIPTION, description)
            intent.putExtra(QUEST_DATE, date)
//            intent.putExtra(QUEST_AUTHOR, author)
            intent.putExtra(QUEST_AUTHOR_ID, authorId)
            intent.putParcelableArrayListExtra(QUEST_LIKES, likes)
            intent.putParcelableArrayListExtra(QUEST_RESPONSES, responses)

            return intent
        }
    }

    //endregion

    //region Overridden Methods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quest_detail)

        getExtras()
        changeStatusBarColor()
        setUpToolbar(questDetailToolbar as Toolbar, resources.getString(R.string.detail_title))
        initViews()
        loadQuest()
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
            }

        }
    }

    private fun getExtras() {
        mQuest = Quest.create()
        mQuest!!.id = intent.getStringExtra(QUEST_ID)
        mQuest!!.title = intent.getStringExtra(QUEST_TITLE)
        mQuest!!.description = intent.getStringExtra(QUEST_DESCRIPTION)
        mQuest!!.publishedAt = intent.getStringExtra(QUEST_DATE)
        mQuest!!.publisherUID = intent.getStringExtra(QUEST_AUTHOR_ID)
        val likesCounter = intent.getParcelableArrayListExtra<QuestLike>(QUEST_LIKES)
        val responsesCounter = intent.getParcelableArrayListExtra<QuestLike>(QUEST_RESPONSES)
        questLikeResponsesCountTextView.text = resources.getString(R.string.board_item_responses_text)
                .replace("{likes}", if (likesCounter != null) likesCounter.toString() else "0")
                .replace("{responses}", if (responsesCounter != null) responsesCounter.toString() else "0")
    }

    private fun loadQuest() {

        //  configure view with quest data
        questDetailTitleTextView.text = mQuest?.title
        questDetailDescriptionTextView.text = mQuest?.description
        questDetailDateTextView.text = mQuest?.publishedAt
//        questDetailAuthorTextView.setText(resources.getString(R.string.generic_username).replace("{username}", intent.getStringExtra(QUEST_AUTHOR)))

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

    private fun updateResponsesCounterText() {
        questLikeResponsesCountTextView.text = resources.getString(R.string.board_item_responses_text)
                .replace("{likes}", "0")
                .replace("{responses}", mResponses.size.toString())
    }

    //endregion
}
