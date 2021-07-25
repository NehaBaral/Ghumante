package com.webtechsolution.ghumantey.ui.agency.addPackage

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.webtechsolution.ghumantey.data.ApiInterface
import com.webtechsolution.ghumantey.data.Preferences
import com.webtechsolution.ghumantey.data.domain.PackagesListItem
import com.webtechsolution.ghumantey.data.domain.PostPackage
import com.webtechsolution.ghumantey.helpers.*
import com.webtechsolution.ghumantey.helpers.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import java.util.ArrayList
import javax.inject.Inject

data class AddPackageUiState(
    val toast: SingleEvent<String> = SingleEvent(),
    val packageList: PackagesListItem? = null,
    val success: SingleEvent<Unit> = SingleEvent(),
    val image: Uri? = null
)

@HiltViewModel
class AddPackageViewModel @Inject constructor(
    val apiInterface: ApiInterface,
    val preference: Preferences,
    val context: Context
) : BaseViewModel() {
    private val _state = MutableLiveData(AddPackageUiState())
    val state = _state as LiveData<AddPackageUiState>

    fun updateNewPackageDetail(
        packageName: String,
        packagePrice: Int,
        destinationName: String,
        destinationDesc: String,
        destinationIternary: String,
        destinationIncluded: String,
        destinationExcluded: String,
        destinationPhone: Long,
        destinationEmail: String,
        destinationDays: String
    ) {
        apiInterface.uploadImage(
            listOf(
                FormUtils.prepareFilePart(
                    "file",
                    state.value?.image,
                    context
                )
            )
        )
            .flatMap {
                val postPackage: PostPackage = PostPackage(
                    destinationDesc,
                    destinationName,
                    destinationEmail,
                    destinationExcluded,
                    destinationIncluded,
                    destinationIternary,
                    packageName,
                    destinationPhone,
                    packagePrice,
                    destinationDays,
                    it.fileName
                )
                apiInterface.obtainPackagesList("Bearer ${preference.authInfo?.token}", postPackage)
            }.subscribeOn(Schedulers.io())
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

    fun addImage(packageImage: Uri) {
        _state.update { copy(image = packageImage) }
    }
}