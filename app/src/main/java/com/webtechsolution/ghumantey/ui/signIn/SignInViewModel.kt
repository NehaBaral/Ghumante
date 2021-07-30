package com.webtechsolution.ghumantey.ui.signIn

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.webtechsolution.ghumantey.data.ApiInterface
import com.webtechsolution.ghumantey.data.Preferences
import com.webtechsolution.ghumantey.data.domain.Login
import com.webtechsolution.ghumantey.data.domain.SignUpBody
import com.webtechsolution.ghumantey.helpers.SingleEvent
import com.webtechsolution.ghumantey.helpers.base.BaseViewModel
import com.webtechsolution.ghumantey.helpers.into
import com.webtechsolution.ghumantey.helpers.set
import com.webtechsolution.ghumantey.helpers.toEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

data class SignInUiState(
    val loadingDialog: Boolean = false,
    val emailError: String? = null,
    val passwordError: String? = null,
    val toast: SingleEvent<String> = SingleEvent(),
    val success: SingleEvent<Unit> = SingleEvent(),
    val signInResponse: SingleEvent<Login> = SingleEvent()
)
@HiltViewModel
class SignInViewModel @Inject constructor(private val apiInterface: ApiInterface
,private val preference:Preferences) : BaseViewModel() {
    private val _state = MutableLiveData(SignInUiState())
    val state = _state as LiveData<SignInUiState>
    fun userSignIn(username: String, password: String, agencySwitch: Boolean) {
        val loginBody:SignUpBody = SignUpBody(username,password,agencySwitch)
        apiInterface.userLogin(loginBody)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _state.set { it.copy(loadingDialog = true) } }
            .subscribe({ loginResponse ->
                preference.authInfo = loginResponse
                _state.set { state ->
                    state.copy(
                        toast = SingleEvent("Login Successful"),
                        loadingDialog = false,
                        success = Unit.toEvent(),
                        signInResponse = loginResponse.toEvent()
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
            }).into(this)

    }
}