package thuy.ptithcm.string.features.user.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_interest.view.*
import thuy.ptithcm.string.R
import thuy.ptithcm.string.features.user.model.Interest
import thuy.ptithcm.string.support.BaseViewHolder

class InterestAdapter (
    private var listInterest: ArrayList<Interest>? = arrayListOf(),
//    var adapterEvent: AdapterEvent,
    val itemClick: (story: Interest) -> Unit
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): BaseViewHolder<*> {

        val view = LayoutInflater
            .from(viewGroup.context)
            .inflate(R.layout.item_interest, viewGroup, false)
        StoryViewHolder(view, itemClick)
        return StoryViewHolder(view, itemClick)
    }

    override fun getItemCount(): Int {
        return listInterest?.size ?: 0
    }

    fun addDataStories(list: ArrayList<Interest>) {
        listInterest = arrayListOf()
        listInterest = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        holder.bind(position)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    inner class StoryViewHolder(
        itemView: View,
        val itemClick: (interest: Interest) -> Unit
    ) : BaseViewHolder<View>(itemView) {
        override fun bind(position: Int) {
            val interest = listInterest?.get(position)
            Glide.with(itemView)
                .load(interest?.photo?.url?.medium)
                .error(R.drawable.no_image)
                .placeholder(R.drawable.ic_image_load)
                .into(itemView.iv_interest)
            itemView.tv_title_interest.text = interest?.title

            itemView.setOnClickListener {
                interest?.let { interest -> itemClick(interest) }
            }
        }
    }
}