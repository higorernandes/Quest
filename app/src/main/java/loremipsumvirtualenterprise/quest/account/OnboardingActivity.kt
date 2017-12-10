package loremipsumvirtualenterprise.quest.account

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.text.Html
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_onboarding.*
import loremipsumvirtualenterprise.quest.R
import android.os.Build

class OnboardingActivity : AppCompatActivity(), View.OnClickListener, ViewPager.OnPageChangeListener
{
    private var mLayouts = intArrayOf()

    companion object {
        var instance : OnboardingActivity? = null
        fun getActivityIntent(context : Context) : Intent {
            return Intent(context, OnboardingActivity::class.java)
        }
    }

    //region Overridden Methods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        mLayouts = intArrayOf(R.layout.fragment_onboarding_page_one,
                R.layout.fragment_onboarding_page_two,
                R.layout.fragment_onboarding_page_three)

        configureViews()

        addBottomDots(0)
        changeStatusBarColor()

        instance = this
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.onboardingLoginButton -> {
                val intent : Intent = LoginActivity.getActivityIntent(this)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
        }
    }

    override fun onPageScrollStateChanged(state: Int) { }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) { }

    override fun onPageSelected(position: Int) {
        addBottomDots(position)
    }

    //endregion

    //region Private Methods

    private fun configureViews() {
        onboardingLoginButton.setOnClickListener(this)

        val onboardingViewPagerAdapter = OnboardingViewPagerAdapter()
        onboardingViewPager.adapter = onboardingViewPagerAdapter
        onboardingViewPager.addOnPageChangeListener(this)
    }

    private fun changeStatusBarColor() {
        val window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorBackground)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decor = getWindow().decorView
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    private fun addBottomDots(currentPage: Int) {
        val dots = arrayOfNulls<TextView>(mLayouts.size)

        onboardingDotsLinearLayout.removeAllViews()

        for (i in dots.indices) {
            dots[i] = TextView(this)
            val linearLayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            linearLayoutParams.setMargins(10, 0, 10, 0) // llp.setMargins(left, top, right, bottom);
            dots[i]?.layoutParams = linearLayoutParams

            val result: Spanned
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                result = Html.fromHtml("&#8226;", Html.FROM_HTML_MODE_LEGACY)
            } else {
                result = Html.fromHtml("&#8226;")
            }
            dots[i]?.text = result

            dots[i]?.textSize = 36f
            dots[i]?.setTextColor(ContextCompat.getColor(this, R.color.colorBlackTranslucent))
            onboardingDotsLinearLayout.addView(dots[i])
        }

        if (dots.isNotEmpty())
            dots[currentPage]?.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
    }

    //region Inner Classes

    inner class OnboardingViewPagerAdapter : PagerAdapter() {

        private var layoutInflater : LayoutInflater? = null

        override fun instantiateItem(container: ViewGroup?, position: Int): Any? {
            layoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            val view = layoutInflater?.inflate(mLayouts[position], container, false)

            container?.addView(view)

            return view
        }

        override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
            return view == `object`
        }

        override fun getCount(): Int {
            return mLayouts.size
        }

        override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
            val view : View = `object` as View
            container?.removeView(view)
        }

    }

    //endregion
}
