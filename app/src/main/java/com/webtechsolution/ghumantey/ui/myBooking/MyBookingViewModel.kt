package com.webtechsolution.ghumantey.ui.myBooking

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.webtechsolution.ghumantey.data.model.DestinationModel
import com.webtechsolution.ghumantey.helpers.base.BaseViewModel

class MyBookingViewModel @ViewModelInject constructor() : BaseViewModel() {
    private val _state = MutableLiveData(listOf(
        DestinationModel("Gatthaghar Rent"),
        DestinationModel("Kausaltar Rent"),
        DestinationModel("Kausaltar Rent"),
        DestinationModel("Kausaltar Rent")
    ).map { it })

    val state = _state as LiveData<List<DestinationModel>>
}