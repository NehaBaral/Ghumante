package com.webtechsolution.ghumantey.data.typeConverters

import androidx.room.TypeConverter
import com.webtechsolution.ghumantey.helpers.to
import com.webtechsolution.ghumantey.helpers.toJson

object PackagesTypeConverter {
    @TypeConverter
    @JvmStatic
    fun toExcludeString(string: String) = string.to<List<Any>>()

    @TypeConverter
    @JvmStatic
    fun fromExcludeString(value: List<Any>?): String? = value?.toJson()

}