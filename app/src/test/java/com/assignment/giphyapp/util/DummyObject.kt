package com.assignment.giphyapp.util

import com.assignment.giphyapp.data.model.*

//make mockup of the expected data to compare it with what is to be actually presented

object DummyObject {
    //for db data
    val testListGifs: List<SavedGif> = listOf(
        SavedGif(
            "1",
            "test.url"
        ),
        SavedGif(
            "2",
            "test2.url"
        )
    )

    //for api data
    val remoteGifs: TrendingGifResponse =
        TrendingGifResponse(
            listOf(
                GifData(
                    images = Images(
                        fixedHeight = FixedHeight(
                            url = "https://media1.giphy.com/media/xT9IgoiyvYeTLbBqxO/200.gif?cid=b18c1f3ffmjf5mwc73c2l1gg6zy0ar6v2kao8r0w2jy6zw1a&rid=200.gif&ct=g"
                        )
                    )
                )
            ),
            null,
            null
        )

}