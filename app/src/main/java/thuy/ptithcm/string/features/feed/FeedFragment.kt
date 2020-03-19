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
import thuy.ptithcm.string.R
import thuy.ptithcm.string.events.FeedEvents
import thuy.ptithcm.string.features.comment.CommentActivity
import thuy.ptithcm.string.features.feed.adapter.FeedAdapter
import thuy.ptithcm.string.features.feed.viewmodel.FeedViewModel
import thuy.ptithcm.string.features.post.PostDetailActivity
import thuy.ptithcm.string.model.Feed
import thuy.ptithcm.string.utils.*


class FeedFragment : Fragment(), FeedEvents {

    companion object {
        private var instance: FeedFragment? = null
        private var positionItem: Int = 0
        private var isClickItem = false

        private var isRefresh: Boolean = false
        private var isLoad: Boolean = false
        private var isOutOfData: Boolean = false

        fun getInstance() = instance ?: FeedFragment()
    }


    private val feedAdapter: FeedAdapter by lazy {
        FeedAdapter(this)
    }

    private val feedViewModel: FeedViewModel by lazy {
        ViewModelProviders.of(requireActivity()).get(FeedViewModel::class.java)
    }

    private val mLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(requireContext())
    }

    override fun onSaveClick(id: Int) {
        activity?.getAccessToken()?.let { feedViewModel.savePost(accessToken = it, id = id) }
    }

    override fun onStringClick(id: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLikeClick(id: Int) {
        activity?.getAccessToken()?.let { feedViewModel.likePost(accessToken = it, id = id) }
    }

    override fun onCommentClick(id: Int) {
        val intent = Intent(requireContext(), CommentActivity().javaClass)
        intent.putExtra("ID_Feed", id)
        startActivity(intent)
    }

    override fun feedItemClick(feed: Feed, position: Int) {
        isClickItem = true
        positionItem = position
        val intent = Intent(requireContext(), PostDetailActivity().javaClass)
        intent.putExtra("feed", feed)
        startActivity(intent)
    }

    override fun onShowMoreClick(id: Int) {
        // Show dialog
        val mBottomSheetDialog = RoundedBottomSheetDialog(requireContext())
        val dialog = layoutInflater.inflate(R.layout.dialog_show_more_feed, null)

        dialog.btn_cancel_dl_feed.setOnClickListener {
            mBottomSheetDialog.dismiss()
        }

        mBottomSheetDialog.setContentView(dialog)
        mBottomSheetDialog.show()
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

    override fun onResume() {
        super.onResume()
        if (isClickItem) {
            val feed = feedViewModel.feedData.value?.data?.get(positionItem)
            feed?.isLiked = requireContext().getBoolean(IS_LIKE)
            feed?.likeCounter = requireActivity().getInt(LIKE_COUNTER)
            feed?.commentCounter = requireActivity().getInt(COMMENT_COUNTER)
            feed?.let { feedViewModel.feedData.value?.data?.set(positionItem, it) }
            feedAdapter.notifyItemChanged(positionItem)
            isClickItem = false
        }
    }

    private fun initViews() {
        rv_feed.layoutManager = mLayoutManager
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
                if (dy > 0) //check for scroll down
                {
                    if (!isLoad && !isOutOfData) {
                        visibleItemCount = mLayoutManager.childCount
                        totalItemCount = mLayoutManager.itemCount
                        pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition()
                        if (visibleItemCount + pastVisibleItems >= totalItemCount - 5) {
                            isLoad = true
                            // Get list of feed
                            activity?.getAccessToken()
                                ?.let {
                                    feedViewModel.getFeedList(
                                        it,
                                        feedViewModel.page.value ?: 1
                                    )
                                }
                        }
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
            isRefresh = true
            feed_no_wifi.gone()
            activity?.getAccessToken()?.let {
                feedViewModel.getFeedList(it)
            }
            feedViewModel.page.value = 1
        }
    }

    private fun bindings() {
        activity?.getAccessToken()?.let {
            feedViewModel.getFeedList(it)
        }
        feedViewModel.errorData.observe(this, Observer { isErr ->
            if (isErr == true) {
                showDialogErrorLogin()
            }
        })
        feedViewModel.feedData.observe(this, Observer { feedData ->
            if (feedData != null) {
                layout_no_feed.gone()
                if (isRefresh) {
                    feedData.data?.let { feedAdapter.addFeedData(it) }
                    isRefresh = false
                } else {
                    feedData.data?.let {
                        feedAdapter.updateFeedData(it)
                        isLoad = false
                    }
                }
                if (feedData.data?.size == 0) {
                    isOutOfData = true
                    Toast.makeText(requireContext(), "Out of data!", Toast.LENGTH_LONG).show()
                }
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
            if (data != null) Toast.makeText(
                requireContext(),
                data.message,
                Toast.LENGTH_SHORT
            ).show()
        })
    }
}