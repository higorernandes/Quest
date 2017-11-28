package loremipsumvirtualenterprise.quest.main.board

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.LayoutInflater.*
import android.view.View
import android.view.ViewGroup
import loremipsumvirtualenterprise.quest.R
import loremipsumvirtualenterprise.quest.model.Quest

/**
 * Created by root on 2017-11-27.
 */
class QuestsArrayAdapter constructor(context: Context, objects: ArrayList<Quest>?, val clickListener: (View.OnClickListener) -> Unit) : RecyclerView.Adapter<QuestsArrayAdapter.Holder>()
{
    //region Attributes

    private var mContext : Context = context
    private var mObjects : ArrayList<Quest>? = objects

    //endregion

    //region Overridden Methods

    override fun onBindViewHolder(holder: Holder?, position: Int) {
        var questItem: Quest = mObjects!![position]
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Holder {
        val itemView : View = from(parent?.context).inflate(R.layout.item_board_quest, parent, false)
        return Holder(itemView)
    }

    override fun getItemCount(): Int {
        return if (mObjects != null) {
            mObjects!!.size
        } else {
            0;
        }
    }

    //endregion

    //region Inner Class

    class Holder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {

        }
    }

    //endregion
}