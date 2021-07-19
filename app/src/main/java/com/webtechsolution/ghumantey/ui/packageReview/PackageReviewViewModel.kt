package com.webtechsolution.ghumantey.ui.packageReview

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.webtechsolution.ghumantey.data.ApiInterface
import com.webtechsolution.ghumantey.data.domain.PackagesListItem
import com.webtechsolution.ghumantey.helpers.SingleEvent
import com.webtechsolution.ghumantey.helpers.base.BaseViewModel
import com.webtechsolution.ghumantey.helpers.set
import com.webtechsolution.ghumantey.helpers.update
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

data class PackageReviewUiState(
    val packageItem: PackagesListItem?=null,
    val toast: SingleEvent<String> = SingleEvent(),
    val reviewSuccess:Boolean = false
)
@HiltViewModel
class PackageReviewViewModel @Inject constructor(val apiInterface: ApiInterface) : BaseViewModel() {
    private val _state = MutableLiveData(PackageReviewUiState())
    val state = _state as LiveData<PackageReviewUiState>

    fun updateReview(args: PackageReviewFragmentArgs) {
        apiInterface.getCommentList(args.packageId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _state.update {
                    copy(packageItem = it,reviewSuccess = true)
                }
            },{
                it.message
                _state.update {
                    copy(reviewSuccess = true,toast = SingleEvent("Server Error!"))
                }
            }).isDisposed
    }

}