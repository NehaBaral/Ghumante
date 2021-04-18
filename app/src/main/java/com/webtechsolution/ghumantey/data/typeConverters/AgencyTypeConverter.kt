package com.webtechsolution.ghumantey.data.typeConverters

import androidx.room.TypeConverter
import com.webtechsolution.ghumantey.data.domain.Agency
import com.webtechsolution.ghumantey.data.domain.BookingPackageItem
import com.webtechsolution.ghumantey.helpers.to
import com.webtechsolution.ghumantey.helpers.toJson

object AgencyTypeConverter {
    @TypeConverter
    @JvmStatic
    fun toExcludeString(string: String) = string.to<Agency>()

    @TypeConverter
    @JvmStatic
    fun fromExcludeString(value: Agency?): String? = value?.toJson()
}