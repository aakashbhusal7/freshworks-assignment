package com.assignment.giphyapp.data.repository

import com.assignment.giphyapp.data.remote.BaseDataSource
import com.assignment.giphyapp.data.remote.GiphyApiInterface
import com.assignment.giphyapp.ext.performNetworkOperation
import javax.inject.Inject

class GiphyApiRepository @Inject constructor(
    private val giphyApiInterface: GiphyApiInterface
) : BaseDataSource() {

    fun loadTrendingGifs(
        limit: Int?,
        page: Int?
    ) = performNetworkOperation(
        networkCall = {
            getResult {
                giphyApiInterface.getTrendingGifs(limit, page)
            }
        }
    )

    fun loadSearchedGifs(
        query: String?,
        limit: Int?,
        page: Int?
    ) = performNetworkOperation(
        networkCall = {
            getResult {
                giphyApiInterface.getSearchedGifs(query, limit, page)
            }
        }
    )
}