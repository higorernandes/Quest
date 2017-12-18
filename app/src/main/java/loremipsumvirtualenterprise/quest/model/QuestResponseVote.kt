package loremipsumvirtualenterprise.quest.model

/**
 * Created by bocato on 17/12/17.
 */
class QuestResponseVote {

    companion object Factory {
        fun create(): QuestResponseVote = QuestResponseVote()
        fun createFromMap(map: Map<String, Any?>): QuestResponseVote {

            val questResponseVote = QuestResponseVote.create()
            questResponseVote.userUID = map.get("userUID") as String?
            questResponseVote.value = map.get("value") as Int

            return questResponseVote
        }
    }

    fun asMap(): Map<String, Any?> {

        var map = HashMap<String, Any?>()
        map.put("userUID", this.userUID)
        map.put("value", this.value)

        return map
    }

    var userUID: String? = null
    var value: Int = 0 // -1, 0, 1
}