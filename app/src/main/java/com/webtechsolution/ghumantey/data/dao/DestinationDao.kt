package com.webtechsolution.ghumantey.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.webtechsolution.ghumantey.data.domain.PackagesListItem
import io.reactivex.Flowable

@Dao
abstract class DestinationDao {

    @Query("SELECT * FROM PackagesListItem")
    abstract fun getAllPackages():Flowable<List<PackagesListItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertPackage(destination: List<PackagesListItem>)
}