package com.webtechsolution.ghumantey.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.webtechsolution.ghumantey.app.Config
import com.webtechsolution.ghumantey.data.dao.DestinationDao
import com.webtechsolution.ghumantey.data.domain.AgencyPackage
import com.webtechsolution.ghumantey.data.domain.AgencyPackageItem
import com.webtechsolution.ghumantey.data.domain.BookingPackageItem
import com.webtechsolution.ghumantey.data.domain.PackagesListItem
import com.webtechsolution.ghumantey.data.typeConverters.AgencyTypeConverter
import com.webtechsolution.ghumantey.data.typeConverters.BookingTypeConverter
import com.webtechsolution.ghumantey.data.typeConverters.CommentTypeConverter

@Database(
    entities = [AgencyPackageItem::class], version = Config.DATABASE_VERSION,
    exportSchema = false
)
@TypeConverters(
    BookingTypeConverter::class,
    CommentTypeConverter::class,
    BookingTypeConverter::class,
    AgencyTypeConverter::class
)
abstract class RoomDB : RoomDatabase() {
    abstract val destinationDao: DestinationDao
}