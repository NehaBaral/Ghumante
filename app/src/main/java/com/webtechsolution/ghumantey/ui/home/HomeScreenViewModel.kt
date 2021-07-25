package com.webtechsolution.ghumantey.ui.home

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.webtechsolution.ghumantey.data.ApiInterface
import com.webtechsolution.ghumantey.data.Preferences
import com.webtechsolution.ghumantey.data.RoomDB
import com.webtechsolution.ghumantey.data.domain.AgencyPackageItem
import com.webtechsolution.ghumantey.helpers.SingleEvent
import com.webtechsolution.ghumantey.helpers.base.BaseViewModel
import com.webtechsolution.ghumantey.helpers.into
import com.webtechsolution.ghumantey.helpers.set
import com.webtechsolution.ghumantey.helpers.update
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

data class DestinationUiState(
    val destinationList: List<AgencyPackageItem> = emptyList(),
    val loading: Boolean = false,
    val showError: Boolean = false,
    val toast: SingleEvent<String> = SingleEvent()
)
@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val apiInterface: ApiInterface,private val roomDB: RoomDB,val preferences: Preferences) : BaseViewModel() {
    private val _state = MutableLiveData(DestinationUiState())
    val state = _state as LiveData<DestinationUiState>
    fun getDestinationList() {
        roomDB.destinationDao.getAllPackages()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _state.set { it.copy(loading = true) } }
            .subscribe({destinationList->
                _state.update {
                    copy(
                        destinationList = destinationList.distinctBy { it.destination }
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
            }).into(this)
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
                _state.update {
                    copy(
                        loading = false,
                        toast = SingleEvent("Server error")
                    )
                }
            }).into(this)
    }
}