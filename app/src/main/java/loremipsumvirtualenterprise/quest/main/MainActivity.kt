package loremipsumvirtualenterprise.quest.main

import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.WindowManager
import loremipsumvirtualenterprise.quest.R
import kotlinx.android.synthetic.main.activity_main.*
import loremipsumvirtualenterprise.quest.main.board.BoardFragment
import loremipsumvirtualenterprise.quest.main.home.HomeFragment
import loremipsumvirtualenterprise.quest.main.notifications.NotificationsFragment
import loremipsumvirtualenterprise.quest.main.trending.TrendingFragment
import android.text.SpannableStringBuilder
import loremipsumvirtualenterprise.quest.util.CustomTypefaceSpan
import android.support.v4.content.res.ResourcesCompat
import android.graphics.Typeface

class MainActivity : AppCompatActivity()
{
    //region Companion Object

    companion object {
        fun getActivityIntent(context : Context) : Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    //endregion

    //region Overridden Methods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        changeStatusBarColor()
        configureBottomBar()
    }

    //endregion

    //region Private Methods

    private fun changeStatusBarColor() {
        val window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.white)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decor = getWindow().decorView
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    private fun configureBottomBar() {
        // Setting the custom font on the bottom bar.
        val typeface: Typeface? = ResourcesCompat.getFont(this, R.font.avenir_next_regular)
        val typefaceSpan = CustomTypefaceSpan("", typeface!!)
        for (i in 0 until mainBottomBar.menu.size()) {
            val menuItem = mainBottomBar.menu.getItem(i)
            val spannableTitle = SpannableStringBuilder(menuItem.getTitle())
            spannableTitle.setSpan(typefaceSpan, 0, spannableTitle.length, 0)
            menuItem.title = spannableTitle
        }

        mainBottomBar.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_home -> setCurrentFragment(MainFragmentTabsManager.MainFragmentType.FRAGMENT_HOME)
                R.id.action_trending -> setCurrentFragment(MainFragmentTabsManager.MainFragmentType.FRAGMENT_TRENDING)
                R.id.action_board -> setCurrentFragment(MainFragmentTabsManager.MainFragmentType.FRAGMENT_BOARD)
                R.id.action_notifications -> setCurrentFragment(MainFragmentTabsManager.MainFragmentType.FRAGMENT_NOTIFICATIONS)
            }
            true
        }

        setCurrentFragment(MainFragmentTabsManager.MainFragmentType.FRAGMENT_HOME)
    }

    private fun setCurrentFragment(fragmentType: MainFragmentTabsManager.MainFragmentType) {
        val fragmentManager = supportFragmentManager
        val currentFragment = fragmentManager.findFragmentById(R.id.content)

        val fragmentTransaction = fragmentManager.beginTransaction()

        val newFragment = MainFragmentTabsManager.getInstance().getMainFragmentByType(fragmentType)

        if (currentFragment == null) {
            fragmentTransaction.add(R.id.content, newFragment)
            fragmentTransaction.commit()
            MainFragmentTabsManager.getInstance().setCurrentSelectedFragment(newFragment!!)
        } else {
            if (newFragment != null) {
                if (!newFragment.isAdded) {
                    fragmentTransaction.add(R.id.content, newFragment)
                }

                val currentFragmentsCount = if (fragmentManager.fragments != null) fragmentManager.fragments.size else 0
                for (i in 0 until currentFragmentsCount) {
                    val existentFragment = fragmentManager.fragments[i]
                    if (existentFragment != null) {
                        if (existentFragment is HomeFragment ||
                                existentFragment is TrendingFragment ||
                                existentFragment is BoardFragment ||
                                existentFragment is NotificationsFragment) {
                            fragmentTransaction.hide(existentFragment)
                        }
                    }
                }

                fragmentTransaction.show(newFragment)

                MainFragmentTabsManager.getInstance().setCurrentSelectedFragment(newFragment)

                fragmentTransaction.commit()
            }
        }
    }

    //endregion
}
