package loremipsumvirtualenterprise.quest.main.notifications

import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_main_notifications.*
import kotlinx.android.synthetic.main.toolbar_main.*
import loremipsumvirtualenterprise.quest.R
import loremipsumvirtualenterprise.quest.generic.MainGenericFragment
import loremipsumvirtualenterprise.quest.model.Notification
import loremipsumvirtualenterprise.quest.settings.SettingsActivity

/**
 * Created by root on 2017-11-26.
 */
class NotificationsFragment : MainGenericFragment()
{
    //region Attributes

    private var mNotifications : ArrayList<Notification> = ArrayList<Notification>()
    private var mNotificationsAdapter : NotificationsArrayAdapter? = null
    private var mContext : Context? = null

    //endregion

    //region Companion Object

    companion object {
        fun newInstance(): NotificationsFragment {
            val fragment = NotificationsFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mContext = container?.context
        return inflater?.inflate(R.layout.fragment_main_notifications, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpToolbar()
        prepareViews()
        loadNotifications()
    }

    //endregion

    //region Overridden Methods

    override fun onFragmentReselected() {

    }

    //endregion

    //region Private Methods

    private fun setUpToolbar() {
        mainToolbarTitleTextView.text = resources.getString(R.string.main_notifications_bottombar_text)
        mainToolbarActionIcon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_settings))
        mainToolbarActionButton.visibility = View.VISIBLE
        mainToolbarActionButton.setOnClickListener {
            startActivity(SettingsActivity.getActivityIntent(context))
        }
    }

    private fun prepareViews() {
        mNotificationsAdapter = NotificationsArrayAdapter(context, mNotifications) {
            // TODO: navigate to correct screen;
        }
        notificationsRecyclerView.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        notificationsRecyclerView.itemAnimator = DefaultItemAnimator()
        notificationsRecyclerView.adapter = mNotificationsAdapter
    }

    private fun loadNotifications() {
        mNotifications.add(Notification())
        mNotifications.add(Notification())
        mNotifications.add(Notification())
        mNotifications.add(Notification())
        mNotifications.add(Notification())
        mNotifications.add(Notification())
        mNotificationsAdapter?.notifyDataSetChanged()
    }

    //endregion
}