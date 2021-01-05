package com.webtechsolution.ghumantey.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.webtechsolution.ghumantey.app.Config
import com.webtechsolution.ghumantey.data.dao.DestinationDao
import com.webtechsolution.ghumantey.data.model.DestinationListItem
import com.webtechsolution.ghumantey.data.model.DestinationModel

@Database(
    entities = [DestinationListItem::class], version = Config.DATABASE_VERSION,
    exportSchema = true
)
@TypeConverters()
abstract class RoomDB : RoomDatabase() {
    abstract val destinationDao: DestinationDao
}