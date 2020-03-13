package thuy.ptithcm.string.features.feed.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import thuy.ptithcm.string.features.feed.model.FeedData
import thuy.ptithcm.string.features.feed.service.FeedApiCaller

class FeedViewModel : ViewModel() {
    private val compo by lazy { CompositeDisposable() }
    private val apiManager: FeedApiCaller by lazy { FeedApiCaller() }
    val feedData = MutableLiveData<FeedData>().apply { value = null }
    var page = MutableLiveData<Int>().apply { value = 1 }


    fun getUserFollowList(
        accessToken: String,
        _page: Int,
        currentPerPage: Int
    ) {
        compo.add(
            apiManager.getFeedList(accessToken, _page, currentPerPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    feedData.value = it
                    Log.d("bbb", feedData.value.toString())
                }, { err ->
                    Log.d("aaa", err.toString())
                })
        )
        page.value = page.value?.plus(1)
    }

}