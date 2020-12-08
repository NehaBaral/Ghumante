package com.webtechsolution.ghumantey.ui.packageDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.webtechsolution.ghumantey.data.model.DestinationModel
import com.webtechsolution.ghumantey.helpers.base.BaseViewModel

class PackageDetailViewModel : BaseViewModel() {
    private val _state = MutableLiveData(listOf(
        DestinationModel("Gatthaghar Rent"),
        DestinationModel("Kausaltar Rent"),
        DestinationModel("Kausaltar Rent"),
        DestinationModel("Kausaltar Rent")
    ).map { it })

    val state = _state as LiveData<List<DestinationModel>>
}