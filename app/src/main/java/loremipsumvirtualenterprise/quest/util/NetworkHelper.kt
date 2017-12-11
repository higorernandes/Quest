package loremipsumvirtualenterprise.quest.util

import android.content.Context
import android.net.ConnectivityManager
import loremipsumvirtualenterprise.quest.application.QuestApplication


/**
 * Created by root on 2017-12-10.
 */
class NetworkHelper {
    /**
     * Checks whether there's an active internet connection at the current moment.
     * @return TRUE if there's internet, FALSE if you're offline.
     */
    companion object {
        val isConnected: Boolean
            get() {
                val connectivityManager  = QuestApplication.appContext?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
                if (connectivityManager != null) {
                    val activeNetwork = connectivityManager.activeNetworkInfo
                    return activeNetwork != null && activeNetwork.isConnectedOrConnecting
                }
                return false
            }
    }
}
