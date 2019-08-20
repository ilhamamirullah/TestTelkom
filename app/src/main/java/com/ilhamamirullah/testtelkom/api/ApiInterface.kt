package com.ilhamamirullah.testtelkom.api

import com.ilhamamirullah.testtelkom.models.TopStoriesModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
    @GET("v0/topstories.json?print=pretty")
    abstract fun getTopStories(): Call<List<Int>>

    @GET("v0/item/{articleid}.json?print=pretty")
    abstract fun getArticle(@Path("articleid") id: Int): Call<TopStoriesModel>
}