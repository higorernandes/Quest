package loremipsumvirtualenterprise.quest.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by bocato on 09/12/17.
 */
class QuestResponse() : Parcelable
{
    constructor(parcel: Parcel) : this() {
        val data = arrayOfNulls<String>(0)

        parcel.readStringArray(data)
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0?.writeStringArray(arrayOf<String>())
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<QuestResponse> {
        override fun createFromParcel(parcel: Parcel): QuestResponse {
            return QuestResponse(parcel)
        }

        override fun newArray(size: Int): Array<QuestResponse?> {
            return arrayOfNulls(size)
        }
    }
}