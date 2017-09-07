package loremipsumvirtualenterprise.quest.account

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_onboarding.*
import loremipsumvirtualenterprise.quest.R

class OnboardingActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        configureViews()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.onboardingLoginButton -> {
                val intent : Intent = LoginActivity.getActivityIntent(this)
                startActivity(intent)
            }
            R.id.onboardingRegisterButton -> {
                val intent : Intent = RegisterActivity.getActivityIntent(this)
                startActivity(intent)
            }
        }
    }

    fun configureViews() {
        onboardingLoginButton.setOnClickListener(this)
        onboardingRegisterButton.setOnClickListener(this)
    }
}
