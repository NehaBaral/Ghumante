package com.webtechsolution.ghumantey.ui.packageDetail

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.webtechsolution.ghumantey.data.ApiInterface
import com.webtechsolution.ghumantey.data.Preferences
import com.webtechsolution.ghumantey.data.domain.Login
import com.webtechsolution.ghumantey.data.domain.PackageDetail
import com.webtechsolution.ghumantey.helpers.SingleEvent
import com.webtechsolution.ghumantey.helpers.base.BaseViewModel
import com.webtechsolution.ghumantey.helpers.into
import com.webtechsolution.ghumantey.helpers.update
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

data class PackageDetailUiState(
    val packageDetail: PackageDetail? = null,
    val loading: Boolean = false,
    val showError: Boolean = false,
    val toast: SingleEvent<String> = SingleEvent(),
    val loginData:Login?=null
)
@HiltViewModel
class PackageDetailViewModel @Inject constructor(val apiInterface: ApiInterface,val preferences: Preferences)  : BaseViewModel() {
    private val _pState = MutableLiveData(PackageDetailUiState())
    val pState = _pState as LiveData<PackageDetailUiState>
    init {
        _pState.update {
            copy(loginData = preferences.authInfo)
        }
    }

    fun getPackageDetail(args: PackageDetailFragmentArgs) {
        apiInterface.getPackageDetail(args.packageId)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { _pState.update { copy(loading = true) } }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({item->
                _pState.update {
                    copy(packageDetail = item)
                }
            }, { throwable ->
                throwable.printStackTrace()
                println("Error====" + throwable.message)
                _pState.update {
                    copy(
                        loading = false,
                        toast = SingleEvent("Server error")
                    )
                }
            }).into(this)
    }
}