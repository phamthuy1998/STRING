package thuy.ptithcm.string.features.post

import android.media.MediaPlayer.OnPreparedListener
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialog
import kotlinx.android.synthetic.main.activity_post_detail.*
import kotlinx.android.synthetic.main.dialog_show_more_feed.view.*
import thuy.ptithcm.string.R
import thuy.ptithcm.string.features.feed.viewmodel.FeedViewModel
import thuy.ptithcm.string.model.Feed
import thuy.ptithcm.string.support.ImageAdapter
import thuy.ptithcm.string.utils.*


class PostDetailActivity : AppCompatActivity() {
    private val feedViewModel: FeedViewModel by lazy {
        ViewModelProviders.of(this).get(FeedViewModel::class.java)
    }

    private var feed: Feed? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_detail)
        feed = intent.getParcelableExtra<Feed>("feed")
        bindings()
        initViews()
        addEvents()
    }

    private fun bindings() {
        feedViewModel.resultLikePost.observe(this, Observer { data ->
            if (data != null) Toast.makeText(
                this,
                data.message,
                Toast.LENGTH_SHORT
            ).show()
        })
        feedViewModel.resultLikePost.observe(this, Observer { data ->
            if (data != null) Toast.makeText(
                this,
                data.message,
                Toast.LENGTH_SHORT
            ).show()
        })
    }

    private fun setData(){
        setBoolean(IS_LIKE, feed?.isLiked ?: false)
        setInt(LIKE_COUNTER, feed?.likeCounter ?: 0)
        setInt(COMMENT_COUNTER, feed?.commentCounter ?: 0)
    }

    override fun onBackPressed() {
        setData()
        finish()
    }

    private fun addEvents() {
        btn_cancel_post_detail.setOnClickListener {
            setData()
            finish()
        }
        btn_like_post_detail.setOnClickListener {
            btn_like_post_detail.isSelected = !btn_like_post_detail.isSelected
            if (btn_like_post_detail.isSelected) {
                feed?.likeCounter = feed?.likeCounter?.plus(1)
            } else
                feed?.likeCounter = feed?.likeCounter?.minus(1)

            if (feed?.likeCounter != 0) {
                tv_like_post_detail.visible()
                tv_like_post_detail.text = feed?.likeCounter.toString()
            } else
                tv_like_post_detail.invisible()

            feed?.id?.let { it1 ->
                getAccessToken()?.let { it2 ->
                    feedViewModel.likePost(
                        it2,
                        it1
                    )
                }
            }
            feed?.isLiked = btn_like_post_detail.isSelected
        }

        btn_show_more_post_detail.setOnClickListener {
            // Show dialog
            val mBottomSheetDialog = RoundedBottomSheetDialog(this)
            val dialog = layoutInflater.inflate(R.layout.dialog_show_more_feed, null)

            dialog.btn_cancel_dl_feed.setOnClickListener {
                mBottomSheetDialog.dismiss()
            }

            mBottomSheetDialog.setContentView(dialog)
            mBottomSheetDialog.show()
        }
    }

    private fun initViews() {
        if (feed != null) {
            //Show video
            if (feed?.videos != null) {
                tv_image_count.gone()
                video_post.visible()
                view_pager_post_detail.gone()
                val video: Uri = Uri.parse(feed?.videos?.url ?: "")
                video_post.setVideoURI(video)
                video_post.setOnPreparedListener(OnPreparedListener { mp ->
                    mp.isLooping = true
                    video_post.start()
                })
            }
            // Show image
            else {
                tv_image_count.visible()
                view_pager_post_detail.visible()
                video_post.gone()
                view_pager_post_detail.adapter = ImageAdapter(this, feed?.photos, tv_image_count)
            }

            // Set image avatar
            Glide.with(this)
                .load(feed?.user?.profilePhoto)
                .error(R.drawable.ic_acc_img)
                .into(iv_avatar_post_detai)
            // User name
            txt_username_post_detail.text = feed?.user?.username

            // Like
            btn_like_post_detail.isSelected = feed?.isLiked!!
            if (feed?.likeCounter != 0) {
                tv_like_post_detail.visible()
                tv_like_post_detail.text = feed?.likeCounter.toString()
            } else
                tv_like_post_detail.invisible()

        }
    }
}
