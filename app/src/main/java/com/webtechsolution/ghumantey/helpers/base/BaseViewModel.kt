package com.webtechsolution.ghumantey.helpers.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.internal.disposables.DisposableContainer

open class BaseViewModel : ViewModel(), DisposableContainer {
    private val _unAuthStatus = MutableLiveData<Unit>()
    val unAuthStatus = _unAuthStatus as LiveData<Unit>
    private val disposeBag = CompositeDisposable()

    override fun add(d: Disposable) = disposeBag.add(d)
    override fun remove(d: Disposable) = disposeBag.remove(d)
    override fun delete(d: Disposable) = disposeBag.delete(d)

    /*fun checkError(error: Error) {
        if (error.errorType == ErrorType.UnAuth)
            _unAuthStatus.value = Unit
    }*/
}