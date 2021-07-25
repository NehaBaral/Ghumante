package com.webtechsolution.ghumantey.helpers

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.DisplayMetrics
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.FileOutputStream
import java.util.*

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

fun dpToPx(dp: Int, context: Context): Int {
    val displayMetrics = context.resources.displayMetrics
    return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
}

fun getRandNameInInternalPath(context: Context): String? {
    return context.applicationContext.filesDir.absolutePath + String.format("/%s", UUID.randomUUID().toString())
}

fun saveBitmap(context: Context, bitmap: Bitmap): Observable<Uri>? {
    return Single.fromCallable {
        val file = File(getRandNameInInternalPath(context))
        val out = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, out)
        out.flush()
        out.close()
        Uri.fromFile(file)
    }.subscribeOn(Schedulers.io()).toObservable()
}

fun moveToInternal(context: Context, uri: Uri?): Observable<Uri?>? {
    val imagePath: String? = getRandNameInInternalPath(context)
    val destination = Uri.fromFile(File(imagePath))
    return moveFile(uri, destination, context)?.map(Function { aBoolean: Boolean? -> destination })
}

fun moveFile(src: Uri?, destination: Uri?, context: Context): Observable<Boolean>? {
    return Single.fromCallable {
        val inputStream = context.contentResolver.openInputStream(src!!)
        val outputStream = context.contentResolver.openOutputStream(destination!!)
        val buf = ByteArray(1024)
        var len: Int
        while (inputStream!!.read(buf).also { len = it } > 0) {
            outputStream!!.write(buf, 0, len)
        }
        inputStream.close()
        outputStream!!.close()
        true
    }.subscribeOn(Schedulers.io()).toObservable()
}