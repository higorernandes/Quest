package loremipsumvirtualenterprise.quest.model

import com.google.firebase.auth.FirebaseUser
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
    var publishedBy: FirebaseUser? = null
    var likes: Array<QuestLike>? = null
    var responses: Array<QuestResponse>? = null

}