package com.webtechsolution.ghumantey.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.webtechsolution.ghumantey.app.Config
import com.webtechsolution.ghumantey.data.dao.DestinationDao
import com.webtechsolution.ghumantey.data.dao.PackagesDao
import com.webtechsolution.ghumantey.data.model.Data
import com.webtechsolution.ghumantey.data.model.DestinationModel
import com.webtechsolution.ghumantey.data.model.PackagesModelItem
import com.webtechsolution.ghumantey.data.typeConverters.PackagesTypeConverter

@Database(
    entities = [Data::class,PackagesModelItem::class], version = Config.DATABASE_VERSION,
    exportSchema = true
)
@TypeConverters(
    PackagesTypeConverter::class
)
abstract class RoomDB : RoomDatabase() {
    abstract val destinationDao: DestinationDao
    abstract val packageDao:PackagesDao
}