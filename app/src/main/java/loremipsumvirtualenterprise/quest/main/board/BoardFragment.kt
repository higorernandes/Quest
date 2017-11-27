package loremipsumvirtualenterprise.quest.main.board

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.toolbar_main.*
import loremipsumvirtualenterprise.quest.R
import loremipsumvirtualenterprise.quest.generic.MainGenericFragment
import loremipsumvirtualenterprise.quest.main.home.HomeFragment

/**
 * Created by root on 2017-11-26.
 */
class BoardFragment : MainGenericFragment()
{
    //region Attributes

    private var mContext : Context? = null

    //endregion

    //region Companion Object

    companion object {
        fun newInstance(): BoardFragment {
            val fragment = BoardFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    //endregion

    //region Overridden Methods

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mContext = container?.context
        return inflater?.inflate(R.layout.fragment_main_board, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
    }

    override fun onFragmentReselected() {

    }

    //endregion

    //region Private Methods

    private fun setUpToolbar() {
        mainToolbarTitleTextView.text = resources.getString(R.string.main_board_bottombar_text)
    }

    //endregion
}