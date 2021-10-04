package com.assignment.giphyapp.module

import android.content.Context
import com.assignment.giphyapp.data.local.GiphyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**@Module to notify that we receive dependencies from there
 * @Singleton to ensure only a single instance is created and used over the entire app
 * @Provides to notify that a function will provide a dependency that is stated
 */

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) =
        GiphyDatabase.getDatabase(context)

    @Provides
    @Singleton
    fun provideGifDao(database: GiphyDatabase) =
        database.gifDao()

}
