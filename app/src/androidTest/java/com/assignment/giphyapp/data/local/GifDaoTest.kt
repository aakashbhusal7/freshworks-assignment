package com.assignment.giphyapp.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.assignment.giphyapp.data.model.SavedGif
import com.assignment.giphyapp.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class GifDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    //for synchronous execution of task which is preferable during testing
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("users_db")
    lateinit var database: GiphyDatabase
    private lateinit var dao: GifDao

    //execute before every test case
    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.gifDao()
    }

    //execute after every test case
    @After
    fun postSetup() {
        database.close()
    }

    @Test
    fun insertGif() = runBlockingTest {
        val gifData = SavedGif("1", "test.url")
        dao.insertGif(gifData)
        val totalGifData = dao.getSavedGifs().getOrAwaitValue()
        assertThat(totalGifData).contains(gifData)
    }

    @Test
    fun deleteGif() = runBlockingTest {
        val gifData = SavedGif("1", "test.url")
        dao.insertGif(gifData)
        dao.deleteGif("1")
        val totalGifData = dao.getSavedGifs().getOrAwaitValue()
        assertThat(totalGifData).doesNotContain(gifData)
    }
}