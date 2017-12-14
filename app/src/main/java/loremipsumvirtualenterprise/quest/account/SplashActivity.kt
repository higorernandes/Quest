package loremipsumvirtualenterprise.quest.account

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import loremipsumvirtualenterprise.quest.R
import loremipsumvirtualenterprise.quest.main.MainActivity
import loremipsumvirtualenterprise.quest.util.SharedPreferencesHelper
import java.util.*

class SplashActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)

        Timer().schedule(object : TimerTask() {
            override fun run() {
                navigateToProperScreen()
            }
        }, 3000)
    }

    fun navigateToProperScreen() {
        if (SharedPreferencesHelper.getStringFromSharedPreferences(this, SharedPreferencesHelper.USER_LOGGED) != null && SharedPreferencesHelper.getStringFromSharedPreferences(this, SharedPreferencesHelper.USER_LOGGED).equals("YES")) {
            startActivity(LoginActivity.getActivityIntent(this))
        } else {
            startActivity(OnboardingActivity.getActivityIntent(this))
        }
        finish()
    }
}
