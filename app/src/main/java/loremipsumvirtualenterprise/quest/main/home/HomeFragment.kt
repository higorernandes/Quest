package loremipsumvirtualenterprise.quest.main.home

import android.content.Context
import android.os.Bundle
import android.support.annotation.Nullable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_main_home.*
import kotlinx.android.synthetic.main.toolbar_main.*
import loremipsumvirtualenterprise.quest.R
import loremipsumvirtualenterprise.quest.generic.MainGenericFragment

/**
 * Created by root on 2017-11-26.
 */
class HomeFragment : MainGenericFragment()
{
    //region Attributes

    private var mContext : Context? = null

    //endregion

    //region Companion Object

    companion object {
        fun newInstance(): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    //endregion

    //region Overridden Methods

    override fun onFragmentReselected() {

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mContext = container?.context
        return inflater?.inflate(R.layout.fragment_main_home, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpToolbar()
    }

    //endregion

    //region Private Methods

    private fun setUpToolbar() {
        mainToolbarTitleTextView.text = resources.getString(R.string.main_home_bottombar_text)
    }

    //endregion
}