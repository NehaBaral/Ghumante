package com.webtechsolution.ghumantey.data.typeConverters

import androidx.room.TypeConverter
import com.webtechsolution.ghumantey.data.domain.CommentItem
import com.webtechsolution.ghumantey.helpers.to
import com.webtechsolution.ghumantey.helpers.toJson

object CommentTypeConverter {
        @TypeConverter
        @JvmStatic
        fun toExcludeString(string: String) = string.to<List<CommentItem>>()

        @TypeConverter
        @JvmStatic
        fun fromExcludeString(value: List<CommentItem>?): String? = value?.toJson()
}