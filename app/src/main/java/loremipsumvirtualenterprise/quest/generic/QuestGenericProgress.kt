package loremipsumvirtualenterprise.quest.generic

import android.app.Dialog
import android.content.Context
import com.wang.avi.AVLoadingIndicatorView
import android.graphics.drawable.ColorDrawable
import android.view.Window.FEATURE_NO_TITLE
import android.os.Bundle
import android.view.View
import android.view.Window
import kotlinx.android.synthetic.main.layout_generic_progress.*
import loremipsumvirtualenterprise.quest.R

/**
 * Created by root on 2017-12-10.
 */
class QuestGenericProgress(context: Context) : Dialog(context)
{
    private var mProgress: AVLoadingIndicatorView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        setContentView(R.layout.layout_generic_progress)
        this.setCancelable(false)
        mProgress = findViewById<AVLoadingIndicatorView>(R.id.progressLoadingIndicatorView) as AVLoadingIndicatorView
    }

    override fun show() {
        super.show()
        mProgress!!.visibility = View.VISIBLE
    }


    override fun hide() {
        super.dismiss()
        //        mProgress.setVisibility(View.GONE);
    }
}