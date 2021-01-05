package com.webtechsolution.ghumantey.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.webtechsolution.ghumantey.data.ApiInterface
import com.webtechsolution.ghumantey.data.RoomDB
import com.webtechsolution.ghumantey.data.model.DestinationListItem
import com.webtechsolution.ghumantey.helpers.SingleEvent
import com.webtechsolution.ghumantey.helpers.base.BaseViewModel
import com.webtechsolution.ghumantey.helpers.set
import com.webtechsolution.ghumantey.ui.packages.destinationUiState
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

data class destinationUiState(
    val destinationList: List<DestinationListItem> = emptyList(),
    val loading: Boolean = false,
    val showError: Boolean = false,
    val toast: SingleEvent<String> = SingleEvent()
)
class HomeScreenViewModel @ViewModelInject constructor(private val apiInterface: ApiInterface,private val roomDB: RoomDB) : BaseViewModel() {
    /*private val _state = MutableLiveData(listOf(
        DestinationModel("Gatthaghar Rent"),
        DestinationModel("Kausaltar Rent"),
        DestinationModel("Kausaltar Rent"),
        DestinationModel("Kausaltar Rent")
    ).map { it })

    val state = _state as LiveData<List<DestinationModel>>*/
    private val _state = MutableLiveData(destinationUiState())
    val state = _state as LiveData<destinationUiState>
    fun getDestinationList() {
        roomDB.destinationDao.getAllDestination()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _state.set { it.copy(loading = true) } }
            .subscribe({destinationList->
                _state.set {
                    it.copy(
                        destinationList = destinationList,
                    )
                }
            },{throwable->
                _state.set {
                    it.copy(
                        toast = SingleEvent("Database error")
                    )
                }
            }).isDisposed
        getServerDestination()
    }

    private fun getServerDestination() {
        apiInterface.getUserDestination()
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { _state.set { it.copy(loading = true) } }
            .flatMapCompletable {
                Completable.fromAction { roomDB.destinationDao.insertDestination(it) }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _state.set {
                    it.copy(
                        loading = false
                    )
                }
            },{throwable->
                _state.set {
                    it.copy(
                        loading = false,
                        toast = SingleEvent("Server error")
                    )
                }
            }).isDisposed
    }
}