package com.assignment.giphyapp.module

import android.content.Context
import androidx.room.Room
import com.assignment.giphyapp.data.local.GiphyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Named("users_db")
    fun provideInMemoryDb(@ApplicationContext context: Context) =
        //create in memory/ram db instead of actual device
        Room.inMemoryDatabaseBuilder(
            context, GiphyDatabase::class.java
        ).allowMainThreadQueries()
            .build()

}