package thuy.ptithcm.string.features.interest.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_interest.view.*
import thuy.ptithcm.string.R
import thuy.ptithcm.string.model.Interest
import thuy.ptithcm.string.support.BaseViewHolder

class InterestAdapter(
    private val itemClick: (story: Interest) -> Unit
) : RecyclerView.Adapter<BaseViewHolder<*>>() {
    private var listInterest: ArrayList<Interest>? = arrayListOf()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): BaseViewHolder<*> {

        val view = LayoutInflater
            .from(viewGroup.context)
            .inflate(R.layout.item_interest, viewGroup, false)
        return StoryViewHolder(view, itemClick,listInterest)
    }

    override fun getItemCount(): Int {
        return listInterest?.size ?: 0
    }

    fun addDataStories(list: ArrayList<Interest>) {
        listInterest = arrayListOf()
        listInterest = list
        notifyDataSetChanged()
    }

    fun getListIDInterest(): ArrayList<Int>?{
        val arrIdInterest: ArrayList<Int> = ArrayList()
        for (item in listInterest!!) {
            if (item.checkUserSelect == 1) {
                item.id?.let { arrIdInterest.add(it) }
            }
        }
        return arrIdInterest
    }

    fun getSelectCount(): Int {
        var count = 0
        for (item in listInterest!!) {
            if (item.checkUserSelect == 1) {
                Log.d("select_interest", item.title.toString())
                count++
            }
        }
        return count
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val element = listInterest?.get(position)
        when (holder) {
            is StoryViewHolder -> holder.bind(element as Interest, position)
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    inner class StoryViewHolder(
        itemView: View,
        val itemClick: (interest: Interest) -> Unit,
        private var listInterest: ArrayList<Interest>?
    ) : BaseViewHolder<Interest>(itemView, listInterest) {
        override fun bind(item: Interest, position: Int) {
            Glide.with(itemView)
                .load(item.photo?.url?.medium)
                .error(R.drawable.no_image)
                .placeholder(R.drawable.ic_image_load)
                .into(itemView.iv_interest)

            itemView.tv_title_interest.text = item.title

            if (item.checkUserSelect == 1) {
                itemView.im_checkbox.background =
                    itemView.context.getDrawable(R.drawable.ic_checkbox_selected)
                itemView.cb_interest.background =
                    itemView.context.getDrawable(R.drawable.bg_purple_rounded)
            } else {
                itemView.im_checkbox.background =
                    itemView.context.getDrawable(R.drawable.ic_checkbox)
                itemView.cb_interest.background =
                    itemView.context.getDrawable(R.drawable.gradient_item_interest)
            }

            itemView.cb_interest.setOnClickListener {
                if (item.checkUserSelect == 1)
                    item.checkUserSelect = 0
                else
                    item.checkUserSelect = 1

                if (item.checkUserSelect == 1) {
                    itemView.im_checkbox.background =
                        itemView.context.getDrawable(R.drawable.ic_checkbox_selected)
                    itemView.cb_interest.background =
                        itemView.context.getDrawable(R.drawable.bg_purple_rounded)
                } else {
                    itemView.im_checkbox.background =
                        itemView.context.getDrawable(R.drawable.ic_checkbox)
                    itemView.cb_interest.background =
                        itemView.context.getDrawable(R.drawable.gradient_item_interest)
                }
                notifyItemChanged(position)
                item.let { it1 -> listInterest?.set(position, it1) }
                itemClick(item)
            }
        }
    }
}