package thuy.ptithcm.string.features.follow.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import thuy.ptithcm.string.model.UserData
import thuy.ptithcm.string.model.UserFollowData
import thuy.ptithcm.string.features.follow.service.FollowApiCaller

class FollowViewModel : ViewModel() {
    private val compo by lazy { CompositeDisposable() }
    private val apiManager: FollowApiCaller by lazy { FollowApiCaller() }
    val listUserFollow = MutableLiveData<UserFollowData>().apply { value = null }
    val resultDataFollow = MutableLiveData<UserData>().apply { value = null }

    fun getUserFollowList(
        accessToken: String,
        page: Int,
        currentPerPage: String
    ) {
        compo.add(
            apiManager.getUserList(accessToken, page, currentPerPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    listUserFollow.value = it
                }, {})
        )
    }

    fun postFollowUser(
        accessToken: String,
        userID: Int
    ) {
        compo.add(
            apiManager.postFollowUser(accessToken, userID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    resultDataFollow.value = it
                }, {})
        )
    }

}