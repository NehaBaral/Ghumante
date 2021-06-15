package com.webtechsolution.ghumantey.ui.profileEdit

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.webtechsolution.ghumantey.helpers.base.BaseViewModel

data class ProfileUiState(
    val image:String?=null,
    val username:String?=null,
    val email:String?=null
)

@HiltViewModel
class ProfileEditViewModel @Inject constructor() : BaseViewModel() {
    private val _state = MutableLiveData<ProfileUiState>()
    val state= _state as LiveData<ProfileUiState>
    fun setImage(image: String) {
        _state.value.apply {
            this?.copy(
                image = image
            )
        }
    }

}