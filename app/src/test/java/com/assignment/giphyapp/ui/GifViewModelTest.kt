package com.assignment.giphyapp.ui

import androidx.lifecycle.MutableLiveData
import com.assignment.giphyapp.BaseTest
import com.assignment.giphyapp.data.repository.GiphyApiRepository
import com.assignment.giphyapp.data.repository.GiphyLocalRepositoryImpl
import com.assignment.giphyapp.ext.Resource
import com.assignment.giphyapp.ui.viewmodel.GifViewModel
import com.assignment.giphyapp.util.DummyObject
import com.jraska.livedata.test
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test

@ExperimentalCoroutinesApi
class GifViewModelTest : BaseTest() {

    override fun preTest() {
        testObject = GifViewModel(giphyApiRepository, giphyLocalRepositoryImpl)
    }

    // Subject under test
    private lateinit var testObject: GifViewModel

    //relaxed mocks to return a default value for each function
    // no need to describe behavior of each method
    @RelaxedMockK
    private lateinit var giphyApiRepository: GiphyApiRepository

    @RelaxedMockK
    private lateinit var giphyLocalRepositoryImpl: GiphyLocalRepositoryImpl

    //rest to check data for saved gifs in database
    @Test
    fun getSavedGifs() {
        val expected = MutableLiveData((DummyObject.testListGifs))
        every {
            giphyLocalRepositoryImpl.getSavedGifs()
        }.returns(expected)
        testObject.start()
        //use of jraska library for easy testing of live data
        val obs = testObject.gifs.test()
        obs.assertHasValue()
            .assertValue {
                it == (DummyObject.testListGifs)
            }
    }

    //test to check api call for trending gifs
    @Test
    fun getTrendingGifs() {
        val expected = MutableLiveData(Resource.success(DummyObject.remoteGifs))
        every {
            giphyApiRepository.loadTrendingGifs(50, 0)
        }.returns(expected)
        testObject.start()
        //use of jraska library for easy testing of live data
        val obs = testObject.remoteGifs.test()
        obs.assertHasValue()
            .assertValue {
                it.data == (DummyObject.remoteGifs)
            }
    }


}