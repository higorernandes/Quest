package loremipsumvirtualenterprise.quest.main.board

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_settings.*
import loremipsumvirtualenterprise.quest.R
import loremipsumvirtualenterprise.quest.generic.QuestGenericActivity

class SearchActivity : QuestGenericActivity()
{
    //region Constructor

    companion object {
        fun getActivityIntent(context: Context) : Intent {
            return Intent(context, SearchActivity::class.java)
        }
    }

    //endregion

    //region Overridden Methods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setUpToolbar(searchToolbar as Toolbar?, resources.getString(R.string.settings_toolbar_title))
        changeStatusBarColor()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return super.onOptionsItemSelected(item)
    }

    //endregion
}
