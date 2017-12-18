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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import loremipsumvirtualenterprise.quest.R
import loremipsumvirtualenterprise.quest.main.board.QuestsArrayAdapter
import loremipsumvirtualenterprise.quest.model.Quest
import loremipsumvirtualenterprise.quest.model.QuestResponse
import loremipsumvirtualenterprise.quest.model.QuestUser
import loremipsumvirtualenterprise.quest.util.DateHelper
import loremipsumvirtualenterprise.quest.util.FirebaseDatabaseUtil

/**
 * Created by root on 2017-12-10.
 */
class QuestResponsesArrayAdapter constructor(context: Context, objects: ArrayList<QuestResponse>, private val upvoteClickListener: (holder: Holder?, position: Int) -> Unit, private val downvoteClickListener: (holder: Holder?, position: Int) -> Unit) : RecyclerView.Adapter<QuestResponsesArrayAdapter.Holder>()
{
    //region Attributes

    private var mContext : Context = context
    private var mObjects : ArrayList<QuestResponse>? = objects

    //endregion

    //region Overridden Methods

    override fun onBindViewHolder(holder: Holder?, position: Int) {
        Glide.with(mContext)
                .load("https://vignette.wikia.nocookie.net/rickandmorty/images/8/8f/Rickk22.png")
                .apply(RequestOptions.circleCropTransform())
                .into(holder?.itemResponseAuthorImageView)

        holder?.itemResponseUpvoteButton?.setOnClickListener {
            upvoteClickListener(holder, position)
            holder.itemResponseUpvoteButton!!.background = ContextCompat.getDrawable(mContext, R.drawable.ic_arrow_down_filled)
        }
        holder?.itemResponseDownvoteButton?.setOnClickListener {
            downvoteClickListener(holder, position)
            holder.itemResponseUpvoteButton!!.background = ContextCompat.getDrawable(mContext, R.drawable.ic_arrow_down_filled)
        }

        // configure ui
        val questResponse: QuestResponse = mObjects!![position]
        holder?.itemResponseVotesCounter?.text = questResponse.votesValue().toString()
        holder?.itemQuestResponseText?.text = questResponse.text
        holder?.itemQuestPublishDate?.text = DateHelper.formatDate(questResponse.publishedAt)
        loadAuthorForHolder(holder, questResponse)
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

    // Helpers
    fun loadAuthorForHolder(holder: QuestResponsesArrayAdapter.Holder?, questResponse: QuestResponse) {

        val publisherDatabaseReference = FirebaseDatabaseUtil.usersNode?.child(questResponse.publisherUid!!)

        publisherDatabaseReference?.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.value != null) {
                    val questUser = QuestUser.createFromDataSnapshot(snapshot)
                    holder?.itemQuestResponseAuthor?.text = "@" + questUser.name
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })

    }

    //region Inner Class

    class Holder constructor(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        var itemResponseAuthorImageView: ImageView? = null
        var itemResponseUpvoteButton: Button? = null
        var itemResponseDownvoteButton: Button? = null
        var itemResponseVotesCounter: TextView? = null
        var itemQuestResponseAuthor: TextView? = null
        var itemQuestResponseText: TextView? = null
        var itemQuestPublishDate: TextView? = null

        init {
            itemResponseAuthorImageView = itemView.findViewById(R.id.itemResponseAuthorImageView)
            itemResponseUpvoteButton = itemView.findViewById<Button>(R.id.itemResponseUpvoteButton)
            itemResponseDownvoteButton = itemView.findViewById(R.id.itemResponseDownvoteButton)
            itemResponseVotesCounter = itemView.findViewById(R.id.itemResponseVotesCounter)
            itemQuestResponseAuthor = itemView.findViewById(R.id.itemQuestResponseAuthor)
            itemQuestResponseText = itemView.findViewById(R.id.itemQuestResponseText)
            itemQuestPublishDate = itemView.findViewById(R.id.itemQuestPublishDate)
        }
    }

    //endregion
}