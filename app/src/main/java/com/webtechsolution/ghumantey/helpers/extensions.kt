package com.webtechsolution.ghumantey.helpers

import android.app.Activity
import android.content.res.Resources
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import kotlin.math.roundToInt

fun Fragment.hideKeyBoard() {
    val imm: InputMethodManager = requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    //Find the currently focused view, so we can grab the correct window token from it.
    val view = activity!!.currentFocus ?: View(activity)
    //If no view currently has focus, create a new one, just so we can grab a window token from it
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun <T> MutableLiveData<T>.set(modifier: (data: T) -> T) {
    value?.let { data ->
        value = modifier(data)
    }
}

fun <T> MutableLiveData<T>.update(modifier: T.() -> T) {
    value?.let { data ->
        value = modifier(data)
    }
}
fun Throwable.handle(): Error {
    try {
        when (this) {
            is SocketTimeoutException -> return HandledError("Couldn't reach server")
            is HttpException -> {
                val errorType = if (code() == 401) ErrorType.UnAuth else ErrorType.OTHER
                if (errorType == ErrorType.UnAuth) {
                    return HandledError("You are not logged in", ErrorType.UnAuth)
                }
                return response()?.let { response ->
                    val message = response.errorBody()!!.string()
                    HandledError(message.to<ServerError>().message, errorType)
                } ?: HandledError("Something went wrong", errorType)
            }
            is ConnectException -> return HandledError("Couldn't reach server")
            else -> return HandledError("Something went wrong")
        }
    } catch (e: Exception) {
        return HandledError("Something went wrong")
    }
}

data class ServerError(val message: String)

val gson = Gson()
fun <T> T.toJson(): String = gson.toJson(this)
inline fun <reified T> String.to(): T = gson.fromJson(this, object : TypeToken<T>() {}.type)

fun Number.dpToPx(): Int {
    return (Resources.getSystem().displayMetrics.density * this.toFloat()).roundToInt()
}






















