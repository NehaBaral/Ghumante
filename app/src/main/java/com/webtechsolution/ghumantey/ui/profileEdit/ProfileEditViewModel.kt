package com.webtechsolution.ghumantey.ui.profileEdit

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.webtechsolution.ghumantey.helpers.base.BaseViewModel

data class ProfileUiState(
    val image:String?=null,
    val username:String?=null,
    val email:String?=null
)

class ProfileEditViewModel @ViewModelInject constructor() : BaseViewModel() {
    private val _state = MutableLiveData<ProfileUiState>()
    val state= _state as LiveData<ProfileUiState>
    fun setImage(image: String) {
        _state.value?.copy(
            image = image
        )
    }

}