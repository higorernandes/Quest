package loremipsumvirtualenterprise.quest.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_settings.*
import loremipsumvirtualenterprise.quest.R
import loremipsumvirtualenterprise.quest.account.LoginActivity
import loremipsumvirtualenterprise.quest.generic.QuestGenericActivity

class SettingsActivity : QuestGenericActivity()
{

    //Firebase references
    private var firebaseAuth: FirebaseAuth? = null

    // Companion Object
    companion object {
        fun getActivityIntent(context : Context) : Intent {
            return Intent(context, SettingsActivity::class.java)
        }
    }

    // Initialization
    private fun initializeVariables() {
        firebaseAuth = FirebaseAuth.getInstance()
    }

    // Lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        changeStatusBarColor()
        setUpToolbar(settingsToolbar as Toolbar?, resources.getString(R.string.settings_toolbar_title))
        initViews()
    }

    private fun initViews() {
        initializeVariables()
        configureListeners()
    }

    // Listeners
    private fun configureListeners() {
        settingsLogoutLinearLayout.setOnClickListener { doLogout() }
    }

    // Actions
    private fun doLogout() {
        firebaseAuth!!.signOut()
        val loginActivityIntent : Intent = LoginActivity.getActivityIntent(this)
        loginActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(loginActivityIntent)
    }

}