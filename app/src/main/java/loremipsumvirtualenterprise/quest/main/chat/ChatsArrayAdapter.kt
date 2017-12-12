package loremipsumvirtualenterprise.quest.main.board

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater.*
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import loremipsumvirtualenterprise.quest.R
import loremipsumvirtualenterprise.quest.model.Chat
import loremipsumvirtualenterprise.quest.util.CircleTransform

/**
 * Created by root on 2017-11-29.
 */
class ChatsArrayAdapter constructor(context: Context, objects: ArrayList<Chat>?, val clickListener: (View.OnClickListener) -> Unit) : RecyclerView.Adapter<ChatsArrayAdapter.Holder>()
{
    //region Attributes

    private var mContext : Context = context
    private var mObjects : ArrayList<Chat>? = objects

    //endregion

    //region Overridden Methods

    override fun onBindViewHolder(holder: Holder?, position: Int) {
//        var questItem: Chat = mObjects!![position]

        Glide.with(mContext)
                .load("https://vignette.wikia.nocookie.net/rickandmorty/images/8/8f/Rickk22.png")
                .apply(RequestOptions.circleCropTransform())
                .into(holder?.chatProfilePictureImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Holder {
        val itemView : View = from(parent?.context).inflate(R.layout.item_chat, parent, false)
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

    class Holder constructor(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        var chatProfilePictureImageView : ImageView? = null

        init {
            chatProfilePictureImageView = itemView.findViewById(R.id.chatProfilePictureImageView)
        }
    }

    //endregion
}