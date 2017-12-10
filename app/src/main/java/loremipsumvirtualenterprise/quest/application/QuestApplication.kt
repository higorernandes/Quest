package loremipsumvirtualenterprise.quest.application

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**
 * Created by root on 2017-12-10.
 */
class QuestApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        instance = this
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        var appContext: Context? = null
            private set
        @SuppressLint("StaticFieldLeak")
        @get:Synchronized
        var instance: QuestApplication? = null
            private set
    }
}