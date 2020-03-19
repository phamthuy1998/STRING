package thuy.ptithcm.string.features.feed.adapter

import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_feed_poi.view.*
import thuy.ptithcm.string.R
import thuy.ptithcm.string.events.FeedEvents
import thuy.ptithcm.string.model.Feed
import thuy.ptithcm.string.support.BaseViewHolder
import thuy.ptithcm.string.utils.invisible
import thuy.ptithcm.string.utils.visible

class PoiViewHolder(
    itemView: View,
    private val listFeed: ArrayList<Feed>?,
    val feedEvents: FeedEvents
) : BaseViewHolder<Feed>(itemView, listFeed) {

    override fun bind(item: Feed, position: Int) {
        itemView.tv_title_poi.text = item.title
        itemView.tv_location_poi.text = item.address

        // Avatar
        Glide.with(itemView)
            .load(item.user?.profilePhoto)
            .error(R.drawable.ic_acc_img)
            .into(itemView.iv_avatar_poi)
        itemView.txt_username_poi.text = item.user?.username

        // Todo --> change image into list image slider
        Glide.with(itemView)
            .load(item.photos?.get(0)?.url?.medium)
            .error(R.drawable.no_image)
            .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(20)))
            .into(itemView.img_poi)

        // Comment
        if (item.commentCounter != 0) {
            itemView.tv_cmt_count_poi.visible()
            itemView.tv_cmt_count_poi.text = item.commentCounter.toString()
        } else
            itemView.tv_cmt_count_poi.invisible()
        if (item.strungCounter != 0)
            itemView.btn_string_poi.text = item.strungCounter.toString()

        // Like
        if (item.likeCounter != 0) {
            itemView.tv_like_count_poi.visible()
            itemView.tv_like_count_poi.text = item.likeCounter.toString()
        } else
            itemView.tv_like_count_poi.invisible()
        itemView.btn_like_poi.isSelected = item.isLiked!!

        // Button like click listener
        itemView.btn_like_poi.setOnClickListener {
            itemView.btn_like_poi.isSelected = !itemView.btn_like_poi.isSelected
            if (itemView.btn_like_poi.isSelected) {
                item.likeCounter = item.likeCounter?.plus(1)
            } else
                item.likeCounter = item.likeCounter?.minus(1)

            if (item.likeCounter != 0) {
                itemView.tv_like_count_poi.visible()
                itemView.tv_like_count_poi.text = item.likeCounter.toString()
            } else
                itemView.tv_like_count_poi.invisible()
            listFeed?.set(position, item)
            item.id?.let { it1 ->  feedEvents.onLikeClick(it1) }

        }

        //  Button comment click
        itemView.btn_cmt_poi.setOnClickListener {
            item.id?.let { it1 ->  feedEvents.onCommentClick(it1) }
        }

        //  Button show more
        itemView.btn_show_more_poi.setOnClickListener {
            item.id?.let { it1 ->  feedEvents.onShowMoreClick(it1) }
        }

        // Button String listener
        itemView.btn_string_poi.setOnClickListener {
            item.id?.let { it1 ->  feedEvents.onStringClick(it1) }
        }

    }
}