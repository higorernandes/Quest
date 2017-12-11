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
import kotlinx.android.synthetic.main.activity_quest_detail.*
import kotlinx.android.synthetic.main.toolbar_main.*
import loremipsumvirtualenterprise.quest.R
import loremipsumvirtualenterprise.quest.generic.QuestGenericActivity
import loremipsumvirtualenterprise.quest.model.QuestLike
import loremipsumvirtualenterprise.quest.model.QuestResponse

class QuestDetailActivity : QuestGenericActivity(), TextWatcher
{
    //region Attributes

    private val mResponses: ArrayList<String> = ArrayList<String>()
    private var mQuestResponsesArrayAdapter: QuestResponsesArrayAdapter? = null
    private var mQuestId : Int? = null

    //endregion

    //region Constructor

    companion object {
        private val QUEST_ID: String = "QUEST_ID"
        private val QUEST_TITLE: String = "QUEST_TITLE"
        private val QUEST_DESCRIPTION: String = "QUEST_DESCRIPTION"
        private val QUEST_DATE: String = "QUEST_DATE"
        private val QUEST_AUTHOR: String = "QUEST_AUTHOR"
        private val QUEST_LIKES: String = "QUEST_LIKES"
        private val QUEST_RESPONSES: String = "QUEST_RESPONSES"

        fun getActivityIntent(context: Context, questId: String, title: String, description: String, date: String, author:String, likes: ArrayList<QuestLike>?, responses: ArrayList<QuestResponse>?) : Intent {
            val intent = Intent(context, QuestDetailActivity::class.java)
            intent.putExtra(QUEST_ID, questId)
            intent.putExtra(QUEST_TITLE, title)
            intent.putExtra(QUEST_DESCRIPTION, description)
            intent.putExtra(QUEST_DATE, date)
            intent.putExtra(QUEST_AUTHOR, author)
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
            //TODO: call service to save comment
        }
    }

    private fun getExtras() {
        mQuestId = intent.getIntExtra(QUEST_ID, 0)
        questDetailTitleTextView.text = intent.getStringExtra(QUEST_TITLE)
        questDetailDescriptionTextView.text = intent.getStringExtra(QUEST_DESCRIPTION)
        questDetailDateTextView.text = intent.getStringExtra(QUEST_DATE)
        questDetailAuthorTextView.setText(resources.getString(R.string.generic_username)
                .replace("{username}", intent.getStringExtra(QUEST_AUTHOR)))

        val likesCounter = intent.getParcelableArrayListExtra<QuestLike>(QUEST_LIKES)
        val responsesCounter = intent.getParcelableArrayListExtra<QuestLike>(QUEST_RESPONSES)
        questLikeResponsesCountTextView.text = resources.getString(R.string.board_item_responses_text)
                .replace("{likes}", if (likesCounter != null) likesCounter.toString() else "0")
                .replace("{responses}", if (responsesCounter != null) responsesCounter.toString() else "0")
    }

    private fun loadQuest() {
        //TODO: load quest using the mQuestId variable.

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
