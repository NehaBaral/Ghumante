package com.webtechsolution.ghumantey.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.webtechsolution.ghumantey.data.model.PackagesModelItem
import io.reactivex.Flowable

@Dao
abstract class PackagesDao {
    @Query("SELECT * FROM PackagesModelItem")
    abstract fun getAllPackages(): Flowable<List<PackagesModelItem>>

    @Query("SELECT * FROM PackagesModelItem WHERE _id =:id")
    abstract fun getDestinationPackage(id:String): Flowable<List<PackagesModelItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertPackages(packages:List<PackagesModelItem>)
}