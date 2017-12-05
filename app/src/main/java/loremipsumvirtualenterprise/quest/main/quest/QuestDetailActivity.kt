package loremipsumvirtualenterprise.quest.main.quest

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_quest_detail.*
import kotlinx.android.synthetic.main.toolbar_main.*
import loremipsumvirtualenterprise.quest.R
import loremipsumvirtualenterprise.quest.generic.QuestGenericActivity

class QuestDetailActivity : QuestGenericActivity()
{
    //region Attributes

    private var mQuestId : Int? = null

    //endregion

    //region Constructor

    companion object {
        private val QUEST_ID: String = "QUEST_ID"

        fun getActivityIntent(context: Context, questId: Int) : Intent {
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

        getExtras()
        changeStatusBarColor()
        setUpToolbar(questDetailToolbar as Toolbar, resources.getString(R.string.detail_title))
        loadQuest()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        onBackPressed()
        return true
    }

    //endregion

    //region Private Methods

    private fun getExtras() {
        mQuestId = intent.getIntExtra(QUEST_ID, 0)
    }

    private fun loadQuest() {
        //TODO: load quest using the mQuestId variable.
    }

    //endregion
}
