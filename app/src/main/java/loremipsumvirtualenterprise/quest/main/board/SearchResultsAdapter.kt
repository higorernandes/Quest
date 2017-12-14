package loremipsumvirtualenterprise.quest.main.board

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import loremipsumvirtualenterprise.quest.R
import loremipsumvirtualenterprise.quest.model.Quest

/**
 * Created by root on 2017-12-13.
 */
class SearchResultsAdapter constructor(context: Context, objects: ArrayList<Quest>?, private val clickListener: (Quest) -> Unit) : RecyclerView.Adapter<SearchResultsAdapter.Holder>()
{
    //region Attributes

    private var mContext : Context = context
    private var mObjects : ArrayList<Quest>? = objects

    //endregion

    //region Overridden Methods

    override fun onBindViewHolder(holder: Holder?, position: Int) {
        val questItem: Quest = mObjects!![position]

        holder?.searchResultQuestTitle?.text = questItem.title
        holder?.searchResultQuestDescription?.text = questItem.description
        holder?.searchResultQuestPublishDate?.text = questItem.publishedAt
        holder?.searchResultRootView?.setOnClickListener { clickListener(questItem) }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Holder {
        val itemView : View = LayoutInflater.from(parent?.context).inflate(R.layout.item_search_result, parent, false)
        return Holder(itemView)
    }

    override fun getItemCount(): Int {
        return if (mObjects != null) {
            mObjects!!.size
        } else {
            0
        }
    }

    //endregion

    // Helpers
//    fun loadAuthorForHolder(holder: Holder?, quest: Quest) {
//
//        val publisherDatabaseReference = FirebaseDatabaseUtil.usersNode?.child(quest.publisherUID!!)
//
//        publisherDatabaseReference?.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                if (snapshot.value != null) {
//                    val questUser = QuestUser.createFromDataSnapshot(snapshot)
//                    holder?.itemQuestAuthor?.text = "@" + questUser.name
//                }
//            }
//            override fun onCancelled(error: DatabaseError) {}
//        })
//
//    }

    //region Inner Class

    class Holder constructor(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        var searchResultRootView: LinearLayout? = null
        var searchResultQuestTitle: TextView? = null
        var searchResultQuestDescription: TextView? = null
        var searchResultQuestPublishDate: TextView? = null

        init {
            searchResultRootView = itemView.findViewById<LinearLayout>(R.id.searchResultRootView)
            searchResultQuestTitle = itemView.findViewById<TextView>(R.id.searchResultQuestTitle)
            searchResultQuestDescription = itemView.findViewById<TextView>(R.id.searchResultQuestDescription)
            searchResultQuestPublishDate = itemView.findViewById<TextView>(R.id.searchResultQuestPublishDate)
        }
    }

}