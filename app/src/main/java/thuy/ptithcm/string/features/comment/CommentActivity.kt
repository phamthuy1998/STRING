package thuy.ptithcm.string.features.comment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_comment.*
import thuy.ptithcm.string.R
import thuy.ptithcm.string.features.comment.adapter.CommentAdapter
import thuy.ptithcm.string.features.comment.viewmodel.CommentViewModel
import thuy.ptithcm.string.utils.*

class CommentActivity : AppCompatActivity() {

    private val commentViewModel: CommentViewModel by lazy {
        ViewModelProviders.of(this).get(CommentViewModel::class.java)
    }

    private val commentAdapter: CommentAdapter by lazy {
        CommentAdapter(onUserTagClick = {

        })
    }

    private val mLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(this)
    }

    companion object {
        private var idFeed: Int = 0
        private var isRefresh: Boolean = false
        private var isLoad: Boolean = false
        private var isOutOfData: Boolean = false
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)
        idFeed = intent.getIntExtra("ID_Feed", -1)
        bindings()
        initViews()
        addEvents()
    }

    private fun initViews() {
        rv_comment.layoutManager = mLayoutManager
        rv_comment.adapter = commentAdapter

        // Set image avatar
        Glide.with(this)
            .load(getString(PROFILE_PHOTO))
            .error(R.drawable.ic_acc_img)
            .into(iv_avt_cmt)

    }

    private fun addEvents() {
        var pastVisibleItems: Int
        var visibleItemCount: Int
        var totalItemCount: Int
        rv_comment.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
                            getAccessToken()
                                ?.let {
                                    commentViewModel.getCommentList(
                                        it,
                                        commentViewModel.page.value ?: 1,
                                        idFeed
                                    )
                                }
                        }
                    }
                }
            }
        })

        srl_comment.setOnRefreshListener {
            refreshLayout()
            srl_comment.isRefreshing = false
        }

        btn_cancel_cmt.setOnClickListener { finish() }
        edt_comment.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (edt_comment.getTextTrim() != "") btn_post_cmt.visible() else btn_post_cmt.invisible()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
        btn_post_cmt.setOnClickListener { addComment() }
    }

    private fun addComment() {
//        commentViewModel.addComment(
//            idFeed,
//            edt_comment.getTextTrim(),
//            replyID,
//            null,
//            "",
//            accessToken
//        )
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun refreshLayout() {
        if (!isNetworkAvailable()) {
            Toast.makeText(this, R.string.errConnection, Toast.LENGTH_LONG).show()
            comment_no_wifi.visible()
        } else {
            isRefresh = true
            comment_no_wifi.gone()
            getAccessToken()?.let {
                commentViewModel.getCommentList(accessToken = it, id = idFeed)
            }
            commentViewModel.page.value = 1
        }
    }

    private fun bindings() {
        getAccessToken()?.let {
            commentViewModel.getCommentList(accessToken = it, id = idFeed)
        }

        commentViewModel.commentData.observe(this, Observer { commentData ->
            if (commentData != null) {
                if (commentData.status) {
                    ll_no_cmt.gone()
                    rv_comment.visible()
                    if (isRefresh) {
                        commentData.data?.let { commentAdapter.addFeedData(it) }
                        isRefresh = false
                    } else {
                        commentData.data?.let {
                            commentAdapter.updateFeedData(it)
                            isLoad = false
                        }
                    }

                    if (commentData.data?.size == 0)
                        isOutOfData = true

                    if (commentData.data?.size == 0 && commentAdapter.itemCount == 0) {
                        ll_no_cmt.visible()
                        rv_comment.gone()
                    }
                } else {
                    rv_comment.gone()
                    ll_no_cmt.visible()
                }
            }
        })

        commentViewModel.errorData.observe(this, Observer { isErr ->
            if (isErr == true) {
                showDialogErrorLogin()
            }
        })
    }
}
