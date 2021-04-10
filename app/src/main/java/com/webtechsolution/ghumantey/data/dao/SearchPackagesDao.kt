package com.webtechsolution.ghumantey.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.webtechsolution.ghumantey.data.domain.SearchPackageItem
import com.webtechsolution.ghumantey.data.model.PackagesModelItem
import io.reactivex.Flowable

/*@Dao
abstract class SearchPackagesDao {
    @Query("SELECT * FROM SearchPackageItem")
    abstract fun getAllPackages(): Flowable<List<SearchPackageItem>>

    @Query("SELECT * FROM SearchPackageItem WHERE destination =:destination")
    abstract fun getDestinationPackage(destination:String): Flowable<List<SearchPackageItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertPackages(packages:List<SearchPackageItem>)
}*/
