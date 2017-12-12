package loremipsumvirtualenterprise.quest.model

import com.google.firebase.database.DataSnapshot
import java.util.HashMap

/**
 * Created by bocato on 11/12/17.
 */
class QuestUser {

    companion object Factory {
        fun create(): QuestUser = QuestUser()
        fun createFromDataSnapshot(snapshot: DataSnapshot): QuestUser {

            //get current data in a map
            val map = snapshot.value as HashMap<*, *>

            // create object
            val questUser = QuestUser.create()

            //key will return Firebase ID
            questUser.id = snapshot.key
            questUser.uid = map.get("uid") as String?
            questUser.name = map.get("name") as String?
            questUser.email = map.get("email") as String?

            return questUser
        }
    }

    var id: String? = null
    var uid: String? = null
    var name: String? = null
    var email: String? = null

}