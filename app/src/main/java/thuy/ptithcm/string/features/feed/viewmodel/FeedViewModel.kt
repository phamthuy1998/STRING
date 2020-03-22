package thuy.ptithcm.string.features.feed.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import thuy.ptithcm.string.features.feed.service.FeedApiCaller
import thuy.ptithcm.string.model.DataResult
import thuy.ptithcm.string.model.Feed

class FeedViewModel : ViewModel() {

    private val compo by lazy { CompositeDisposable() }
    private val apiManager: FeedApiCaller by lazy { FeedApiCaller() }
    val arrFeed = MutableLiveData<ArrayList<Feed>>().apply { value = arrayListOf() }
    val resultSavePost = MutableLiveData<DataResult>().apply { value = null }
    val resultLikePost = MutableLiveData<DataResult>().apply { value = null }
    var page = MutableLiveData<Int>().apply { value = 1 }
    var errorData = MutableLiveData<Boolean>().apply { value = false }
    var isLoad = MutableLiveData<Boolean>().apply { value = false }
    var isOutOfData = MutableLiveData<Boolean>().apply { value = false }


    fun getFeedList(
        accessToken: String,
        _page: Int = 1
    ) {
        isLoad.value = true
        compo.add(
            apiManager.getFeedList(accessToken, _page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.data != null) {
                        isOutOfData.value = false
                        errorData.value = false
                        if (page.value == 1) {
                            arrFeed.value = it.data
                        } else {
                            it?.data.let { it1 -> arrFeed.value?.addAll(it1) }
                        }
                        page.value = page.value?.plus(1)
                        isLoad.value = false
                    } else isOutOfData.value = true

                }, {
                    isLoad.value = false
                    errorData.value = true
                })
        )
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