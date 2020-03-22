package thuy.ptithcm.string.features.comment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_comment.view.*
import thuy.ptithcm.string.R
import thuy.ptithcm.string.model.ReplyComment
import thuy.ptithcm.string.support.BaseViewHolder

class ReplyCommentAdapter(
    private val listReplyCmt: ArrayList<ReplyComment>?= arrayListOf(),
    private val onUserTagClick: (userId: Int) -> Unit
) : RecyclerView.Adapter<BaseViewHolder<*>>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val view = LayoutInflater
            .from(viewGroup.context)
            .inflate(R.layout.item_comment, viewGroup, false)
        return ReplyCommentViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listReplyCmt?.size ?: 0
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val element = listReplyCmt?.get(position)
        when (holder) {
            is ReplyCommentViewHolder -> holder.bind(element as ReplyComment, position)
            else -> throw IllegalArgumentException()
        }
    }

    inner class ReplyCommentViewHolder(
        itemView: View
    ) : BaseViewHolder<ReplyComment>(itemView) {
        override fun bind(item: ReplyComment, position: Int) {
            // Avatar
            Glide.with(itemView)
                .load(item.user?.profilePhoto)
                .error(R.drawable.ic_acc_img)
                .into(itemView.iv_avatar_cmt)
            // User name
            itemView.txt_username_cmt.text = item.user?.username
            // Content comment
            itemView.tv_comment_content.text= item.description
        }
    }
}