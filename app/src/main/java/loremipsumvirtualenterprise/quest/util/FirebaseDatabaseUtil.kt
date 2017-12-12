package loremipsumvirtualenterprise.quest.util

import com.google.firebase.database.FirebaseDatabase

/**
 * Created by bocato on 12/12/17.
 */
object FirebaseDatabaseUtil {

    val questsNode = FirebaseDatabase.getInstance().reference?.child(FirebaseConstants.FIREBASE_QUESTS_NODE)
    val usersNode = FirebaseDatabase.getInstance().reference?.child(FirebaseConstants.FIREBASE_USERS_NODE)

}