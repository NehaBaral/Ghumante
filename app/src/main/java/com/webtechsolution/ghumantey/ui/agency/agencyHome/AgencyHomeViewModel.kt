package com.webtechsolution.ghumantey.ui.agency.agencyHome

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.webtechsolution.ghumantey.data.ApiInterface
import com.webtechsolution.ghumantey.data.Preferences
import com.webtechsolution.ghumantey.data.domain.AgencyPackage
import com.webtechsolution.ghumantey.data.domain.AgencyPackageItem
import com.webtechsolution.ghumantey.helpers.SingleEvent
import com.webtechsolution.ghumantey.helpers.base.BaseViewModel
import com.webtechsolution.ghumantey.helpers.set
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

data class AgencyHomeUiState(
    val toast:SingleEvent<String> = SingleEvent(),
    val agencyPackageList:List<AgencyPackageItem> = emptyList()
)
@HiltViewModel
class AgencyHomeViewModel @Inject constructor(val apiInterface: ApiInterface,val preference:Preferences) : BaseViewModel() {
    private val _state = MutableLiveData(AgencyHomeUiState())
    val state = _state as LiveData<AgencyHomeUiState>

    fun updateAgencyPackage() {
        apiInterface.getAgencyByPackage("Bearer ${preference.authInfo?.token}")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({packageItem->
                _state.set {
                    it.copy(agencyPackageList = packageItem,toast = SingleEvent("Agency Packages"))
                }
            },{
                it.printStackTrace()
                it.message
                _state.set {
                    it.copy(toast = SingleEvent("Failed"))
                }
            }).isDisposed
    }
}