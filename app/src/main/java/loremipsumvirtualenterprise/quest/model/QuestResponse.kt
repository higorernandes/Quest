package loremipsumvirtualenterprise.quest.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by bocato on 09/12/17.
 */
class QuestResponse {

    companion object Factory {
        fun create(): QuestResponse = QuestResponse()
        fun createFromMap(map: Map<String, Any?>): QuestResponse {

            val questResponse = QuestResponse.create()

            questResponse.publisherUid = map.get("publisherUid") as String?
            questResponse.text = map.get("text") as String?
            questResponse.publishedAt = map.get("publishedAt") as String?


            questResponse.votes = ArrayList<QuestResponseVote>()
            val questResponseVotes = map.get("votes") as ArrayList<Map<String, Any>>?
            if (questResponseVotes != null) {
                for (questResponseVote in questResponseVotes) {
                    val vote = QuestResponseVote.createFromMap(questResponseVote)
                    questResponse!!.votes!!.add(vote)
                }
            }

            return questResponse
        }
    }

    var publisherUid: String? = null
    var text: String? = null
    var publishedAt: String? = null
    var votes: ArrayList<QuestResponseVote>? = null

    fun votesValue(): Int {
        var votes = 0
        if (this.votes != null && !this.votes!!.isEmpty()) {
            this.votes!!.forEach { vote ->
                votes += vote.value
            }
        }
        return votes
    }

}