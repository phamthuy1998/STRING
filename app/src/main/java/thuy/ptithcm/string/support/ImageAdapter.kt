package thuy.ptithcm.string.support

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import thuy.ptithcm.string.R
import thuy.ptithcm.string.model.Photo


class ImageAdapter(
    private val context: Context,
    private val listImage: ArrayList<Photo>?,
    private val tvImageCount: TextView
) :
    PagerAdapter() {
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as ImageView
    }

    override fun getCount(): Int {
        return listImage?.size ?: 0
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        tvImageCount.text = context.getString(R.string.current_per_page_image, position, count)
        val imageView = ImageView(context)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        Glide.with(context)
            .load(listImage?.get(position)?.url?.original)
            .into(imageView)
        (container as ViewPager).addView(imageView, 0)
        return imageView
    }


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        (container as ViewPager).removeView(`object` as ImageView)
    }
}