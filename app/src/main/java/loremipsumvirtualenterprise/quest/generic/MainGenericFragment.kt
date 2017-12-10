package loremipsumvirtualenterprise.quest.generic

import android.support.v4.app.Fragment

/**
 * Created by root on 2017-11-26.
 */
abstract class MainGenericFragment : Fragment()
{
    var mProgress: QuestGenericProgress? = null
    protected abstract fun onFragmentReselected()
//    public abstract void setPresenter(IPresenter presenter)
}