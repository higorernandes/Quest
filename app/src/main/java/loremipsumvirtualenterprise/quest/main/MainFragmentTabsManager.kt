package loremipsumvirtualenterprise.quest.main

import loremipsumvirtualenterprise.quest.generic.MainGenericFragment
import loremipsumvirtualenterprise.quest.main.board.BoardFragment
import loremipsumvirtualenterprise.quest.main.home.HomeFragment
import loremipsumvirtualenterprise.quest.main.notifications.NotificationsFragment
import loremipsumvirtualenterprise.quest.main.trending.TrendingFragment

/**
 * Created by root on 2017-11-26.
 */
class MainFragmentTabsManager
{

    private var mHomeFragment: HomeFragment? = null
    private var mTrendingFragment: TrendingFragment? = null
    private var mBoardFragment: BoardFragment? = null
    private var mNotificationsFragment: NotificationsFragment? = null

    private var mCurrentSelectedFragment: MainGenericFragment? = null



    companion object {
        private var mInstance: MainFragmentTabsManager? = null

        fun getInstance(): MainFragmentTabsManager {
            if (mInstance == null) {
                mInstance = MainFragmentTabsManager()
            }
            return mInstance as MainFragmentTabsManager
        }
    }

    fun setCurrentSelectedFragment(fragment: MainGenericFragment) {
        mCurrentSelectedFragment = fragment
    }

    fun getCurrentSelectedFragment(): MainGenericFragment? {
        return mCurrentSelectedFragment
    }

    private fun getHomeFragment(): HomeFragment {
        if (mHomeFragment == null) {
            mHomeFragment = HomeFragment.newInstance()
            mHomeFragment!!.retainInstance = true
        }
        return mHomeFragment as HomeFragment
    }

    private fun getTrendingFragment(): TrendingFragment {
        if (mTrendingFragment == null) {
            mTrendingFragment = TrendingFragment.newInstance()
            mTrendingFragment!!.retainInstance = true
        }
        return mTrendingFragment as TrendingFragment
    }

    private fun getBoardFragment(): BoardFragment {
        if (mBoardFragment == null) {
            mBoardFragment = BoardFragment.newInstance()
            mBoardFragment!!.retainInstance = true
        }
        return mBoardFragment as BoardFragment
    }

    private fun getNotificationsFragment(): NotificationsFragment {
        if (mNotificationsFragment == null) {
            mNotificationsFragment = NotificationsFragment.newInstance()
            mNotificationsFragment!!.retainInstance = true
        }
        return mNotificationsFragment as NotificationsFragment
    }

    fun verifyFragmentInstantiatedByType(fragmentType: MainFragmentType): Boolean? {
        return when (fragmentType) {
            MainFragmentType.FRAGMENT_HOME -> mHomeFragment != null
            MainFragmentType.FRAGMENT_TRENDING -> mTrendingFragment != null
            MainFragmentType.FRAGMENT_BOARD -> mBoardFragment != null
            MainFragmentType.FRAGMENT_NOTIFICATIONS -> mNotificationsFragment != null
        }
    }

    fun getMainFragmentByType(fragmentType: MainFragmentType): MainGenericFragment? {
        return when (fragmentType) {
            MainFragmentType.FRAGMENT_HOME -> getHomeFragment()
            MainFragmentType.FRAGMENT_TRENDING -> getTrendingFragment()
            MainFragmentType.FRAGMENT_BOARD -> getBoardFragment()
            MainFragmentType.FRAGMENT_NOTIFICATIONS -> getNotificationsFragment()
        }
    }

    fun clearAllFragments() {
        mHomeFragment = null
        mBoardFragment = null
        mNotificationsFragment = null
        mTrendingFragment = null
    }

    //region Inner Classes

    enum class MainFragmentType {
        FRAGMENT_HOME,
        FRAGMENT_TRENDING,
        FRAGMENT_BOARD,
        FRAGMENT_NOTIFICATIONS
    }

    //endregion
}