package com.webtechsolution.ghumantey.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.webtechsolution.ghumantey.data.ApiInterface
import com.webtechsolution.ghumantey.data.RoomDB
import com.webtechsolution.ghumantey.data.domain.PackagesListItem
import com.webtechsolution.ghumantey.data.domain.SearchBody
import com.webtechsolution.ghumantey.helpers.SingleEvent
import com.webtechsolution.ghumantey.helpers.base.BaseViewModel
import com.webtechsolution.ghumantey.helpers.set
import com.webtechsolution.ghumantey.helpers.update
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

data class destinationUiState(
    val destinationList: List<PackagesListItem> = emptyList(),
    val loading: Boolean = false,
    val showError: Boolean = false,
    val toast: SingleEvent<String> = SingleEvent()
)
class HomeScreenViewModel @ViewModelInject constructor(private val apiInterface: ApiInterface,private val roomDB: RoomDB) : BaseViewModel() {
    private val _state = MutableLiveData(destinationUiState())
    val state = _state as LiveData<destinationUiState>
    fun getDestinationList() {
        roomDB.destinationDao.getAllPackages()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _state.set { it.copy(loading = true) } }
            .subscribe({destinationList->
                _state.update {
                    copy(
                        destinationList = destinationList
                    )
                }
            },{throwable->
                throwable.message
                throwable.printStackTrace()
                _state.update {
                    copy(
                        toast = SingleEvent("Database error")
                    )
                }
            }).isDisposed
        getServerDestination()
    }

    private fun getServerDestination(){
        apiInterface.getPackagesList()
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { _state.update { copy(loading = true) } }
            .flatMapCompletable {
                Completable.fromAction { roomDB.destinationDao.insertPackage(it) }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _state.update {
                    copy(
                        loading = false
                    )
                }
            }, { throwable ->
                println("Error====" + throwable.message)
                println("Errrr==="+throwable.printStackTrace())
                _state.update {
                    copy(
                        loading = false,
                        toast = SingleEvent("Server error")
                    )
                }
            }).isDisposed
    }
}