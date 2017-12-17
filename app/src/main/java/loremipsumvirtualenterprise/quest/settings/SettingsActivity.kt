package loremipsumvirtualenterprise.quest.settings

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_settings.*
import loremipsumvirtualenterprise.quest.R
import loremipsumvirtualenterprise.quest.account.LoginActivity
import loremipsumvirtualenterprise.quest.generic.GenericWebViewActivity
import loremipsumvirtualenterprise.quest.generic.QuestGenericActivity
import loremipsumvirtualenterprise.quest.main.MainActivity
import loremipsumvirtualenterprise.quest.util.SharedPreferencesHelper

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
        settingsChangePasswordLinearLayout.setOnClickListener { startActivity(ChangePasswordActivity.getActivityIntent(this)) }
        settingsPrivacyPolicyLinearLayout.setOnClickListener { startActivity(GenericWebViewActivity.getActivityIntent(this, "https://www.google.com.br/intl/en_br/policies/privacy/?fg=1", GenericWebViewActivity.WebViewType.WEB_VIEW_PRIVACY_POLICY))}
        settingsAboutLinearLayout.setOnClickListener { startActivity(GenericWebViewActivity.getActivityIntent(this, "https://www.google.com.br/intl/en_br/about/?utm_source=google.com&utm_medium=referral&utm_campaign=hp-footer&fg=1", GenericWebViewActivity.WebViewType.WEB_VIEW_ABOUT))}
        settingsLogoutLinearLayout.setOnClickListener { confirmLogout() }
    }

    // Actions
    private fun confirmLogout() {
        val alertDialog: AlertDialog = AlertDialog.Builder(this).setMessage(resources.getString(R.string.logout_confirmation)).create()
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, resources.getString(R.string.generic_yes)) { _, _ -> doLogout() }
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, resources.getString(R.string.generic_no)) { dialog, _ -> dialog.dismiss() }

        val text = alertDialog.window.findViewById<TextView>(android.R.id.message)
        val button1 = alertDialog.findViewById<TextView>(android.R.id.button1)
        alertDialog.show()

        text?.typeface = QuestGenericActivity.sAvenirNextRegular
        button1?.typeface = QuestGenericActivity.sAvenirNextRegular
    }

    private fun doLogout() {
        firebaseAuth!!.signOut()
        val loginActivityIntent : Intent = LoginActivity.getActivityIntent(this)
        MainActivity.instance?.finish()
        finish()
        startActivity(loginActivityIntent)
        SharedPreferencesHelper.removeFromSharedPreferences(this, SharedPreferencesHelper.USER_LOGGED)
    }

}