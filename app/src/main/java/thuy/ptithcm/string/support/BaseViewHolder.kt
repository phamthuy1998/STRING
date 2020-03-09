package thuy.ptithcm.string.support

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<view : View>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun bind(position: Int)

}