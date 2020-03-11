package thuy.ptithcm.string.features.user.adapter

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.item_follow.view.*
import thuy.ptithcm.string.R
import thuy.ptithcm.string.features.user.model.Photo
import thuy.ptithcm.string.features.user.model.UserFollowData
import thuy.ptithcm.string.features.user.model.UserInfo
import thuy.ptithcm.string.support.BaseViewHolder

class FollowAdapter(
    private var listFollows: ArrayList<UserInfo>? = null,
    private val userFollowClick: (userID: Int, checkFollow: Boolean) -> Unit,
    private val onLoadMore: () -> Unit
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): BaseViewHolder<*> {

        val view = LayoutInflater
            .from(viewGroup.context)
            .inflate(R.layout.item_follow, viewGroup, false)
        return StoryViewHolder(view, userFollowClick)
    }

    override fun getItemCount(): Int {
        return listFollows?.size ?: 0
    }

    fun upDateStories(arrUser: ArrayList<UserInfo>) {
        if (arrUser.isNotEmpty()) {
            listFollows?.addAll(arrUser)
        }
        notifyDataSetChanged()
    }

    fun addDataStories(arrUser: ArrayList<UserInfo>) {
        if (arrUser.isNotEmpty()) {
            listFollows = arrUser
        }
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        if (position == (listFollows?.size?.minus(5)))
            onLoadMore()
        holder.bind(position)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    inner class StoryViewHolder(
        itemView: View,
        val userFollow: (userID: Int, checkFollow: Boolean) -> Unit
    ) : BaseViewHolder<View>(itemView) {
        override fun bind(position: Int) {
            val user = listFollows?.get(position)
            itemView.btn_follow.isSelected = user?.checkfollow ?: false

            if (itemView.btn_follow.isSelected) { // selected == true --> Follow --> button show Unfollow
                itemView.btn_follow.text = itemView.context.getString(R.string.un_follow)
                itemView.btn_follow.setTextColor(
                    ContextCompat.getColorStateList(
                        itemView.context,
                        R.color.colorPurple
                    )
                )
            } else { // selected == false Un follow --> button show follow
                itemView.btn_follow.text = itemView.context.getString(R.string.follow)
                itemView.btn_follow.setTextColor(
                    ContextCompat.getColorStateList(
                        itemView.context,
                        R.color.colorWhite
                    )
                )
            }
            itemView.btn_follow.setOnClickListener {
                itemView.btn_follow.isSelected = !itemView.btn_follow.isSelected
                user?.checkfollow = itemView.btn_follow.isSelected
                user?.let { it1 -> listFollows?.set(position, it1) }
                if (itemView.btn_follow.isSelected) { // Follow --> button show Unfollow
                    itemView.btn_follow.text = itemView.context.getString(R.string.un_follow)
                    itemView.btn_follow.setTextColor(
                        ContextCompat.getColorStateList(
                            itemView.context,
                            R.color.colorPurple
                        )
                    )
                } else { // Un follow --> button show follow
                    itemView.btn_follow.text = itemView.context.getString(R.string.follow)
                    itemView.btn_follow.setTextColor(
                        ContextCompat.getColorStateList(
                            itemView.context,
                            R.color.colorWhite
                        )
                    )
                }
                user?.id?.let { it1 -> userFollow(it1, itemView.btn_follow.isSelected) }
            }

            itemView.txt_username_follow.text = user?.username

            // Set image post
            val multiLeft = MultiTransformation<Bitmap>(
                RoundedCornersTransformation(7, 0, RoundedCornersTransformation.CornerType.ALL)
            )
            if (user?.photos != null) {
                itemView.ll_img_post.visibility = View.VISIBLE
                Glide.with(itemView)
                    .load(user.photos[0].url?.medium)
                    .apply(RequestOptions.bitmapTransform(multiLeft))
                    .into(itemView.iv_post1_follow)
                if (user.photos.size > 1) {
                    Glide.with(itemView)
                        .load(user.photos[1].url?.medium)
                        .error(R.drawable.no_image)
                        .into(itemView.iv_post2_follow)
                } else if (user.photos.size >= 2) {
                    Glide.with(itemView)
                        .load(user.photos[2].url?.medium)
                        .error(R.drawable.no_image)
                        .apply(RequestOptions.bitmapTransform(multiLeft))
                        .into(itemView.iv_post3_follow)
                }
            } else
                itemView.ll_img_post.visibility = View.GONE

            // Set image avatar
            Glide.with(itemView)
                .load(user?.profilePhoto)
                .error(R.drawable.ic_acc_img)
                .into(itemView.iv_avatar_follow)
        }
    }
}