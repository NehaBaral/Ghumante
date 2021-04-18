package com.webtechsolution.ghumantey.ui.myBooking

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.webtechsolution.ghumantey.data.ApiInterface
import com.webtechsolution.ghumantey.data.domain.AgencyPackageItem
import com.webtechsolution.ghumantey.data.model.DestinationModel
import com.webtechsolution.ghumantey.helpers.SingleEvent
import com.webtechsolution.ghumantey.helpers.base.BaseViewModel
import com.webtechsolution.ghumantey.helpers.set
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

data class MyBookingUiState(
    val toast: SingleEvent<String> = SingleEvent(),
    val myBookingList:List<AgencyPackageItem> = emptyList()
)

class MyBookingViewModel @ViewModelInject constructor(val apiInterface: ApiInterface) :
    BaseViewModel() {

    /*private val _state = MutableLiveData(listOf(
        DestinationModel("Gatthaghar Rent"),
        DestinationModel("Kausaltar Rent"),
        DestinationModel("Kausaltar Rent"),
        DestinationModel("Kausaltar Rent")
    ).map { it })

    val state = _state as LiveData<List<DestinationModel>>*/
    private val _state = MutableLiveData(MyBookingUiState())
    val state = _state as LiveData<MyBookingUiState>

    fun getBookingPackage(token: String) {
        apiInterface.getBookingPackage(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({bookingPackage->
                _state.set {
                    it.copy(myBookingList = bookingPackage,toast = SingleEvent("Booking list"))
                }
            }, {t->
                t.message
                _state.set {
                    it.copy(toast = SingleEvent("Server failed"))
                }
            }).isDisposed
    }
}