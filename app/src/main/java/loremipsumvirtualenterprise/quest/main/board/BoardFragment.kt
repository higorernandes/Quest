package loremipsumvirtualenterprise.quest.main.board

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_main_board.*
import kotlinx.android.synthetic.main.layout_empty_list.*
import kotlinx.android.synthetic.main.toolbar_main.*
import loremipsumvirtualenterprise.quest.R
import loremipsumvirtualenterprise.quest.generic.MainGenericFragment
import loremipsumvirtualenterprise.quest.generic.QuestGenericProgress
import loremipsumvirtualenterprise.quest.main.quest.CreateQuestActivity
import loremipsumvirtualenterprise.quest.main.quest.QuestDetailActivity
import loremipsumvirtualenterprise.quest.model.Quest
import loremipsumvirtualenterprise.quest.model.QuestLike
import loremipsumvirtualenterprise.quest.model.QuestResponse

/**
 * Created by root on 2017-11-26.
 */
class BoardFragment : MainGenericFragment()
{
    // Constants
    private val TAG = "BoardFragment"

    //Firebase references
    private var firebaseDatabaseReference: DatabaseReference? = null

    // Properties
    private var mContext : Context? = null
    private var mQuestsAdapter : QuestsArrayAdapter? = null
    private var mQuestsList : ArrayList<Quest> = ArrayList<Quest>()

    // Instantiation
    companion object {
        fun newInstance(): BoardFragment {
            val fragment = BoardFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    // Initialization
    private fun initializeVariables() {
        firebaseDatabaseReference = FirebaseDatabase.getInstance().reference
    }

    // Listeners
    private fun configureListeners() {
        firebaseDatabaseReference!!.orderByKey().addListenerForSingleValueEvent(questItemsListener)
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
            emptyLayout.visibility = View.VISIBLE
            mainBoardRecyclerView.visibility = View.GONE
        }
    }

    private fun loadDateFromSnapshot(dataSnapshot: DataSnapshot) {

        mProgress?.show()
        val items = dataSnapshot.children.iterator()
        mQuestsList.clear()

        if (emptyLayout.visibility == View.VISIBLE) {
            emptyLayout.visibility = View.GONE
            mainBoardRecyclerView.visibility = View.VISIBLE
        }

        //Check if current database contains any collection
        if (items.hasNext()) {
            val questListIndex = items.next()
            val itemsIterator = questListIndex.children.iterator()

            //check if the collection has any to do items or not
            while (itemsIterator.hasNext()) {

                //get current item
                val currentItem = itemsIterator.next()
                val questItem = Quest.create()

                //get current data in a map
                val map = currentItem.value as HashMap<*, *>

                //key will return Firebase ID
                questItem.id = currentItem.key
                questItem.title = map.get("title") as String?
                questItem.description = map.get("description") as String?
                questItem.publishedAt = map.get("publishedAt") as String?
                questItem.publisherUID = map.get("publisherUID") as String?
//                questItem.likes = map.get("likes") as Array<QuestLike>?
//                questItem.responses = map.get("responses") as Array<QuestResponse>?

                // add to list
                mQuestsList.add(questItem)
            }
        }

        //alert adapter that has changed
        activity.runOnUiThread {
            mProgress?.hide()
            mQuestsAdapter?.notifyDataSetChanged()
        }
    }

    // Lifecycle
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mContext = container?.context
        return inflater?.inflate(R.layout.fragment_main_board, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initializeVariables()
        configureListeners()
        setUpToolbar()
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
        mainBoardRecyclerView.itemAnimator = DefaultItemAnimator()
        mainBoardRecyclerView.layoutManager = LinearLayoutManager(mContext, LinearLayout.VERTICAL, false)
        mQuestsAdapter =  QuestsArrayAdapter(context, mQuestsList) { item: Quest ->
            startActivity(QuestDetailActivity.getActivityIntent(context, item.id!!))
        }
        mainBoardRecyclerView.adapter = mQuestsAdapter

        boardFloatingActionButton.setOnClickListener { startActivity(CreateQuestActivity.getActivityIntent(context)) }
        noInternetReconnectButton.setOnClickListener {
            initializeVariables()
            configureListeners()
        }
    }

}
