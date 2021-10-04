package com.assignment.giphyapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.assignment.giphyapp.data.model.SavedGif

@Dao
interface GifDao {

    @Query("SELECT * FROM savedGif")
    fun getSavedGifs(): LiveData<List<SavedGif>>

    @Query("SELECT EXISTS(SELECT * FROM savedGif WHERE userId= :id)")
    fun isIdSaved(id: String): LiveData<Boolean>

    //add suspend for ui-blocking calls

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGif(savedGif: SavedGif)

    @Query("DELETE FROM savedGif WHERE userId = :id")
    suspend fun deleteGif(id: String)

}
