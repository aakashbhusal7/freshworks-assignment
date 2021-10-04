package com.assignment.giphyapp.data.remote

import com.assignment.giphyapp.data.model.TrendingGifResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyApiInterface {

    @GET("v1/gifs/trending")
    suspend fun getTrendingGifs(
        @Query("limit") limit: Int?,
        @Query("offset") page: Int?
    ): Response<TrendingGifResponse>

    @GET("v1/gifs/search")
    suspend fun getSearchedGifs(
        @Query("q") searchQuery: String?,
        @Query("limit") limit: Int?,
        @Query("offset") page: Int?
    ): Response<TrendingGifResponse>

}