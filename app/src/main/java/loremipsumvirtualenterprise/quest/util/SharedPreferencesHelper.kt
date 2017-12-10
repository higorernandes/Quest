package loremipsumvirtualenterprise.quest.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

/**
 * Created by root on 2017-12-10.
 */
class SharedPreferencesHelper
{
    //region Companion Object

    companion object {

        val USER_LOGGED : String = "USER_LOGGED"
        val USER_ID : String = "USER_ID"
        val USER_NAME : String = "USER_NAME"
        val USER_EMAIL : String = "USER_EMAIL"

        fun saveStringInSharedPreferences(context: Context, key: String, value: String) {
            val sharedPreferences : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            val sharedPreferencesEditor : SharedPreferences.Editor = sharedPreferences.edit();
            sharedPreferencesEditor.putString(key, value)
            sharedPreferencesEditor.commit()
        }

        fun getStringFromSharedPreferences(context: Context, key: String) : String? {
            val sharedPreferences : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return sharedPreferences.getString(key, null)
        }

        fun removeFromSharedPreferences(context: Context, key: String) {
            val sharedPreferences : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            if (sharedPreferences.contains(key)) {
                val sharedPreferencesEditor : SharedPreferences.Editor = sharedPreferences.edit();
                sharedPreferencesEditor.remove(key)
                sharedPreferencesEditor.commit()
            }
        }
    }

    //endregion

}