package loremipsumvirtualenterprise.quest.main.notifications

import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.toolbar_main.*
import loremipsumvirtualenterprise.quest.R
import loremipsumvirtualenterprise.quest.generic.MainGenericFragment
import loremipsumvirtualenterprise.quest.settings.SettingsActivity

/**
 * Created by root on 2017-11-26.
 */
class NotificationsFragment : MainGenericFragment()
{
    //region Attributes

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

    //endregion
}