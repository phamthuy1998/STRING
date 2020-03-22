package thuy.ptithcm.string.features.feed


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialog
import kotlinx.android.synthetic.main.dialog_show_more_feed.view.*
import kotlinx.android.synthetic.main.fragment_feed.*
import kotlinx.android.synthetic.main.no_feed.*
import kotlinx.android.synthetic.main.no_internet.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import thuy.ptithcm.string.R
import thuy.ptithcm.string.events.FeedEvent
import thuy.ptithcm.string.events.TypeFeedEvent
import thuy.ptithcm.string.features.comment.CommentActivity
import thuy.ptithcm.string.features.feed.adapter.FeedAdapter
import thuy.ptithcm.string.features.feed.viewmodel.FeedViewModel
import thuy.ptithcm.string.features.post.PostDetailActivity
import thuy.ptithcm.string.utils.*


class FeedFragment : Fragment() {

    companion object {
        private var instance: FeedFragment? = null
        private var positionItem: Int = 0

        fun getInstance() = instance ?: FeedFragment()
    }


    private val feedAdapter: FeedAdapter by lazy {
        FeedAdapter(this::feedEvents)
    }

    private fun feedEvents(position: Int, type: TypeFeedEvent) {

        when (type) {
            TypeFeedEvent.SAVE -> {
                activity?.getAccessToken()
                    ?.let { feedViewModel.savePost(accessToken = it, id = id) }
            }

            TypeFeedEvent.STRING -> {
            }

            TypeFeedEvent.STRING_POI -> {
            }

            TypeFeedEvent.LIKE -> {
                activity?.getAccessToken()
                    ?.let {
                        feedViewModel.likePost(
                            accessToken = it,
                            id = feedViewModel.arrFeed.value?.get(position)?.id ?: 0
                        )
                    }
                feedViewModel.arrFeed.value?.get(position)?.isLiked =
                    !(feedViewModel.arrFeed.value?.get(position)?.isLiked ?: false)
            }

            TypeFeedEvent.COMMENT -> {
                onCommentClick(position)
            }

            TypeFeedEvent.SHOW_MORE -> {
                onShowMoreClick(position)
            }

            TypeFeedEvent.ITEM_CLICK -> {
                feedItemClick(position)
            }

        }
    }

    private val feedViewModel: FeedViewModel by lazy {
        ViewModelProviders.of(requireActivity()).get(FeedViewModel::class.java)
    }

    private val mLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(requireContext())
    }

    private fun onCommentClick(position: Int) {
        positionItem = position
        val intent = Intent(requireContext(), CommentActivity().javaClass)
        intent.putExtra("ID_Feed", feedViewModel.arrFeed.value?.get(position)?.id)
        startActivity(intent)
    }

    private fun feedItemClick(position: Int) {
        positionItem = position
        val feed = feedViewModel.arrFeed.value?.get(position)
        val intent = Intent(requireContext(), PostDetailActivity().javaClass)
        intent.putExtra("feed", feed)
        startActivity(intent)
    }

    private fun onShowMoreClick(id: Int) {
        // Show dialog
        val mBottomSheetDialog = RoundedBottomSheetDialog(requireContext())
        val dialog = layoutInflater.inflate(R.layout.dialog_show_more_feed, null)

        dialog.btn_cancel_dl_feed.setOnClickListener {
            mBottomSheetDialog.dismiss()
        }

        mBottomSheetDialog.setContentView(dialog)
        mBottomSheetDialog.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!requireContext().isNetworkAvailable()) {
            Toast.makeText(requireContext(), R.string.errConnection, Toast.LENGTH_LONG).show()
            feed_no_wifi.visible()
        } else {
            feed_no_wifi.gone()
        }
        initViews()
        bindings()
        addEvents()
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onFeedItemEvent(feedEvent: FeedEvent) {
        val feed = feedViewModel.arrFeed.value?.get(positionItem)
        if (feedEvent.isLike != null)
            feed?.isLiked = feedEvent.isLike
        if (feedEvent.likeCounter != null)
            feed?.likeCounter = feedEvent.likeCounter
        if (feedEvent.commentCounter != null)
            feed?.commentCounter = feedEvent.commentCounter
        feed?.let { feedViewModel.arrFeed.value?.set(positionItem, it) }
        feedAdapter.notifyItemChanged(positionItem)
    }

    private fun initViews() {
        rv_feed.adapter = feedAdapter
    }

    private fun addEvents() {
        swipe_refresh_feed.setOnRefreshListener {
            refreshLayout()
            swipe_refresh_feed.isRefreshing = false
        }

        btn_reload_no_wifi.setOnClickListener { refreshLayout() }
        btn_follow_people.setOnClickListener { followPeople() }

        var pastVisibleItems: Int
        var visibleItemCount: Int
        var totalItemCount: Int
        rv_feed.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0 && feedViewModel.isLoad.value == false && feedViewModel.isOutOfData.value == false) {
                    visibleItemCount = mLayoutManager.childCount
                    totalItemCount = mLayoutManager.itemCount
                    pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition()
                    if (visibleItemCount + pastVisibleItems >= totalItemCount - 5) {
                        // Get list of feed
                        feedViewModel.getFeedList(
                            activity?.getAccessToken() ?: "",
                            feedViewModel.page.value ?: 1
                        )
                    }
                }
            }
        })
    }

    private fun followPeople() {

    }

    private fun refreshLayout() {
        if (!requireContext().isNetworkAvailable()) {
            Toast.makeText(requireContext(), R.string.errConnection, Toast.LENGTH_LONG).show()
            feed_no_wifi.visible()
        } else {
            feed_no_wifi.gone()
            feedViewModel.page.value = 1
            activity?.getAccessToken()?.let {
                feedViewModel.getFeedList(it, feedViewModel.page.value ?: 1)
            }
        }
    }

    private fun bindings() {

        activity?.getAccessToken()?.let {
            feedViewModel.getFeedList(it, feedViewModel.page.value ?: 1)
        }

        // Login in another device
        feedViewModel.errorData.observe(this, Observer { isErr ->
            if (isErr == true) {
                showDialogErrorLogin()
            }
        })

        feedViewModel.arrFeed.observe(this, Observer { arrFeed ->
            if (arrFeed != null) {
                layout_no_feed.gone()
                feedAdapter.addFeedData(arrFeed)
            } else {
                layout_no_feed.visible()
            }
        })

        feedViewModel.resultSavePost.observe(this, Observer { data ->
            if (data != null) Toast.makeText(
                requireContext(),
                data.message,
                Toast.LENGTH_SHORT
            ).show()
        })

        feedViewModel.resultLikePost.observe(this, Observer { data ->
            if (data?.status == true) {
                Toast.makeText(requireContext(), data.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
