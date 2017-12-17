package loremipsumvirtualenterprise.quest.generic

import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.toolbar_main.*
import loremipsumvirtualenterprise.quest.R
import android.text.Spannable
import loremipsumvirtualenterprise.quest.util.CustomTypefaceSpan
import android.text.SpannableString



/**
 * Created by root on 2017-12-04.
 */
abstract class QuestGenericActivity : AppCompatActivity()
{
    companion object {
        var sAvenirNextRegular: Typeface? = null
        var sAvenirNextItalic: Typeface? = null
        var sAvenirNextBold: Typeface? = null
        var sAvenirNextMedium: Typeface? = null
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        loadFonts()
    }

    fun changeStatusBarColor() {
        val window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.white)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decor = getWindow().decorView
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    fun setUpToolbar(toolbar: Toolbar?, text: String?) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.title = ""
        if (text != null) {
            if (mainToolbarTitleTextView != null) {
                mainToolbarTitleTextView.text = text
            }
        }
    }

    fun setFontError(s: String): SpannableString {
        val newTitle = SpannableString(s)
        newTitle.setSpan(CustomTypefaceSpan("", sAvenirNextRegular!!), 0,
                newTitle.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)

        return newTitle
    }

    private fun loadFonts() {
        sAvenirNextRegular = ResourcesCompat.getFont(this, R.font.avenir_next_regular)
        sAvenirNextBold = ResourcesCompat.getFont(this, R.font.avenir_next_bold)
        sAvenirNextItalic = ResourcesCompat.getFont(this, R.font.avenir_next_it)
        sAvenirNextMedium = ResourcesCompat.getFont(this, R.font.avenir_next_demi)
    }
}