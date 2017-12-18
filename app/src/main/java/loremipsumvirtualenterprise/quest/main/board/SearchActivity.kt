package loremipsumvirtualenterprise.quest.main.board

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.fragment_main_board.*
import kotlinx.android.synthetic.main.layout_empty_list.*
import kotlinx.android.synthetic.main.toolbar_search.*
import loremipsumvirtualenterprise.quest.R
import loremipsumvirtualenterprise.quest.generic.QuestGenericActivity
import loremipsumvirtualenterprise.quest.model.Quest
import loremipsumvirtualenterprise.quest.util.FirebaseDatabaseUtil

class SearchActivity : QuestGenericActivity()
{
    // Constants
    private val TAG = "SearchActivity"

    //region Attributes

    private var mSearchResultsAdapter: SearchResultsAdapter? = null
    private var mSearchResults: ArrayList<Quest> = ArrayList<Quest>()
    private val mQuests: ArrayList<Quest> = ArrayList<Quest>()

    //endregion

    //region Constructor

    companion object {
        fun getActivityIntent(context: Context) : Intent {
            return Intent(context, SearchActivity::class.java)
        }
    }

    //endregion

    //region Overridden Methods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setUpToolbar(searchToolbar as Toolbar?, resources.getString(R.string.settings_toolbar_title))
        changeStatusBarColor()
        prepareViews()
        configureListeners()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return super.onOptionsItemSelected(item)
    }

    //endregion

    // Listeners
    private fun configureListeners() {
        if (FirebaseDatabaseUtil.questsNode != null) {
            FirebaseDatabaseUtil.questsNode.orderByKey().addValueEventListener(questItemsListener)
        }
    }

    var questItemsListener: ValueEventListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // Get Post object and use the values to update the UI
            loadDataFromSnapshot(dataSnapshot)
        }

        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Item failed, log a message
//            mProgress?.hide()
            Log.w(TAG, "loadItem:onCancelled", databaseError.toException())
            emptyLayout?.visibility = View.VISIBLE
            mainBoardRecyclerView?.visibility = View.GONE
        }
    }

    private fun loadDataFromSnapshot(dataSnapshot: DataSnapshot) {

        val items = dataSnapshot.children.iterator()
        mQuests.clear()

        if (emptyLayout?.visibility == View.VISIBLE) {
            emptyLayout?.visibility = View.GONE
            mainBoardRecyclerView.visibility = View.VISIBLE
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
                if (questItem != null){
                    mQuests.add(questItem)
                }

            }
        }

        //alert adapter that has changed
//        this.runOnUiThread {
//            if (mSearchResults.size == 0) {
//                emptyListGreetingTextView.text = resources.getString(R.string.board_no_items_greeting_text)
//                emptyListTextTextView.text = resources.getString(R.string.board_no_items_text)
//                emptyListSuggestionTextView.text = resources.getString(R.string.board_no_items_suggestion_text)
//                noInternetReconnectButton.visibility = View.GONE
//                emptyLayout.visibility = View.VISIBLE
//                mainBoardRecyclerView.visibility = View.GONE
//            } else {
////                mProgress?.hide()
////                mQuestsAdapter?.notifyDataSetChanged()
//                mainBoardRecyclerView.visibility = View.VISIBLE
//                noInternetReconnectButton.visibility = View.VISIBLE
//                emptyLayout.visibility = View.GONE
//                boardFloatingActionButton.visibility = View.VISIBLE
//            }
//        }
    }

    //region Private Methods

    private fun prepareViews() {
        mSearchResultsAdapter = SearchResultsAdapter(this, mSearchResults) { }
        searchRecyclerView.itemAnimator = DefaultItemAnimator()
        searchRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        searchRecyclerView.adapter = mSearchResultsAdapter

        searchToolbarSearchButton.setOnClickListener { performSearch() }
    }

    private fun performSearch() {
        //TODO: perform proper search
        val searchParameter = searchToolbarEditText.text.toString()

        mSearchResults.clear()
        for (quest in mQuests) {
            if (quest.title?.contains(searchParameter, true)!!) {
                mSearchResults.add(quest)
            }
        }

//        for (i in 0..5) {
//            val quest = Quest()
//            quest.title = "Why do they always send the poor?"
//            quest.description = "'Cause I'm slim shady yes I'm the real shady all other slim shadies are just imitating"
//            quest.publishedAt = "25 DE NOVEMBRO DE 2017"
//            mSearchResults.add(quest)
//        }

        mSearchResultsAdapter?.notifyDataSetChanged()
    }

    //endregion
}
