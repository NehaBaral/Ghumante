package com.webtechsolution.ghumantey.ui.signIn

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.webtechsolution.ghumantey.data.ApiInterface
import com.webtechsolution.ghumantey.helpers.SingleEvent
import com.webtechsolution.ghumantey.helpers.base.BaseViewModel
import com.webtechsolution.ghumantey.helpers.set
import com.webtechsolution.ghumantey.helpers.toEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

data class SignInUiState(
    val loadingDialog: Boolean = false,
    val emailError: String? = null,
    val passwordError: String? = null,
    val toast: SingleEvent<String> = SingleEvent(),
    val success: SingleEvent<Unit> = SingleEvent()
)
class SignInViewModel @ViewModelInject constructor(private val apiInterface: ApiInterface) : BaseViewModel() {
    private val _state = MutableLiveData(SignInUiState())
    val state = _state as LiveData<SignInUiState>
    fun userSignIn(email: String, password: String) {
        apiInterface.userLogin(email,password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _state.set { it.copy(loadingDialog = true) } }
            .subscribe({ loginResponse ->
                _state.set { state ->
                    state.copy(
                        toast = SingleEvent("Login Successful"),
                        loadingDialog = false,
                        success = Unit.toEvent()
                    )
                }
            }, {throwable->
                println("Errorrrr===="+throwable.message)
                _state.set {
                    it.copy(
                        loadingDialog = false,
                        toast = SingleEvent("Login Failed"),
                    )
                }
            }).isDisposed

    }
}