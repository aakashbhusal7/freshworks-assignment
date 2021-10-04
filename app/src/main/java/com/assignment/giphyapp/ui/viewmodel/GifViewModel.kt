package com.assignment.giphyapp.ui.viewmodel

import androidx.lifecycle.*
import com.assignment.giphyapp.data.model.GifData
import com.assignment.giphyapp.data.model.SavedGif
import com.assignment.giphyapp.data.model.TrendingGifResponse
import com.assignment.giphyapp.data.repository.GiphyApiRepository
import com.assignment.giphyapp.data.repository.GiphyLocalRepositoryImpl
import com.assignment.giphyapp.ext.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GifViewModel @Inject constructor(
    private val giphyApiRepository: GiphyApiRepository,
    private val giphyLocalRepositoryImpl: GiphyLocalRepositoryImpl
) : ViewModel() {

    //live data to store data and present it to the view through observable pattern
    var trendingGifs: MutableLiveData<List<GifData>> = MutableLiveData()
    var paginating: MutableLiveData<Boolean> = MutableLiveData()

    private val _invoke = MutableLiveData<Boolean>()

    //for mocking purpose in unit test
    fun start() {
        _invoke.value = true
    }

    //store required values in the variables for db and api
    var gifsInitial = _invoke.switchMap {
        giphyLocalRepositoryImpl.getSavedGifs()
    }
    var remoteGifsInitial = _invoke.switchMap {
        giphyApiRepository.loadTrendingGifs(50, 0)
    }

    //use these variables to compare the data with the mockup we create with {@DummyObject}
    val gifs: LiveData<List<SavedGif>> = gifsInitial
    val remoteGifs: LiveData<Resource<TrendingGifResponse>> = remoteGifsInitial

    fun retrieveTrendingGifs(
        limit: Int? = 50,
        page: Int? = null
    ) = giphyApiRepository.loadTrendingGifs(limit, page)

    fun retrieveSearchedGifs(
        query: String? = null,
        limit: Int? = 50,
        page: Int? = null
    ) = giphyApiRepository.loadSearchedGifs(query, limit, page)

    //save gif to a db
    fun saveGifToFavorite(savedGif: SavedGif) =
        viewModelScope.launch {
            giphyLocalRepositoryImpl.insertGif(savedGif)
        }

    //read all the saved gifs from db
    fun loadSavedGifs(): LiveData<List<SavedGif>> =
        giphyLocalRepositoryImpl.getSavedGifs()

    //check if the gif stored is a favorite or not according to the `id` stored in an entity of gif database
    fun checkIfFavorite(gifId: String): LiveData<Boolean> =
        giphyLocalRepositoryImpl.checkIfSaved(gifId)

    //delete a particular gif according to the `id` of a gif
    fun deleteGif(gifId: String) =
        viewModelScope.launch {
            giphyLocalRepositoryImpl.deleteGif(gifId)
        }

}