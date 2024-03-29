package com.webtechsolution.ghumantey.ui.packages

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.webtechsolution.ghumantey.data.ApiInterface
import com.webtechsolution.ghumantey.data.RoomDB
import com.webtechsolution.ghumantey.data.domain.SearchBody
import com.webtechsolution.ghumantey.data.domain.SearchPackageItem
import com.webtechsolution.ghumantey.helpers.SingleEvent
import com.webtechsolution.ghumantey.helpers.base.BaseViewModel
import com.webtechsolution.ghumantey.helpers.into
import com.webtechsolution.ghumantey.helpers.set
import com.webtechsolution.ghumantey.helpers.update
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

data class packagesUiState(
    val packagesList: ArrayList<SearchPackageItem> = ArrayList(),
    val loading: Boolean = false,
    val showError: Boolean = false,
    val toast: SingleEvent<String> = SingleEvent()
){
    val list = packagesList.sortedBy {
        it.price
    }
}

@HiltViewModel
class DestinationViewModel @Inject constructor(
    private val apiInterface: ApiInterface,
    private val roomDB: RoomDB
) : BaseViewModel() {
    private val _state = MutableLiveData(packagesUiState())
    val state = _state as LiveData<packagesUiState>

    init {
        _state.set {
            it.copy()
        }
    }

    fun getDestinationList(args: PackagesFragmentArgs) {
        val searchBody: SearchBody = SearchBody(args.destinationId)
        apiInterface.getSearchPackages(searchBody)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { _state.update { copy(loading = true) } }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _state.update {
                    copy(
                        loading = false,
                        packagesList = it
                    )
                }
            }, { throwable ->
                throwable.printStackTrace()
                println("Error====" + throwable.message)
                _state.update {
                    copy(
                        loading = false,
                        toast = SingleEvent("Server error")
                    )
                }
            }).into(this)
    }
}