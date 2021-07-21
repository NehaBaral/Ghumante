package com.webtechsolution.ghumantey.ui.packageBook

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.webtechsolution.ghumantey.data.ApiInterface
import com.webtechsolution.ghumantey.data.Preferences
import com.webtechsolution.ghumantey.data.domain.BookPackageBody
import com.webtechsolution.ghumantey.helpers.SingleEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.webtechsolution.ghumantey.helpers.base.BaseViewModel
import com.webtechsolution.ghumantey.helpers.toEvent
import com.webtechsolution.ghumantey.helpers.update
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

data class PackageBookUiState(
    val success:SingleEvent<Unit> = SingleEvent(),
    val loading:Boolean = false,
    val toast:SingleEvent<String> = SingleEvent()
)
@HiltViewModel
class PackageBookViewModel @Inject constructor(
    val apiInterface: ApiInterface,
    val preference:Preferences
) : BaseViewModel() {

    private val _state = MutableLiveData(PackageBookUiState())
    val state = _state as LiveData<PackageBookUiState>
    fun bookPackage(packageId:String,travellerNum: String, date: String, contact: String) {
        _state.update { copy(loading = true) }
        val packageBody:BookPackageBody = BookPackageBody(true,date,contact,travellerNum)
        apiInterface.bookPackage("Bearer ${preference.authInfo!!.token}",packageId,packageBody)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _state.update { copy(loading = false,success = Unit.toEvent()) }
            },{
                it.printStackTrace()
                it.message
                _state.update { copy(loading = false,toast = SingleEvent("Booking Failed")) }
            }).isDisposed
    }
}
