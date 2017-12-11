package loremipsumvirtualenterprise.quest.model

/**
 * Created by bocato on 11/12/17.
 */
class QuestUser {

    companion object Factory {
        fun create(): QuestUser = QuestUser()
    }

    var uid: String? = null
    var name: String? = null
    var email: String? = null

}