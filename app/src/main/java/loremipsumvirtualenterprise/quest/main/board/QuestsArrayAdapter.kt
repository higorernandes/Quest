package loremipsumvirtualenterprise.quest.main.board

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater.*
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import loremipsumvirtualenterprise.quest.R
import loremipsumvirtualenterprise.quest.model.Quest

/**
 * Created by root on 2017-11-27.
 */
class QuestsArrayAdapter constructor(context: Context, objects: ArrayList<Quest>?, private val clickListener: (Quest) -> Unit) : RecyclerView.Adapter<QuestsArrayAdapter.Holder>()
{
    //region Attributes

    private var mContext : Context = context
    private var mObjects : ArrayList<Quest>? = objects

    //endregion

    //region Interface

    interface IQuestAdapterListener

    //endregion

    //region Overridden Methods

    override fun onBindViewHolder(holder: Holder?, position: Int) {
        val questItem: Quest = mObjects!![position]

        holder?.itemQuestTitle?.text = questItem.title
        holder?.itemQuestDescription?.text = questItem.description
        holder?.itemQuestPublishDate?.text = questItem.publishedAt
//        holder?.itemQuestAuthor?.text
        holder?.itemQuestResponses?.text = mContext.resources.getString(R.string.board_item_responses_text)
                .replace("{likes}", if (questItem.likes?.size == null) "0" else questItem.likes?.size.toString())
                .replace("{responses}", if (questItem.responses?.size == null) "0" else questItem.responses?.size.toString())
        holder?.itemQuestRootView?.setOnClickListener { clickListener(questItem)}
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Holder {
        val itemView : View = from(parent?.context).inflate(R.layout.item_board_quest, parent, false)
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

    //region Inner Class

    class Holder constructor(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        var itemQuestRootView: LinearLayout? = null
        var itemQuestTitle: TextView? = null
        var itemQuestDescription: TextView? = null
        var itemQuestPublishDate: TextView? = null
        var itemQuestAuthor : TextView? = null
        var itemQuestResponses : TextView? = null

        init {
            itemQuestRootView = itemView.findViewById<LinearLayout>(R.id.itemQuestRootView)
            itemQuestTitle = itemView.findViewById<TextView>(R.id.itemQuestTitle)
            itemQuestDescription = itemView.findViewById<TextView>(R.id.itemQuestDescription)
            itemQuestPublishDate = itemView.findViewById<TextView>(R.id.itemQuestPublishDate)
            itemQuestAuthor = itemView.findViewById<TextView>(R.id.itemQuestAuthor)
            itemQuestResponses = itemView.findViewById<TextView>(R.id.itemQuestLikeResponsesCountTextView)
        }
    }

    //endregion
}