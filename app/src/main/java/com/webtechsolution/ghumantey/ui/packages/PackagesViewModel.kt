package com.webtechsolution.ghumantey.ui.packages

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.webtechsolution.ghumantey.data.ApiInterface
import com.webtechsolution.ghumantey.data.RoomDB
import com.webtechsolution.ghumantey.data.domain.SearchBody
import com.webtechsolution.ghumantey.data.domain.SearchPackageItem
import com.webtechsolution.ghumantey.data.model.PackagesModelItem
import com.webtechsolution.ghumantey.helpers.SingleEvent
import com.webtechsolution.ghumantey.helpers.base.BaseViewModel
import com.webtechsolution.ghumantey.helpers.set
import com.webtechsolution.ghumantey.helpers.update
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

data class packagesUiState(
    val packagesList: List<SearchPackageItem> = emptyList(),
    val loading: Boolean = false,
    val showError: Boolean = false,
    val toast: SingleEvent<String> = SingleEvent()
)
class DestinationViewModel @ViewModelInject constructor(private val apiInterface: ApiInterface,private val roomDB: RoomDB)  : BaseViewModel() {
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
                println("Error====" + throwable.message)
                _state.update {
                    copy(
                        loading = false,
                        toast = SingleEvent("Server error")
                    )
                }
            }).isDisposed
    }
}