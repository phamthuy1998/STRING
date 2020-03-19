package thuy.ptithcm.string.features.feed.adapter

import android.view.View
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_feed_post.view.*
import thuy.ptithcm.string.R
import thuy.ptithcm.string.events.FeedEvents
import thuy.ptithcm.string.model.Feed
import thuy.ptithcm.string.support.BaseViewHolder
import thuy.ptithcm.string.support.RoundedCornersTransformation
import thuy.ptithcm.string.utils.convertIntoTime
import thuy.ptithcm.string.utils.gone
import thuy.ptithcm.string.utils.invisible
import thuy.ptithcm.string.utils.visible


class PostViewHolder(
    itemView: View,
    private var listFeed: ArrayList<Feed>?,
    private val feedEvents: FeedEvents
) : BaseViewHolder<Feed>(itemView, listFeed) {

    override fun bind(item: Feed, position: Int) {
        if (item.videos != null) {
            itemView.tv_time_duration.visible()
            itemView.tv_time_duration.text = item.videos.duration?.let { convertIntoTime(it) }
            itemView.btn_play_video.visible()
            itemView.ll_image_feed.visible()
            itemView.ll_img_post_bot.gone()
            Glide.with(itemView)
                .load(item.videos.thumbs)
                .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(20)))
                .into(itemView.img_post_top)
        }
        // Load image post
        item.photos?.let { photos ->
            itemView.tv_time_duration.gone()
            itemView.btn_play_video.gone()
            itemView.ll_image_feed.visible()
            when (photos.size) {
                1 -> {
                    itemView.ll_img_post_bot.gone()
                    Glide.with(itemView)
                        .load(photos[0].url?.medium)
                        .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(20)))
                        .into(itemView.img_post_top)
                }
                2 -> {
                    Glide.with(itemView)
                        .load(photos[0].url?.medium)
                        .apply(
                            RequestOptions().transform(
                                CenterCrop(),
                                RoundedCornersTransformation(
                                    itemView.context,
                                    20,
                                    0,
                                    RoundedCornersTransformation.CornerType.TOP
                                )
                            )
                        )
                        .into(itemView.img_post_top)

                    itemView.ll_img_post_bot.visible()
                    itemView.img_post_right.gone()
                    itemView.line_img_bot.gone()
                    Glide.with(itemView)
                        .load(photos[1].url?.medium)
                        .apply(
                            RequestOptions().transform(
                                CenterCrop(),
                                RoundedCornersTransformation(
                                    itemView.context,
                                    20,
                                    0,
                                    RoundedCornersTransformation.CornerType.BOTTOM
                                )
                            )
                        )
                        .error(R.drawable.no_image)
                        .into(itemView.img_post_left)
                }
                else -> {
                    itemView.ll_img_post_bot.visible()
                    Glide.with(itemView)
                        .load(photos[0].url?.medium).apply(
                            RequestOptions().transform(
                                CenterCrop(),
                                RoundedCornersTransformation(
                                    itemView.context,
                                    20,
                                    0,
                                    RoundedCornersTransformation.CornerType.TOP
                                )
                            )
                        )
                        .into(itemView.img_post_top)
                    itemView.img_post_left.visible()
                    itemView.img_post_right.visible()
                    Glide.with(itemView)
                        .load(photos[1].url?.medium)
                        .apply(
                            RequestOptions().transform(
                                CenterCrop(),
                                RoundedCornersTransformation(
                                    itemView.context,
                                    20,
                                    0,
                                    RoundedCornersTransformation.CornerType.BOTTOM_LEFT
                                )
                            )
                        )
                        .error(R.drawable.no_image)
                        .into(itemView.img_post_left)
                    Glide.with(itemView)
                        .load(photos[2].url?.medium)
                        .apply(
                            RequestOptions().transform(
                                CenterCrop(),
                                RoundedCornersTransformation(
                                    itemView.context,
                                    20,
                                    0,
                                    RoundedCornersTransformation.CornerType.BOTTOM_RIGHT
                                )
                            )
                        )
                        .error(R.drawable.no_image)
                        .into(itemView.img_post_right)
                }
            }
        }

        // Set image avatar
        Glide.with(itemView)
            .load(item.user?.profilePhoto)
            .error(R.drawable.ic_acc_img)
            .into(itemView.iv_avatar_feed_post)

        // Set text view
        itemView.txt_username_feed.text = item.user?.username
        itemView.tv_time_post.text = item.updatedAt
        itemView.tv_description_feed.text = item.description
        itemView.tv_location_feed.text = item.place?.title

        // Set status button save
        itemView.btn_save_post.text = item.saveCounter.toString()
        if (item.isSaved == true) { // Saved
            itemView.btn_save_post.isSelected = true
            itemView.btn_save_post.setTextColor(
                ContextCompat.getColorStateList(
                    itemView.context,
                    R.color.colorWhite
                )
            )
            itemView.btn_save_post.setCompoundDrawablesWithIntrinsicBounds(
                itemView.context.getDrawable(R.drawable.ic_save_selected), null, null, null
            )
        } else { // Not save
            itemView.btn_save_post.isSelected = false
            itemView.btn_save_post.setTextColor(
                ContextCompat.getColorStateList(
                    itemView.context,
                    R.color.colorPurple
                )
            )
            itemView.btn_save_post.setCompoundDrawablesWithIntrinsicBounds(
                itemView.context.getDrawable(R.drawable.ic_save), null, null, null
            )
        }

        // Comment
        if (item.commentCounter != 0) {
            itemView.tv_cmt_count.visible()
            itemView.tv_cmt_count.text = item.commentCounter.toString()
        } else
            itemView.tv_cmt_count.invisible()

        // Like
        itemView.btn_like_post.isSelected = item.isLiked!!
        if (item.likeCounter != 0) {
            itemView.tv_like_count.visible()
            itemView.tv_like_count.text = item.likeCounter.toString()
        } else
            itemView.tv_like_count.invisible()

        addEvents(item, position)
    }

    private fun addEvents(item: Feed, position: Int) {
        //  Button comment click
        itemView.btn_cmt_post.setOnClickListener {
            item.id?.let { it1 -> feedEvents.onCommentClick(it1) }
        }

        //  Button show more
        itemView.btn_show_more_post.setOnClickListener {
            item.id?.let { it1 -> feedEvents.onShowMoreClick(it1) }
        }

        // Button like click listener
        itemView.btn_like_post.setOnClickListener {
            itemView.btn_like_post.isSelected = !itemView.btn_like_post.isSelected
            if (itemView.btn_like_post.isSelected) {
                item.likeCounter = item.likeCounter?.plus(1)
            } else
                item.likeCounter = item.likeCounter?.minus(1)

            if (item.likeCounter != 0) {
                itemView.tv_like_count.visible()
                itemView.tv_like_count.text = item.likeCounter.toString()
            } else
                itemView.tv_like_count.invisible()
            listFeed?.set(position, item)
            item.id?.let { it1 -> feedEvents.onLikeClick(it1) }
        }

        // Button save listener
        itemView.btn_save_post.setOnClickListener {
            itemView.btn_save_post.isSelected = !itemView.btn_save_post.isSelected
            item.isSaved = itemView.btn_save_post.isSelected
            if (itemView.btn_save_post.isSelected) { // saved
                item.saveCounter = item.saveCounter?.plus(1)
                itemView.btn_save_post.text = item.saveCounter.toString()
                itemView.btn_save_post.setTextColor(
                    ContextCompat.getColorStateList(
                        itemView.context,
                        R.color.colorWhite
                    )
                )
                itemView.btn_save_post.setCompoundDrawablesWithIntrinsicBounds(
                    itemView.context.getDrawable(R.drawable.ic_save_selected), null, null, null
                )
            } else { //not save
                item.saveCounter = item.saveCounter?.minus(1)
                itemView.btn_save_post.text = item.saveCounter.toString()
                itemView.btn_save_post.setTextColor(
                    ContextCompat.getColorStateList(
                        itemView.context,
                        R.color.colorPurple
                    )
                )
                itemView.btn_save_post.setCompoundDrawablesWithIntrinsicBounds(
                    itemView.context.getDrawable(R.drawable.ic_save), null, null, null
                )
            }
            listFeed?.set(position, item)
            item.id?.let { it1 -> feedEvents.onSaveClick(it1) }
        }

        // Image listener
        itemView.ll_image_feed.setOnClickListener {
            feedEvents.feedItemClick(item, position)
        }

        itemView.btn_play_video.setOnClickListener { feedEvents.feedItemClick(item, position) }

    }
}