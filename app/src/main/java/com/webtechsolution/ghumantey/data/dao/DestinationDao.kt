package com.webtechsolution.ghumantey.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.webtechsolution.ghumantey.data.domain.AgencyPackageItem
import io.reactivex.Flowable

@Dao
abstract class DestinationDao {

    @Query("SELECT * FROM AgencyPackageItem")
    abstract fun getAllPackages():Flowable<List<AgencyPackageItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertPackage(destination: List<AgencyPackageItem>)
}