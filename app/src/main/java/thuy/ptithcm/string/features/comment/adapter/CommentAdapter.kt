package thuy.ptithcm.string.features.comment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_comment.view.*
import thuy.ptithcm.string.R
import thuy.ptithcm.string.model.Comment
import thuy.ptithcm.string.support.BaseViewHolder
import thuy.ptithcm.string.utils.gone
import thuy.ptithcm.string.utils.visible

class CommentAdapter(
    private val onUserTagClick: (userId: Int) -> Unit
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var listComment: ArrayList<Comment>? = arrayListOf()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val view = LayoutInflater
            .from(viewGroup.context)
            .inflate(R.layout.item_comment, viewGroup, false)
        return CommentViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listComment?.size ?: 0
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val element = listComment?.get(position)
        when (holder) {
            is CommentViewHolder -> holder.bind(element as Comment, position)
            else -> throw IllegalArgumentException()
        }
    }

    fun addFeedData(arrFeed: ArrayList<Comment>) {
        if (arrFeed.isNotEmpty()) {
            listComment = arrFeed
            notifyDataSetChanged()
        }
    }

    fun updateFeedData(arrFeed: ArrayList<Comment>) {
        if (arrFeed.isNotEmpty()) {
            listComment?.addAll(arrFeed)
            notifyDataSetChanged()
        }
    }

    inner class CommentViewHolder(
        itemView: View
    ) : BaseViewHolder<Comment>(itemView, listComment) {
        override fun bind(item: Comment, position: Int) {
            // Avatar
            Glide.with(itemView)
                .load(item.user?.profilePhoto)
                .error(R.drawable.ic_acc_img)
                .into(itemView.iv_avatar_cmt)
            // User name
            itemView.txt_username_cmt.text = item.user?.username
            // Content comment
            itemView.tv_comment_content.text = item.description

            if (item.replyComment != null&&item.replyComment.size!=0) {
                itemView.rv_comment_reply.visible()
                val viewPool = RecyclerView.RecycledViewPool()
                itemView.rv_comment_reply.apply {
                    adapter = ReplyCommentAdapter(item.replyComment, onUserTagClick)
                    setRecycledViewPool(viewPool)
                }
            }else{
                itemView.rv_comment_reply.gone()
            }
        }
    }
}