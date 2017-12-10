package loremipsumvirtualenterprise.quest.main.quest

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater.*
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import loremipsumvirtualenterprise.quest.R

/**
 * Created by root on 2017-12-10.
 */
class QuestResponsesArrayAdapter constructor(context: Context, objects: ArrayList<String>, private val upvoteClickListener: (String) -> Unit, private val downvoteClickListener: (String) -> Unit) : RecyclerView.Adapter<QuestResponsesArrayAdapter.Holder>()
{
    //region Attributes

    private var mContext : Context = context
    private var mObjects : ArrayList<String>? = objects

    //endregion

    //region Overridden Methods

    override fun onBindViewHolder(holder: Holder?, position: Int) {
        val questItem: String = mObjects!![position]

        Glide.with(mContext)
                .load("https://vignette.wikia.nocookie.net/rickandmorty/images/8/8f/Rickk22.png")
                .apply(RequestOptions.circleCropTransform())
                .into(holder?.itemResponseAuthorImageView)

        holder?.itemResponseUpvoteButton?.setOnClickListener {
            upvoteClickListener("")
            holder.itemResponseUpvoteButton!!.background = ContextCompat.getDrawable(mContext, R.drawable.ic_arrow_down_filled)
        }
        holder?.itemResponseDownvoteButton?.setOnClickListener {
            downvoteClickListener("")
            holder.itemResponseUpvoteButton!!.background = ContextCompat.getDrawable(mContext, R.drawable.ic_arrow_down_filled)
        }

        holder?.itemResponseVotesCounter?.text = "5"
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Holder {
        val itemView : View = from(parent?.context).inflate(R.layout.item_quest_response, parent, false)
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
        var itemResponseAuthorImageView: ImageView? = null
        var itemResponseUpvoteButton: Button? = null
        var itemResponseDownvoteButton: Button? = null
        var itemResponseVotesCounter: TextView? = null

        init {
            itemResponseAuthorImageView = itemView.findViewById(R.id.itemResponseAuthorImageView)
            itemResponseUpvoteButton = itemView.findViewById(R.id.itemResponseUpvoteButton)
            itemResponseDownvoteButton = itemView.findViewById(R.id.itemResponseDownvoteButton)
            itemResponseVotesCounter = itemView.findViewById(R.id.itemResponseVotesCounter)
        }
    }

    //endregion
}