package com.webtechsolution.ghumantey.ui.myProfile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.webtechsolution.ghumantey.data.Preferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.webtechsolution.ghumantey.helpers.base.BaseViewModel
import com.webtechsolution.ghumantey.helpers.update

data class MyProfileUiState(
    val username:String?=null
)
@HiltViewModel
class MyProfileViewModel @Inject constructor(val preference:Preferences) : BaseViewModel() {

    private val _state = MutableLiveData(MyProfileUiState())
    val state = _state as LiveData<MyProfileUiState>

    init {
        _state.update { copy(username=preference.authInfo?.userName) }
    }

    fun signUp() {

    }
}