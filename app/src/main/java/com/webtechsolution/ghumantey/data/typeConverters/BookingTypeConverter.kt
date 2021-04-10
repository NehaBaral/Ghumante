package com.webtechsolution.ghumantey.data.typeConverters

import androidx.room.TypeConverter
import com.webtechsolution.ghumantey.data.domain.BookingPackageItem
import com.webtechsolution.ghumantey.helpers.to
import com.webtechsolution.ghumantey.helpers.toJson

object BookingTypeConverter {
    @TypeConverter
    @JvmStatic
    fun toExcludeString(string: String) = string.to<List<BookingPackageItem>>()

    @TypeConverter
    @JvmStatic
    fun fromExcludeString(value: List<BookingPackageItem>?): String? = value?.toJson()
}