package loremipsumvirtualenterprise.quest.account

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_login.*
import loremipsumvirtualenterprise.quest.R
import loremipsumvirtualenterprise.quest.main.MainActivity

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        fun getActivityIntent(context : Context) : Intent {
            val intent : Intent = Intent(context, LoginActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        configureViews()
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

    fun configureViews() {
        loginLoginButton.setOnClickListener(this)
        loginForgotPasswordButton.setOnClickListener(this)
        loginNoAccountButton.setOnClickListener(this)
    }
}
