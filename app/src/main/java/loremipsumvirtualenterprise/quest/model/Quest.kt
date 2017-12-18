package loremipsumvirtualenterprise.quest.model

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import loremipsumvirtualenterprise.quest.util.FirebaseConstants
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/**
 * Created by root on 2017-11-27.
 */

class Quest {

    companion object Factory {
        fun create(): Quest = Quest()
        fun createFromDataSnapshot(snapshot: DataSnapshot): Quest? {
            //get current data in a map
            val map = snapshot.value as HashMap<*, *>?
            if (map != null) {
                val questItem = Quest.create()
                //key will return Firebase ID
                questItem.id = snapshot.key
                questItem.title = map.get("title") as String?
                questItem.description = map.get("description") as String?
                questItem.publishedAt = map.get("publishedAt") as String?
                questItem.publisherUID = map.get("publisherUID") as String?
                questItem.likes = map.get("likes") as ArrayList<QuestLike>?

                questItem.responses = ArrayList<QuestResponse>()
                val responseItems = map.get("responses") as ArrayList<Map<String, Any>>?
                if (responseItems != null) {
                    for (responseItem in responseItems) {
                        questItem!!.responses!!.add(QuestResponse.createFromMap(responseItem))
                    }
                }

                return questItem
            }

            return null
        }
    }

    var id: String? = null
    var title: String? = null
    var description: String? = null
    var publishedAt: String? = null
    var publisherUID: String? = null
    var likes: ArrayList<QuestLike>? = null
    var responses: ArrayList<QuestResponse>? = null

    fun asMap(): Map<String, Any?> {

        var map = HashMap<String, Any?>()
        map.put("title", this.title)
        map.put("description", this.description)
        map.put("publishedAt", this.publishedAt)
        map.put("publisherUID", this.publisherUID)
        map.put("likes", this.likes)
        map.put("responses", this.responses)

        return map
    }

}