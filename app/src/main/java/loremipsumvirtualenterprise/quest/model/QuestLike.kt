package loremipsumvirtualenterprise.quest.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by bocato on 09/12/17.
 */
class QuestLike() : Parcelable
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

    companion object CREATOR : Parcelable.Creator<QuestLike> {
        override fun createFromParcel(parcel: Parcel): QuestLike {
            return QuestLike(parcel)
        }

        override fun newArray(size: Int): Array<QuestLike?> {
            return arrayOfNulls(size)
        }
    }
}