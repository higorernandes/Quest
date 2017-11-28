package loremipsumvirtualenterprise.quest.main

import loremipsumvirtualenterprise.quest.generic.MainGenericFragment
import loremipsumvirtualenterprise.quest.main.board.BoardFragment
import loremipsumvirtualenterprise.quest.main.chat.ChatFragment
import loremipsumvirtualenterprise.quest.main.home.HomeFragment
import loremipsumvirtualenterprise.quest.main.notifications.NotificationsFragment

/**
 * Created by root on 2017-11-26.
 */
class MainFragmentTabsManager
{
    private var mBoardFragment: BoardFragment? = null
    private var mHomeFragment: HomeFragment? = null
    private var mChatFragment: ChatFragment? = null
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

    private fun getBoardFragment(): BoardFragment {
        if (mBoardFragment == null) {
            mBoardFragment = BoardFragment.newInstance()
            mBoardFragment!!.retainInstance = true
        }
        return mBoardFragment as BoardFragment
    }

    private fun getHomeFragment(): HomeFragment {
        if (mHomeFragment == null) {
            mHomeFragment = HomeFragment.newInstance()
            mHomeFragment!!.retainInstance = true
        }
        return mHomeFragment as HomeFragment
    }

    private fun getChatFragment(): ChatFragment {
        if (mChatFragment == null) {
            mChatFragment = ChatFragment.newInstance()
            mChatFragment!!.retainInstance = true
        }
        return mChatFragment as ChatFragment
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
            MainFragmentType.FRAGMENT_BOARD -> mBoardFragment != null
            MainFragmentType.FRAGMENT_HOME -> mHomeFragment != null
            MainFragmentType.FRAGMENT_CHAT -> mChatFragment != null
            MainFragmentType.FRAGMENT_NOTIFICATIONS -> mNotificationsFragment != null
        }
    }

    fun getMainFragmentByType(fragmentType: MainFragmentType): MainGenericFragment? {
        return when (fragmentType) {
            MainFragmentType.FRAGMENT_BOARD -> getBoardFragment()
            MainFragmentType.FRAGMENT_HOME -> getHomeFragment()
            MainFragmentType.FRAGMENT_CHAT -> getChatFragment()
            MainFragmentType.FRAGMENT_NOTIFICATIONS -> getNotificationsFragment()
        }
    }

    fun clearAllFragments() {
        mBoardFragment = null
        mHomeFragment = null
        mNotificationsFragment = null
        mChatFragment = null
    }

    //region Inner Classes

    enum class MainFragmentType {
        FRAGMENT_BOARD,
        FRAGMENT_HOME,
        FRAGMENT_CHAT,
        FRAGMENT_NOTIFICATIONS
    }

    //endregion
}