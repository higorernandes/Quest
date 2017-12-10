package loremipsumvirtualenterprise.quest.main.notifications

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import loremipsumvirtualenterprise.quest.R
import loremipsumvirtualenterprise.quest.model.Notification

/**
 * Created by root on 2017-12-10.
 */
class NotificationsArrayAdapter constructor(context: Context, objects: ArrayList<Notification>?, private val clickListener: (Notification) -> Unit) : RecyclerView.Adapter<NotificationsArrayAdapter.Holder>()
{
    //region Attributes

    private var mContext : Context = context
    private var mObjects : ArrayList<Notification>? = objects

    //endregion

    //region Overridden Methods

    override fun onBindViewHolder(holder: Holder?, position: Int) {
        var notificationItem: Notification = mObjects!![position]

        holder?.itemNotificationRootView?.setOnClickListener { clickListener(notificationItem)}
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Holder {
        val itemView : View = LayoutInflater.from(parent?.context).inflate(R.layout.item_notification, parent, false)
        return Holder(itemView)
    }

    override fun getItemCount(): Int {
        return if (mObjects != null) {
            mObjects!!.size
        } else {
            0;
        }
    }

    //endregion

    //region Inner Class

    class Holder constructor(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        var itemNotificationRootView: RelativeLayout? = null

        init {
            itemNotificationRootView = itemView.findViewById(R.id.itemNotificationRootView)
        }
    }

    //endregion
}