package com.webtechsolution.ghumantey.ui.agency.userDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.webtechsolution.ghumantey.data.RoomDB
import com.webtechsolution.ghumantey.data.domain.AgencyPackageItem
import com.webtechsolution.ghumantey.helpers.SingleEvent
import com.webtechsolution.ghumantey.helpers.base.BaseViewModel
import com.webtechsolution.ghumantey.helpers.update
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

data class UserDetailUiState(
    val toast:SingleEvent<String> = SingleEvent(),
    val agencyDetail:AgencyPackageItem ?= null
)
@HiltViewModel
class UserDetailViewModel @Inject constructor(val roomDb:RoomDB) : BaseViewModel() {
    private val _state = MutableLiveData(UserDetailUiState())
    val state = _state as LiveData<UserDetailUiState>

    fun getPackage(packageId: String) {
        roomDb.destinationDao.getPackagesById(packageId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                _state.update { copy(agencyDetail = it) }
            }.isDisposed
    }
}