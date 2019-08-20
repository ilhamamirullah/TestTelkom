package com.ilhamamirullah.testtelkom.api

import com.ilhamamirullah.testtelkom.models.CommentResponse
import com.ilhamamirullah.testtelkom.models.TopStoriesModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
    @GET("v0/topstories.json?print=pretty")
    fun getTopStories(): Call<List<Int>>

    @GET("v0/item/{articleid}.json?print=pretty")
    fun getArticle(@Path("articleid") id: Int): Call<TopStoriesModel>

    @GET("v0/item/{id}.json?print=pretty")
    fun gatComment(@Path("id") id: Int): Call<CommentResponse>
}