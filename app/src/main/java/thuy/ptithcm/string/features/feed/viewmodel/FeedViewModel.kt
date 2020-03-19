package thuy.ptithcm.string.features.feed.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import thuy.ptithcm.string.features.feed.service.FeedApiCaller
import thuy.ptithcm.string.model.DataResult
import thuy.ptithcm.string.model.Feed
import thuy.ptithcm.string.model.FeedData

class FeedViewModel : ViewModel() {
    companion object {
        private var instance : FeedViewModel? = null
        fun getInstance() = instance ?: FeedViewModel()
    }

    private val compo by lazy { CompositeDisposable() }
    private val apiManager: FeedApiCaller by lazy { FeedApiCaller() }
    val feedData = MutableLiveData<FeedData>().apply { value = null }
    val resultSavePost = MutableLiveData<DataResult>().apply { value = null }
    val resultLikePost = MutableLiveData<DataResult>().apply { value = null }
    var page = MutableLiveData<Int>().apply { value = 1 }
    var errorData = MutableLiveData<Boolean>().apply { value = false }

    var feedItem = MutableLiveData<Feed>().apply { value = null }
    fun getFeedList(
        accessToken: String,
        _page: Int = 1
    ) {
        compo.add(
            apiManager.getFeedList(accessToken, _page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    errorData.value = false
                    feedData.value = it
                }, {
                    errorData.value = true
                })
        )
        page.value = page.value?.plus(1)
    }

    fun savePost(
        accessToken: String,
        id: Int
    ) {
        compo.add(
            apiManager.savePost(accessToken, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    resultSavePost.value = it
                    errorData.value = false
                }, {
                    errorData.value = true
                })
        )
    }

    fun likePost(
        accessToken: String,
        id: Int
    ) {
        compo.add(
            apiManager.likePost(accessToken, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    resultLikePost.value = it
                    errorData.value = false

                }, {
                    errorData.value = true
                })
        )
    }
}