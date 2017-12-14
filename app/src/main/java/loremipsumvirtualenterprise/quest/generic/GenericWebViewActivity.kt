package loremipsumvirtualenterprise.quest.generic

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.webkit.WebResourceRequest
import kotlinx.android.synthetic.main.activity_generic_web_view.*
import loremipsumvirtualenterprise.quest.R
import android.webkit.WebView
import android.webkit.WebViewClient
import java.io.Serializable

class GenericWebViewActivity : QuestGenericActivity()
{
    //region Attributes

    private var mWebViewUrl: String? = null
    private var mWebViewType: WebViewType? = null

    //endregion

    //region Contract

    companion object {
        private val WEB_VIEW_URL: String = "WEB_VIEW_URL"
        private val WEB_VIEW_TYPE: String = "WEB_VIEW_TYPE"

        fun getActivityIntent(context: Context, url: String, type: WebViewType) : Intent {
            val intent = Intent(context, GenericWebViewActivity::class.java)
            intent.putExtra(WEB_VIEW_URL, url)
            intent.putExtra(WEB_VIEW_TYPE, type)
            return intent
        }
    }

    //endregion

    //region Overridden Methods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generic_web_view)

        mWebViewUrl = intent.getStringExtra(WEB_VIEW_URL)
        mWebViewType = intent.getSerializableExtra(WEB_VIEW_TYPE) as WebViewType?

        var webViewTitle: String? = null
        when (mWebViewType) {
            WebViewType.WEB_VIEW_PRIVACY_POLICY -> webViewTitle = resources.getString(R.string.settings_privacy_policy)
            WebViewType.WEB_VIEW_ABOUT -> webViewTitle = resources.getString(R.string.settings_about)
        }
        setUpToolbar(genericWebViewToolbar as Toolbar?, webViewTitle)
        changeStatusBarColor()

        genericWebView.webViewClient = CustomWebViewClient()
        val url = mWebViewUrl
        genericWebView.settings.javaScriptEnabled = false
        genericWebView.loadUrl(url) // load a web page in a web view
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    //endregion

    //region Inner classes

    private inner class CustomWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            view?.loadUrl(request?.url.toString())
            return true
        }
    }

    enum class WebViewType {
        WEB_VIEW_PRIVACY_POLICY,
        WEB_VIEW_ABOUT
    }

    //endregion
}
