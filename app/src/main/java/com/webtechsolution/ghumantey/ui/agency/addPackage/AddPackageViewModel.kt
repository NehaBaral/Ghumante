package com.webtechsolution.ghumantey.ui.agency.addPackage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.webtechsolution.ghumantey.data.ApiInterface
import com.webtechsolution.ghumantey.data.Preferences
import com.webtechsolution.ghumantey.data.domain.PackagesList
import com.webtechsolution.ghumantey.data.domain.PackagesListItem
import com.webtechsolution.ghumantey.data.domain.PostPackage
import com.webtechsolution.ghumantey.helpers.SingleEvent
import com.webtechsolution.ghumantey.helpers.base.BaseViewModel
import com.webtechsolution.ghumantey.helpers.set
import com.webtechsolution.ghumantey.helpers.toEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

data class AddPackageUiState(
    val toast: SingleEvent<String> = SingleEvent(),
    val packageList: PackagesListItem? = null,
    val success: SingleEvent<Unit> = SingleEvent()
)

@HiltViewModel
class AddPackageViewModel @Inject constructor(val apiInterface: ApiInterface,val preference: Preferences) : BaseViewModel() {
    private val _state = MutableLiveData(AddPackageUiState())
    val state = _state as LiveData<AddPackageUiState>
    fun updateNewPackageDetail(
        packageName: String, packagePrice: Int, destinationName: String,
        destinationDesc: String, destinationIternary: String, destinationIncluded: String,
        destinationExcluded: String, destinationPhone: Long, destinationEmail: String
    ) {
        val postPackage: PostPackage = PostPackage(
            destinationDesc,
            destinationName,
            destinationEmail,
            destinationExcluded,
            destinationIncluded,
            destinationIternary,
            packageName,
            destinationPhone,
            packagePrice
        )
        apiInterface.obtainPackagesList("Bearer ${preference.authInfo?.token}", postPackage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ packageList ->
                _state.set {
                    it.copy(
                        packageList = packageList,
                        toast = SingleEvent("Package list updated"),
                        success = Unit.toEvent()
                    )
                }
            }, {
                it.message
                it.printStackTrace()
                _state.set {
                    it.copy(toast = SingleEvent("Updated failed"), success = Unit.toEvent())
                }
            }).isDisposed

    }
}