package loremipsumvirtualenterprise.quest.model

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import loremipsumvirtualenterprise.quest.util.FirebaseConstants
import java.util.*

/**
 * Created by root on 2017-11-27.
 */

class Quest {

    companion object Factory {
        fun create(): Quest = Quest()
    }

    var id: String? = null
    var title: String? = null
    var description: String? = null
    var publishedAt: String? = null
    var publisherUID: String? = null
    var likes: ArrayList<QuestLike>? = null
    var responses: ArrayList<QuestResponse>? = null

    // Move somewhere else?
    fun getAuthor() : QuestUser? {
        if (this.publisherUID != null) {
            return FirebaseDatabase.getInstance().reference?.child(FirebaseConstants.FIREBASE_QUESTS_NODE)?.child(this.publisherUID!!) as QuestUser?
        } else { return null }
    }

}