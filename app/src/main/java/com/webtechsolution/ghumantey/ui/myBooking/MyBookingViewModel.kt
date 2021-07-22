package com.webtechsolution.ghumantey.ui.myBooking

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.webtechsolution.ghumantey.data.ApiInterface
import com.webtechsolution.ghumantey.data.Preferences
import com.webtechsolution.ghumantey.data.domain.AgencyPackageItem
import com.webtechsolution.ghumantey.data.domain.BookingPackageItem
import com.webtechsolution.ghumantey.data.model.DestinationModel
import com.webtechsolution.ghumantey.helpers.SingleEvent
import com.webtechsolution.ghumantey.helpers.base.BaseViewModel
import com.webtechsolution.ghumantey.helpers.set
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

data class MyBookingUiState(
    val toast: SingleEvent<String> = SingleEvent(),
    val myBookingList: List<BookingPackageItem> = emptyList()
)

@HiltViewModel
class MyBookingViewModel @Inject constructor(
    val apiInterface: ApiInterface,
    val preferences: Preferences
) : BaseViewModel() {

    private val _state = MutableLiveData(MyBookingUiState())
    val state = _state as LiveData<MyBookingUiState>

    fun getBookingPackage() {
        val token = preferences.authInfo?.token!!
            apiInterface.getBookingPackage("Bearer $token")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ bookingPackage ->
                    _state.set {
                        it.copy(myBookingList = bookingPackage.bookings, toast = SingleEvent("Booking list"))
                    }
                }, { t ->
                    t.message
                    _state.set {
                        it.copy(toast = SingleEvent("Server failed"))
                    }
                }).isDisposed
    }
}