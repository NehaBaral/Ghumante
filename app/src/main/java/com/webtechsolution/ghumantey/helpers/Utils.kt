package com.webtechsolution.ghumantey.helpers

class SingleEvent<T>(value: T? = null) {
    private var _value: T? = null

    val value: T?
        get() {
            val tmp = _value;
            _value = null
            return tmp
        }

    init {
        _value = value
    }
}

fun <T> T.toEvent() = SingleEvent(this)