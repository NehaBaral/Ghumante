package com.webtechsolution.ghumantey.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.webtechsolution.ghumantey.app.Config
import com.webtechsolution.ghumantey.data.dao.DestinationDao
import com.webtechsolution.ghumantey.data.domain.PackagesListItem
import com.webtechsolution.ghumantey.data.typeConverters.BookingTypeConverter
import com.webtechsolution.ghumantey.data.typeConverters.CommentTypeConverter

@Database(
    entities = [PackagesListItem::class], version = Config.DATABASE_VERSION,
    exportSchema = false
)
@TypeConverters(
    BookingTypeConverter::class,
    CommentTypeConverter::class
)
abstract class RoomDB : RoomDatabase() {
    abstract val destinationDao: DestinationDao
}