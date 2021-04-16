package com.webtechsolution.ghumantey.ui.agency.addPackage

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.webtechsolution.ghumantey.data.ApiInterface
import com.webtechsolution.ghumantey.data.domain.PackagesList
import com.webtechsolution.ghumantey.data.domain.PostPackage
import com.webtechsolution.ghumantey.helpers.SingleEvent
import com.webtechsolution.ghumantey.helpers.base.BaseViewModel
import com.webtechsolution.ghumantey.helpers.set
import com.webtechsolution.ghumantey.helpers.toEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

data class AddPackageUiState(
    val toast:SingleEvent<String> = SingleEvent(),
    val packageList:PackagesList? = null,
    val success:SingleEvent<Unit> = SingleEvent()
)

class AddPackageViewModel @ViewModelInject constructor(val apiInterface: ApiInterface) : BaseViewModel() {
    private val _state = MutableLiveData(AddPackageUiState())
    val state = _state as LiveData<AddPackageUiState>
    fun updateNewPackageDetail(
        token: String,
        packageName: String, packagePrice: Int, destinationName: String,
        destinationDesc: String, destinationIternary: String, destinationIncluded: String,
        destinationExcluded: String, destinationPhone: Long, destinationEmail: String
    ) {
        val postPackage:PostPackage = PostPackage(destinationDesc,destinationName,destinationEmail,
        destinationExcluded,destinationIncluded,destinationIternary,packageName,destinationPhone,packagePrice)
        apiInterface.obtainPackagesList("Bearer "+token,postPackage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({packageList->
                _state.set {
                    it.copy(packageList = packageList,toast = SingleEvent("Package list updated"),success = Unit.toEvent())
                }
            },{
                _state.set {
                    it.copy(toast = SingleEvent("Updated failed"),success = Unit.toEvent())
                }
            }).isDisposed

    }
}