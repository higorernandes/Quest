package loremipsumvirtualenterprise.quest.main

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import loremipsumvirtualenterprise.quest.R

class MainActivity : AppCompatActivity() {

    companion object {
        fun getActivityIntent(context : Context) : Intent {
            val intent : Intent = Intent(context, MainActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}