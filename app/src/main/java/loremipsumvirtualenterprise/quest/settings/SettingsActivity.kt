package loremipsumvirtualenterprise.quest.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import kotlinx.android.synthetic.main.activity_settings.*
import loremipsumvirtualenterprise.quest.R
import loremipsumvirtualenterprise.quest.generic.QuestGenericActivity

class SettingsActivity : QuestGenericActivity()
{
    //region Companion Object

    companion object {
        fun getActivityIntent(context : Context) : Intent {
            return Intent(context, SettingsActivity::class.java)
        }
    }

    //endregion

    //region Overridden Methods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        changeStatusBarColor()
        setUpToolbar(settingsToolbar as Toolbar?, resources.getString(R.string.settings_toolbar_title))
        initViews()
    }

    //endregion

    //region Private Methods

    private fun initViews() {
        settingsLogoutLinearLayout.setOnClickListener {
            //TODO: perform logout
        }
    }

    //endregion
}