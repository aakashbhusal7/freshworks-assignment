package com.assignment.giphyapp.data.repository

import androidx.lifecycle.LiveData
import com.assignment.giphyapp.data.local.GifDao
import com.assignment.giphyapp.data.model.SavedGif
import javax.inject.Inject

class GiphyLocalRepositoryImpl @Inject constructor(private val gifDao: GifDao) :
    GiphyLocalRepository {

    //extra layer of repository for abstraction
    //can be used when we have to load data of both remote and local at a same instance

    override suspend fun insertGif(savedGif: SavedGif) =
        gifDao.insertGif(savedGif)

    override fun getSavedGifs(): LiveData<List<SavedGif>> =
        gifDao.getSavedGifs()

    override fun checkIfSaved(id: String): LiveData<Boolean> =
        gifDao.isIdSaved(id)

    override suspend fun deleteGif(gifId: String) {
        gifDao.deleteGif(gifId)
    }
}