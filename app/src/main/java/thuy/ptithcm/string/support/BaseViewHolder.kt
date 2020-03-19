package thuy.ptithcm.string.support

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T>(
    itemView: View,
    listItem: ArrayList<T>?
) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: T, position: Int)
}