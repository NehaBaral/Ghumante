package com.webtechsolution.ghumantey.ui.agency.agencyBooking

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.webtechsolution.ghumantey.data.ApiInterface
import com.webtechsolution.ghumantey.data.domain.AgencyPackageItem
import com.webtechsolution.ghumantey.helpers.SingleEvent
import com.webtechsolution.ghumantey.helpers.base.BaseViewModel
import com.webtechsolution.ghumantey.helpers.set
import com.webtechsolution.ghumantey.ui.agency.agencyHome.AgencyHomeUiState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

data class AgencyBookingUiState(
    val toast:SingleEvent<String> = SingleEvent(),
    val agencyPackageList:List<AgencyPackageItem> = emptyList()
)
class AgencyBookingViewModel @ViewModelInject constructor(val apiInterface: ApiInterface) : BaseViewModel(){
    private val _state = MutableLiveData(AgencyBookingUiState())
    val state = _state as LiveData<AgencyBookingUiState>

    fun updateAgencyPackage(token: String) {
        apiInterface.getAgencyByPackage("Bearer "+token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({packageItem->
                val packages = packageItem.filter {
                    it.bookings.isNotEmpty()
                }
                _state.set {
                    it.copy(agencyPackageList = packages,toast = SingleEvent("Agency Packages"))
                }
            },{
                _state.set {
                    it.copy(toast = SingleEvent("Failed"))
                }
            }).isDisposed
    }
}