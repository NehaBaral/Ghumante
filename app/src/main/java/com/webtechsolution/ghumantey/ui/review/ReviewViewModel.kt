package com.webtechsolution.ghumantey.ui.review

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.webtechsolution.ghumantey.data.ApiInterface
import com.webtechsolution.ghumantey.data.Preferences
import com.webtechsolution.ghumantey.data.domain.CommentBody
import com.webtechsolution.ghumantey.data.domain.PackagesListItem
import com.webtechsolution.ghumantey.helpers.SingleEvent
import com.webtechsolution.ghumantey.helpers.base.BaseViewModel
import com.webtechsolution.ghumantey.helpers.toEvent
import com.webtechsolution.ghumantey.helpers.update
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

data class ReviewUiState(
    val packageItem:PackagesListItem?=null,
    val toast: SingleEvent<String> = SingleEvent(),
    val reviewSuccess:Boolean = false
)
@HiltViewModel
class ReviewViewModel @Inject constructor(val apiInterface:ApiInterface,val preference:Preferences) : BaseViewModel() {

    private val  _state = MutableLiveData(ReviewUiState())
    val state = _state as LiveData<ReviewUiState>

    fun postReview(review: String, rating: Float, args: ReviewFragmentArgs) {
        val commentBody:CommentBody = CommentBody("nehaaaa",review,rating.toInt())
        val token = preference.authInfo?.token!!
        apiInterface.postComment("Bearer $token",args.packageId,commentBody)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                _state.update {
                    copy(
                        reviewSuccess = true
                    )
                }
            },{
                println("error=="+it.message)
                it.printStackTrace()
                _state.update {
                    copy(
                        reviewSuccess = true,
                        toast = SingleEvent("Couldn't update review")
                    )
                }
            }).isDisposed
    }
}