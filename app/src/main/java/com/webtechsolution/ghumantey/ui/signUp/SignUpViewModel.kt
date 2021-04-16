package com.webtechsolution.ghumantey.ui.signUp

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.webtechsolution.ghumantey.data.ApiInterface
import com.webtechsolution.ghumantey.data.domain.SignUpBody
import com.webtechsolution.ghumantey.helpers.SingleEvent
import com.webtechsolution.ghumantey.helpers.base.BaseViewModel
import com.webtechsolution.ghumantey.helpers.set
import com.webtechsolution.ghumantey.helpers.toEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

data class SignUpState(
    val loadingDialog: Boolean = false,
    val toast: SingleEvent<String> = SingleEvent(),
    val success: SingleEvent<Unit> = SingleEvent()
)
class SignUpViewModel @ViewModelInject constructor(val apiInterface: ApiInterface) : BaseViewModel() {
    private val _state = MutableLiveData(SignUpState())
    val state = _state as LiveData<SignUpState>

    fun userRegister(username: String, email: String, password: String){
        val signupBody:SignUpBody = SignUpBody(username,password,true)
        apiInterface.userRegister(signupBody)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _state.set { it.copy(loadingDialog = true) } }
            .subscribe({state->
                _state.set {
                    it.copy(
                        loadingDialog = false,
                        toast = SingleEvent(state.status),
                        success = Unit.toEvent()
                    )
                }
            }, {
                it.message
                _state.set {
                    it.copy(
                        loadingDialog = false,
                        toast = SingleEvent("Sign up failed")
                    )
                }
            }).isDisposed
    }

}