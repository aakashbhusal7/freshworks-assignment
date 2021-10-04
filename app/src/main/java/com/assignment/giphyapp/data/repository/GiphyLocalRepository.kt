package com.assignment.giphyapp.data.repository

import androidx.lifecycle.LiveData
import com.assignment.giphyapp.data.model.SavedGif

interface GiphyLocalRepository {

    suspend fun insertGif(savedGif: SavedGif)

    fun getSavedGifs(): LiveData<List<SavedGif>>

    fun checkIfSaved(id: String): LiveData<Boolean>

    suspend fun deleteGif(gifId: String)
}