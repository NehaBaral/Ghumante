package com.webtechsolution.ghumantey.ui.packageDetail

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.webtechsolution.ghumantey.data.ApiInterface
import com.webtechsolution.ghumantey.data.domain.PackageDetail
import com.webtechsolution.ghumantey.data.domain.PackagesListItem
import com.webtechsolution.ghumantey.data.model.DestinationModel
import com.webtechsolution.ghumantey.helpers.SingleEvent
import com.webtechsolution.ghumantey.helpers.base.BaseViewModel
import com.webtechsolution.ghumantey.helpers.set
import com.webtechsolution.ghumantey.helpers.update
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

data class PackageDetailUiState(
    val packageDetail: PackageDetail? = null,
    val loading: Boolean = false,
    val showError: Boolean = false,
    val toast: SingleEvent<String> = SingleEvent()
)
@HiltViewModel
class PackageDetailViewModel @Inject constructor(val apiInterface: ApiInterface)  : BaseViewModel() {
    private val _pState = MutableLiveData(PackageDetailUiState())
    val pState = _pState as LiveData<PackageDetailUiState>
    init {
        _pState.set {
            it.copy()
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
                println("Error====" + throwable.message)
                _pState.update {
                    copy(
                        loading = false,
                        toast = SingleEvent("Server error")
                    )
                }
            }).isDisposed
    }
}