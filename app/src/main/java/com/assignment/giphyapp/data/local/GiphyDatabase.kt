package com.assignment.giphyapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.assignment.giphyapp.data.model.SavedGif

@Database(entities = [SavedGif::class], version = 2)

abstract class GiphyDatabase : RoomDatabase() {
    abstract fun gifDao(): GifDao

    companion object {
        @Volatile
        private var instance: GiphyDatabase? = null
        private val LOCK = Any()

        fun getDatabase(context: Context): GiphyDatabase =
            instance ?: synchronized(LOCK) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }

        //fallback set to destructive migration since its not a production app
        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            GiphyDatabase::class.java, "users.db"
        ).fallbackToDestructiveMigration()
            .build()
    }
}
