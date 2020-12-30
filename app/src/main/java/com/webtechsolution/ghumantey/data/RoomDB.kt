package com.webtechsolution.ghumantey.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.webtechsolution.ghumantey.app.Config
import com.webtechsolution.ghumantey.data.model.DestinationModel
import com.webtechsolution.ghumantey.data.model.UserModel

@Database(entities = [UserModel::class],version = Config.DATABASE_VERSION,
    exportSchema = false)
@TypeConverters()
abstract class RoomDB : RoomDatabase() {

}