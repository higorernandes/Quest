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
import android.view.MenuItem
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.toolbar_search.*
import loremipsumvirtualenterprise.quest.R
import loremipsumvirtualenterprise.quest.generic.QuestGenericActivity
import loremipsumvirtualenterprise.quest.model.Quest

class SearchActivity : QuestGenericActivity()
{
    //region Attributes

    private var mSearchResultsAdapter: SearchResultsAdapter? = null
    private val mSearchResults: ArrayList<Quest> = ArrayList<Quest>()

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
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return super.onOptionsItemSelected(item)
    }

    //endregion

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
        searchToolbarEditText.text.toString()

        for (i in 0..5) {
            val quest = Quest()
            quest.title = "Why do they always send the poor?"
            quest.description = "'Cause I'm slim shady yes I'm the real shady all other slim shadies are just imitating"
            quest.publishedAt = "25 DE NOVEMBRO DE 2017"
            mSearchResults.add(quest)
        }
        mSearchResultsAdapter?.notifyDataSetChanged()
    }

    //endregion
}
