package thuy.ptithcm.string.features.feed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import thuy.ptithcm.string.R
import thuy.ptithcm.string.events.TypeFeedEvent
import thuy.ptithcm.string.model.Feed
import thuy.ptithcm.string.support.BaseViewHolder
import thuy.ptithcm.string.utils.ITINERARY
import thuy.ptithcm.string.utils.POI
import thuy.ptithcm.string.utils.POST

class FeedAdapter(
    private val events: (position: Int, type: TypeFeedEvent)-> Unit
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var listFeed: ArrayList<Feed>? = arrayListOf()

    companion object {
        private const val TYPE_POST = 0
        private const val TYPE_POI = 1
        private const val TYPE_ITINERARY = 2
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return when (viewType) {
            TYPE_POST -> {
                val view = LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.item_feed_post, viewGroup, false)
                PostViewHolder(view, events)
            }
            TYPE_POI -> {
                val view = LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.item_feed_poi, viewGroup, false)
                PoiViewHolder(view, events)
            }
            TYPE_ITINERARY -> {
                val view = LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.item_feed_itinerary, viewGroup, false)
                ItineraryViewHolder(view, events)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int {
        return listFeed?.size ?: 0
    }

    fun addFeedData(arrFeed: ArrayList<Feed>?) {
        if (arrFeed != null && arrFeed.size > 0) {
            listFeed = arrFeed
            notifyDataSetChanged()
        }
    }

    fun updateFeedData(arrFeed: ArrayList<Feed>) {
        if (arrFeed.isNotEmpty()) {
            listFeed?.addAll(arrFeed)
            notifyDataSetChanged()
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {

        val element = listFeed?.get(position)
        when (holder) {
            is PostViewHolder -> holder.bind(element as Feed, position)
            is PoiViewHolder -> holder.bind(element as Feed, position)
            is ItineraryViewHolder -> holder.bind(element as Feed, position)
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = listFeed?.get(position)
        return when (item?.type) {
            POI -> TYPE_POI
            POST -> TYPE_POST
            ITINERARY -> TYPE_ITINERARY
            else -> throw IllegalArgumentException("Invalid type of data $position")
        }
    }

}