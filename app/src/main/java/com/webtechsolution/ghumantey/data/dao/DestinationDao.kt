package com.webtechsolution.ghumantey.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.webtechsolution.ghumantey.data.model.DestinationListItem
import io.reactivex.Flowable

@Dao
abstract class DestinationDao {

    @Query("SELECT * FROM DestinationListItem")
    abstract fun getAllDestination():Flowable<List<DestinationListItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertDestination(destination:List<DestinationListItem>)
}