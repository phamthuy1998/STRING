package thuy.ptithcm.string.features.user.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_follow.*
import thuy.ptithcm.string.R
import thuy.ptithcm.string.features.user.adapter.FollowAdapter
import thuy.ptithcm.string.features.user.viewmodel.FollowViewModel
import thuy.ptithcm.string.utils.CURRENT_PER_PAGE
import thuy.ptithcm.string.utils.getAccessToken

class FollowFragment : Fragment() {
    companion object {
        private var instance: FollowFragment? = null
        fun getInstance(): FollowFragment {
            if (instance == null) instance =
                FollowFragment()
            return instance!!
        }
    }

    private val followAdapter: FollowAdapter by lazy {
        FollowAdapter(
            userFollowClick = { userID: Int, _: Boolean ->
                itemFollowClick(userID)
            },
            onLoadMore = {
                loadMoreUser()
            })
    }
    private val followViewModel: FollowViewModel by lazy {
        ViewModelProviders
            .of(activity!!)
            .get(FollowViewModel::class.java)
    }
    private var page = 1
    private var isLoadMore = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_follow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressbar_follow.visibility = View.VISIBLE
        context?.getAccessToken()?.let {
            followViewModel.getUserFollowList(it, page, CURRENT_PER_PAGE.toString())
        }
        inItViews()
        bindings()
        addEvents()
    }


    private fun itemFollowClick(id: Int) {
        activity?.getAccessToken()?.let { followViewModel.postFollowUser(it, id) }
    }

    private fun loadMoreUser() {
        isLoadMore = true
        page++
        context?.getAccessToken()?.let {
            followViewModel.getUserFollowList(it, page, CURRENT_PER_PAGE.toString())
        }
    }

    private fun addEvents() {
        swipeRefreshLayoutFollow.setOnRefreshListener {
            refreshLayout()
        }
    }

    private fun refreshLayout() {
        page = 1
        context?.getAccessToken()?.let {
            followViewModel.getUserFollowList(it, page, CURRENT_PER_PAGE.toString())
        }
        swipeRefreshLayoutFollow.isRefreshing = false
    }

    private fun inItViews() {
        swipeRefreshLayoutFollow.setColorSchemeResources(
            R.color.colorPurple
        )

        rv_user_follow.run {
            rv_user_follow.adapter = followAdapter
            rv_user_follow.layoutManager = LinearLayoutManager(context)
        }
    }

    private fun bindings() {
        followViewModel.listUserFollow.observe(this, Observer { listUserFollow ->

            if (listUserFollow != null) {
                progressbar_follow.visibility = View.GONE
                if (isLoadMore) {
                    listUserFollow.data?.let { followAdapter.upDateStories(it) }
                    isLoadMore = false
                } else {
                    listUserFollow.data?.let { followAdapter.addDataStories(it) }
                }
            }
        })

        followViewModel.resultDataFollow.observe(this, Observer {

        })
    }

}
