package loremipsumvirtualenterprise.quest.util

import android.text.format.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by root on 2017-12-18.
 */
class DateHelper
{
    companion object {
        fun formatDate(date: Date) : String {
            return DateFormat.format("dd de MMM de yyyy", date) as String
        }

        fun formatDate(date: String?) : String {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale("pt", "BR"))
            val formattedDate = dateFormat.parse(date)
            return DateFormat.format("dd 'de' MMMM 'de' yyyy", formattedDate) as String
        }
    }
}