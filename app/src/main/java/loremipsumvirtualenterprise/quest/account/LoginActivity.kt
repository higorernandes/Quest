package loremipsumvirtualenterprise.quest.account

import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_login.*
import loremipsumvirtualenterprise.quest.R
import loremipsumvirtualenterprise.quest.main.MainActivity

class LoginActivity : AppCompatActivity(), View.OnClickListener
{



    //region Companion Object
    companion object {
        fun getActivityIntent(context : Context) : Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }

    //endregion

    //region Overridden Methods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initViews()
        setUpToolbar()
        changeStatusBarColor()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.loginLoginButton -> {
                val intent : Intent = MainActivity.getActivityIntent(this)
                startActivity(intent)
            }
            R.id.loginForgotPasswordButton -> {
                // TODO: handle "Forgot Password"
            }
            R.id.loginNoAccountButton -> {
                val intent : Intent = RegisterActivity.getActivityIntent(this)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
            }
        }
        return true
    }

    //endregion

    //region Private Methods

    private fun initViews() {
        loginPasswordEditText.typeface = ResourcesCompat.getFont(this, R.font.avenir_next_regular)

        loginLoginButton.setOnClickListener(this)
        loginForgotPasswordButton.setOnClickListener(this)
        loginNoAccountButton.setOnClickListener(this)
    }

    private fun setUpToolbar() {
        setSupportActionBar(mainToolbar as Toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    private fun changeStatusBarColor() {
        val window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorBackground)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decor = getWindow().decorView
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    //endregion
}
