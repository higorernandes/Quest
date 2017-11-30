package loremipsumvirtualenterprise.quest.main.chat

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_main_chat.*
import kotlinx.android.synthetic.main.toolbar_main.*
import loremipsumvirtualenterprise.quest.R
import loremipsumvirtualenterprise.quest.generic.MainGenericFragment
import loremipsumvirtualenterprise.quest.main.board.ChatsArrayAdapter
import loremipsumvirtualenterprise.quest.model.Chat

/**
 * Created by root on 2017-11-26.
 */
class ChatFragment : MainGenericFragment()
{
    //region Attributes

    private var mChatsArrayAdapter : ChatsArrayAdapter?  = null
    private var mObjects : ArrayList<Chat> = ArrayList<Chat>()
    private var mContext : Context? = null

    //endregion

    //region Companion Object

    companion object {
        fun newInstance(): ChatFragment {
            val fragment = ChatFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    //endregion

    //region Overridden Methods

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mContext = container?.context
        return inflater?.inflate(R.layout.fragment_main_chat, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        setUpToolbar()
        loadChats()
    }

    override fun onFragmentReselected() {

    }

    //endregion

    //region Private Methods

    private fun initViews() {
        mChatsArrayAdapter = ChatsArrayAdapter(context, mObjects) {
            //TODO: open chat conversation window.
        }
        mainChatRecyclerView.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        mainChatRecyclerView.adapter = mChatsArrayAdapter
    }

    private fun setUpToolbar() {
        mainToolbarTitleTextView.text = resources.getString(R.string.main_chat_bottombar_text)
    }

    private fun loadChats() {
        mObjects.add(Chat())
        mObjects.add(Chat())
        mObjects.add(Chat())
        mObjects.add(Chat())
        mObjects.add(Chat())
        mObjects.add(Chat())
    }

    //endregion
}