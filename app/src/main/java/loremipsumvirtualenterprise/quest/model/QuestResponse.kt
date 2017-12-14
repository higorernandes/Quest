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
            val votes = map.get("votes")
            questResponse.votes = votes as Long? // change this to an object

            return questResponse
        }
    }


    var publisherUid: String? = null
    var text: String? = null
    var publishedAt: String? = null
    var votes: Long? = null

}