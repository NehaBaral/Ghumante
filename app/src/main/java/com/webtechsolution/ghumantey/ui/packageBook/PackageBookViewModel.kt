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
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

data class PackageBookUiState(
    val success:SingleEvent<Unit> = SingleEvent()
)
@HiltViewModel
class PackageBookViewModel @Inject constructor(
    val apiInterface: ApiInterface,
    val preference:Preferences
) : BaseViewModel() {

    private val _state = MutableLiveData(PackageBookUiState())
    val state = _state as LiveData<PackageBookUiState>
    fun bookPackage(packageId:String,travellerNum: String, date: String, contact: String) {
        val packageBody:BookPackageBody = BookPackageBody(true,date,contact,travellerNum)
        apiInterface.bookPackage("Bearer ${preference.authInfo!!.token}",packageId,packageBody)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                println("Booked====")
            },{
                it.printStackTrace()
                it.message
            }).isDisposed
    }
}
