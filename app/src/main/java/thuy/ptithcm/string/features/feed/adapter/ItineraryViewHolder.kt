package thuy.ptithcm.string.features.feed.adapter

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_feed_itinerary.view.*
import thuy.ptithcm.string.R
import thuy.ptithcm.string.events.TypeFeedEvent
import thuy.ptithcm.string.model.Feed
import thuy.ptithcm.string.support.BaseViewHolder
import thuy.ptithcm.string.utils.gone
import thuy.ptithcm.string.utils.invisible
import thuy.ptithcm.string.utils.visible

class ItineraryViewHolder(
    itemView: View,
    private val events: (position: Int, type: TypeFeedEvent) -> Unit
) : BaseViewHolder<Feed>(itemView) {

    override fun bind(item: Feed, position: Int) {
        val viewPool = RecyclerView.RecycledViewPool()

        itemView.rv_itinerary.apply {
            layoutManager =
                LinearLayoutManager(
                    itemView.rv_itinerary.context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            adapter = ItineraryAdapter(item.itineraries)
            setRecycledViewPool(viewPool)
        }

        itemView.tv_title_itinerary.text = item.title

        // Set avatar
        Glide.with(itemView)
            .load(item.user?.profilePhoto)
            .error(R.drawable.ic_acc_img)
            .into(itemView.iv_avatar_itinerary)
        itemView.txt_username_itinerary.text = item.user?.username

        // Root feed
        itemView.tv_description_itinerary.text = item.description
        if (item.strungFrom != null) {
            itemView.ll_acc_from.visible()
            // Avatar strung from
            Glide.with(itemView)
                .load(item.strungFrom.profilePhoto)
                .error(R.drawable.ic_acc_img)
                .into(itemView.iv_avatar_itinerary_from)
            itemView.txt_username_itinerary_from.text = item.strungFrom.username
        } else {
            itemView.ll_acc_from.gone()
        }

        // Like
        if (item.likeCounter != 0) {
            itemView.tv_like_count_itinerary.visible()
            itemView.tv_like_count_itinerary.text = item.likeCounter.toString()
        } else
            itemView.tv_like_count_itinerary.invisible()
        itemView.btn_like_itinerary.isSelected = item.isLiked!!

        // Comment
        if (item.commentCounter != 0) {
            itemView.tv_cmt_count_itinerary.visible()
            itemView.tv_cmt_count_itinerary.text = item.commentCounter.toString()
        } else
            itemView.tv_cmt_count_itinerary.invisible()
        // Button like click listener
        itemView.btn_like_itinerary.setOnClickListener {
            itemView.btn_like_itinerary.isSelected = !itemView.btn_like_itinerary.isSelected
            if (itemView.btn_like_itinerary.isSelected) {
                item.likeCounter = item.likeCounter?.plus(1)
            } else
                item.likeCounter = item.likeCounter?.minus(1)

            if (item.likeCounter != 0) {
                itemView.tv_like_count_itinerary.visible()
                itemView.tv_like_count_itinerary.text = item.likeCounter.toString()
            } else
                itemView.tv_like_count_itinerary.invisible()
            events.invoke(position, TypeFeedEvent.LIKE)
        }

        //  Button comment click
        itemView.btn_cmt_itinerary.setOnClickListener {
            events.invoke(position, TypeFeedEvent.COMMENT)
        }

        //  Button show more
        itemView.btn_show_more_itinerary.setOnClickListener {
            events.invoke(position, TypeFeedEvent.SHOW_MORE)
        }

        // Button String listener
        itemView.btn_string_itinerary.setOnClickListener {
            events.invoke(position, TypeFeedEvent.STRING)
        }
    }
}