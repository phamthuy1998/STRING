package thuy.ptithcm.string.features.feed.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_itinerary.view.*
import thuy.ptithcm.string.R
import thuy.ptithcm.string.model.Itinerary
import thuy.ptithcm.string.support.BaseViewHolder

class ItineraryAdapter(
    private val listItinerary: ArrayList<Itinerary>?
) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val view = LayoutInflater
            .from(viewGroup.context)
            .inflate(R.layout.item_itinerary, viewGroup, false)
        return ItemItineraryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listItinerary?.size ?: 0
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val element = listItinerary?.get(position)
        when (holder) {
            is ItemItineraryViewHolder -> holder.bind(element as Itinerary, position)
            else -> throw IllegalArgumentException()
        }
    }


    inner class ItemItineraryViewHolder(
        itemView: View
    ) : BaseViewHolder<Itinerary>(itemView, listItinerary) {

        override fun bind(item: Itinerary, position: Int) {

            Glide.with(itemView)
                .load(item.photos?.url?.medium)
                .error(R.drawable.gradient_bg_itinerary)
                .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(20)))
                .into(itemView.img_itinerary)

            itemView.tv_location_itinerary.text = item.title
        }
    }
}
