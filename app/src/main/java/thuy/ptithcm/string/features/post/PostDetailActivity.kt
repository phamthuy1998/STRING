package thuy.ptithcm.string.features.post

import android.content.Intent
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
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import thuy.ptithcm.string.R
import thuy.ptithcm.string.events.FeedEvent
import thuy.ptithcm.string.features.comment.CommentActivity
import thuy.ptithcm.string.features.feed.viewmodel.FeedViewModel
import thuy.ptithcm.string.model.Feed
import thuy.ptithcm.string.support.ImageAdapter
import thuy.ptithcm.string.utils.getAccessToken
import thuy.ptithcm.string.utils.gone
import thuy.ptithcm.string.utils.invisible
import thuy.ptithcm.string.utils.visible


class PostDetailActivity : AppCompatActivity() {
    private val feedViewModel: FeedViewModel by lazy {
        ViewModelProviders.of(this).get(FeedViewModel::class.java)
    }

    private var feed: Feed? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_detail)
        feed = intent.getParcelableExtra<Feed>("feed")
        EventBus.getDefault().register(this)
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onFeedItemEvent(feedEvent: FeedEvent) {
        tv_cmt_count_post_detail.text = feedEvent.commentCounter.toString()
    }


    private fun setData() {
        val feedEvent = FeedEvent(feed?.isLiked, feed?.likeCounter, feed?.commentCounter)
        EventBus.getDefault().post(feedEvent)
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

        btn_cmt_post_detail.setOnClickListener {
            val intent = Intent(this, CommentActivity().javaClass)
            intent.putExtra("ID_Feed", feed?.id)
            startActivity(intent)
        }

    }


    private fun initViews() {
        if (feed != null) {
            //Show video
            if (feed?.videos != null) {
                tv_image_count.gone()
                view_pager_post_detail.gone()
                video_post.visible()
                val video: Uri = Uri.parse(feed?.videos?.url ?: "")
                video_post.setVideoURI(video)
                video_post.setOnPreparedListener(OnPreparedListener { mp ->
                    mp.isLooping = true
                    video_post.start()
                })
            }
            // Show image
            else {
                video_post.gone()
                tv_image_count.visible()
                view_pager_post_detail.visible()
                view_pager_post_detail.adapter =
                    ImageAdapter(this, feed?.photos, tv_image_count)
            }

            // Set image avatar
            Glide.with(this)
                .load(feed?.user?.profilePhoto)
                .error(R.drawable.ic_acc_img)
                .into(iv_avatar_post_detai)
            // User name
            txt_username_post_detail.text = feed?.user?.username

            // Like
            btn_like_post_detail.isSelected = feed?.isLiked ?: false
            if (feed?.likeCounter != 0) {
                tv_like_post_detail.visible()
                tv_like_post_detail.text = feed?.likeCounter.toString()
            } else
                tv_like_post_detail.invisible()

            tv_cmt_count_post_detail.text = feed?.commentCounter.toString()
        }
    }
}
