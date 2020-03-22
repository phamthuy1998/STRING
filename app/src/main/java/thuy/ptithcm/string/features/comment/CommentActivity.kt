package thuy.ptithcm.string.features.comment

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialog
import kotlinx.android.synthetic.main.activity_comment.*
import kotlinx.android.synthetic.main.dialog_cofirm_comment_del.view.*
import kotlinx.android.synthetic.main.dialog_comment_more.view.*
import kotlinx.android.synthetic.main.dialog_user_comment_more.view.*
import kotlinx.android.synthetic.main.dialog_user_comment_more.view.btn_del_user_cmt
import org.greenrobot.eventbus.EventBus
import thuy.ptithcm.string.R
import thuy.ptithcm.string.events.FeedEvent
import thuy.ptithcm.string.features.comment.adapter.CommentAdapter
import thuy.ptithcm.string.features.comment.viewmodel.CommentViewModel
import thuy.ptithcm.string.model.Comment
import thuy.ptithcm.string.utils.*

class CommentActivity : AppCompatActivity() {

    private val commentViewModel: CommentViewModel by lazy {
        ViewModelProviders.of(this).get(CommentViewModel::class.java)
    }

    private val commentAdapter: CommentAdapter by lazy {
        CommentAdapter(onUserTagClick = {

        }, onCommentMoreClick = { comment ->
            onShowMoreCommentClick(comment)
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

    private fun onShowMoreCommentClick(comment: Comment) {
        if (comment.user?.username == getString(USER_NAME)) {
            // Show dialog
            val mBottomSheetDialog = RoundedBottomSheetDialog(this)
            val dialog = layoutInflater.inflate(R.layout.dialog_user_comment_more, null)

            dialog.btn_cacel_user_cmt_more.setOnClickListener {
                mBottomSheetDialog.dismiss()
            }

            dialog.btn_del_user_cmt.setOnClickListener {
                showDialogConfirmDel(comment.id)
                mBottomSheetDialog.dismiss()
            }

            mBottomSheetDialog.setContentView(dialog)
            mBottomSheetDialog.show()
        } else {
            // Show dialog
            val mBottomSheetDialog = RoundedBottomSheetDialog(this)
            val dialog = layoutInflater.inflate(R.layout.dialog_comment_more, null)

            dialog.btn_cancel_cmt_more.setOnClickListener {
                mBottomSheetDialog.dismiss()
            }

            mBottomSheetDialog.setContentView(dialog)
            mBottomSheetDialog.show()
        }
    }

    private fun showDialogConfirmDel(id: Int?) {
        val mDialogView = LayoutInflater.from(this)
            .inflate(R.layout.dialog_cofirm_comment_del, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setCancelable(false) //click outside = false

        val mAlertDialog = mBuilder.show()
        // Del comment
        mDialogView.btn_del_user_cmt.setOnClickListener {
            commentViewModel.deleteComment(id, idFeed, getAccessToken())
            mAlertDialog.dismiss()
        }
        mDialogView.btn_cancel_confirm_del_cmt.setOnClickListener {
            mAlertDialog.dismiss()
        }
        mDialogView.btn_cancel_del_cmt.setOnClickListener {
            mAlertDialog.dismiss()
        }

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
        btn_post_cmt.setOnClickListener {
            addComment()
            it.hideKeyboard()
        }
    }

    private fun addComment() {
        commentViewModel.addComment(
            idFeed,
            edt_comment.getTextTrim(),
            null,
            null,
            null,
            getAccessToken()
        )
        edt_comment.text.clear()
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
        var feedEvent: FeedEvent

        commentViewModel.commentData.observe(this, Observer { commentData ->
            if (commentData?.status == true) {
                ll_no_cmt.gone()
                rv_comment.visible()

                if (isRefresh) {
                    commentAdapter.addFeedData(commentData.data)
                    isRefresh = false
                } else {
                    commentAdapter.updateFeedData(commentData.data)
                    isLoad = false
                }
                if (commentData.data?.size == 0)
                    isOutOfData = true


                if (commentData.data?.size == 0 && commentAdapter.itemCount == 0) {
                    ll_no_cmt.visible()
                    rv_comment.gone()
                }
            }
        })

        commentViewModel.errorData.observe(this, Observer { isErr ->
            if (isErr == true) {
                showDialogErrorLogin()
            }
        })

        commentViewModel.cmtResult.observe(this, Observer { resultAddCmt ->
            if (resultAddCmt?.status == true) {
                getAccessToken()?.let {
                    commentViewModel.getCommentList(accessToken = it, id = idFeed, _page = 1)
                    commentViewModel.page.value = 1
                    isRefresh = true
                    feedEvent = FeedEvent(null, null, commentViewModel.commentData.value?.data?.size?.plus(1))
                    EventBus.getDefault().post(feedEvent)
                }
            }
        })
    }
}
