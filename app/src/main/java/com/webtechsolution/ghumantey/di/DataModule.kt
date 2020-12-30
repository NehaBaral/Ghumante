package com.webtechsolution.ghumantey.di

import android.content.Context
import androidx.room.Room
import com.webtechsolution.ghumantey.app.Config
import com.webtechsolution.ghumantey.data.RoomDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class DataModule {

    @Provides
    @Singleton
    fun getRoomDb(context: Context): RoomDB {
        return Room.databaseBuilder(context, RoomDB::class.java, Config.DATABASE_NAME).build()
    }
}