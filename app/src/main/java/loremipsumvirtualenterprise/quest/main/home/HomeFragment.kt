package loremipsumvirtualenterprise.quest.main.home

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_reset_password.*
import kotlinx.android.synthetic.main.fragment_main_board.*
import kotlinx.android.synthetic.main.fragment_main_home.*
import kotlinx.android.synthetic.main.layout_empty_list.*
import kotlinx.android.synthetic.main.toolbar_main.*
import loremipsumvirtualenterprise.quest.R
import loremipsumvirtualenterprise.quest.generic.MainGenericFragment
import loremipsumvirtualenterprise.quest.generic.QuestGenericProgress
import loremipsumvirtualenterprise.quest.main.board.QuestsArrayAdapter
import loremipsumvirtualenterprise.quest.main.board.SearchActivity
import loremipsumvirtualenterprise.quest.main.quest.CreateQuestActivity
import loremipsumvirtualenterprise.quest.main.quest.QuestDetailActivity
import loremipsumvirtualenterprise.quest.model.Quest
import loremipsumvirtualenterprise.quest.util.FirebaseDatabaseUtil
import loremipsumvirtualenterprise.quest.util.NetworkHelper

/**
 * Created by root on 2017-11-26.
 */
class HomeFragment : MainGenericFragment()
{
    // Constants
    private val TAG = "HomeFragment"

    // Properties
    private var mContext : Context? = null
    private var mQuestsAdapter : QuestsArrayAdapter? = null
    private var mQuestsList : ArrayList<Quest> = ArrayList<Quest>()

    // Instantiation
    companion object {
        fun newInstance(): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    // Listeners
    private fun configureListeners() {
        if (FirebaseDatabaseUtil.questsNode != null) {
            FirebaseDatabaseUtil.questsNode.orderByKey().addValueEventListener(questItemsListener)
        }
    }

    var questItemsListener: ValueEventListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // Get Post object and use the values to update the UI
            loadDateFromSnapshot(dataSnapshot)
        }

        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Item failed, log a message
            mProgress?.hide()
            Log.w(TAG, "loadItem:onCancelled", databaseError.toException())
            homeEmptyLayout.visibility = View.VISIBLE
            mainHomeRecyclerView.visibility = View.GONE
        }
    }

    private fun hasAnAnswerFromUser(questItem: Quest, currentUserUID: String?): Boolean {
        val responses = questItem.responses
        if (responses != null && currentUserUID != null){
            val responsesIterator = questItem.responses!!.iterator()
            if (responsesIterator.hasNext()) {
                while (responsesIterator.hasNext()) {
                    val response = responsesIterator.next()
                    return response.publisherUid == currentUserUID
                }
            }
        }

        return false
    }

    private fun loadDateFromSnapshot(dataSnapshot: DataSnapshot) {

        val items = dataSnapshot.children.iterator()
        mQuestsList.clear()

        if (homeEmptyLayout.visibility == View.VISIBLE) {
            homeEmptyLayout.visibility = View.GONE
            mainHomeRecyclerView.visibility = View.VISIBLE
        }

        //Check if current database contains any collection
        if (items.hasNext()) {

            val itemsIterator = items.iterator()

            //check if the collection has any to do items or not
            while (itemsIterator.hasNext()) {

                //get current item
                val currentItem = itemsIterator.next()
                val questItem = Quest.createFromDataSnapshot(currentItem)

                // add to list
                val currentUserUID = FirebaseAuth.getInstance().currentUser?.uid
                val wasAskedByUser = questItem.publisherUID != null && currentUserUID != null && questItem.publisherUID!!.equals(currentUserUID)
                val hasAnAnswerFromUser = hasAnAnswerFromUser(questItem, currentUserUID)
                if (wasAskedByUser || hasAnAnswerFromUser) {
                    mQuestsList.add(questItem)
                }

            }
        }

        //alert adapter that has changed
        activity.runOnUiThread {
            if (mQuestsList.size == 0) {
                emptyListGreetingTextView.text = resources.getString(R.string.board_no_items_greeting_text)
                emptyListTextTextView.text = resources.getString(R.string.board_no_items_text)
                emptyListSuggestionTextView.text = resources.getString(R.string.board_no_items_suggestion_text)
                noInternetReconnectButton.visibility = View.GONE
                homeEmptyLayout.visibility = View.VISIBLE
                mainHomeRecyclerView.visibility = View.GONEg
            } else {
                mProgress?.hide()
                mQuestsAdapter?.notifyDataSetChanged()
                mainHomeRecyclerView.visibility = View.VISIBLE
                noInternetReconnectButton.visibility = View.VISIBLE
                homeEmptyLayout.visibility = View.GONE
                mainHomeFloatingActionButton.visibility = View.VISIBLE
            }
        }
    }

    // Lifecycle
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mContext = container?.context
        return inflater?.inflate(R.layout.fragment_main_home, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        configureListeners()
        setUpToolbar()
        if (!NetworkHelper.isConnected) {
            noInternetReconnectButton.visibility = View.VISIBLE
            homeEmptyLayout.visibility = View.VISIBLE
            mainHomeRecyclerView.visibility = View.GONE
            mainHomeFloatingActionButton.visibility = View.GONE
            mProgress?.hide()
        } else {
            mProgress?.hide()
        }
    }

    override fun onFragmentReselected() { }

    // Configuration
    private fun setUpToolbar() {
        mainToolbarTitleTextView.text = resources.getString(R.string.main_board_bottombar_text)
        mainToolbarActionButton.visibility = View.VISIBLE
        mainToolbarActionButton.setOnClickListener {
            startActivity(SearchActivity.getActivityIntent(context))
        }
    }

    private fun initViews() {
        mProgress = QuestGenericProgress(context)
        mainHomeRecyclerView.itemAnimator = DefaultItemAnimator() as RecyclerView.ItemAnimator?
        mainHomeRecyclerView.layoutManager = LinearLayoutManager(mContext, LinearLayout.VERTICAL, false)
        mQuestsAdapter =  QuestsArrayAdapter(context, mQuestsList) { item: Quest ->
            startActivity(QuestDetailActivity.getActivityIntent(context, item.id!!))
        }
        mainHomeRecyclerView.adapter = mQuestsAdapter

        mainHomeRecyclerView.setOnClickListener { startActivity(CreateQuestActivity.getActivityIntent(context)) }
        mainHomeFloatingActionButton.setOnClickListener { startActivity(CreateQuestActivity.getActivityIntent(context)) }
//        noInternetReconnectButton.setOnClickListener {
//            configureListeners()
//        }
    }

}