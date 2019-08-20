package com.ilhamamirullah.testtelkom.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.ilhamamirullah.testtelkom.R
import com.ilhamamirullah.testtelkom.api.ApiClient
import com.ilhamamirullah.testtelkom.api.ApiInterface
import com.ilhamamirullah.testtelkom.models.ArticleModel
import com.ilhamamirullah.testtelkom.models.TopStoriesModel
import com.ilhamamirullah.testtelkom.adapters.TopStoryAdapter
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    internal var list = ArrayList<ArticleModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.setHasFixedSize(true)

        val apiInterface = ApiClient()
        val apiInt = apiInterface.getClient().create(ApiInterface::class.java)
        apiInt.getTopStories().enqueue(object :Callback<List<Int>>{
            override fun onFailure(call: Call<List<Int>>, t: Throwable) {

            }

            override fun onResponse(call: Call<List<Int>>, response: Response<List<Int>>) {
                val topStories = response.body()
                val countStories:Int = if (topStories!!.size>20) 20 else topStories.size-1

                for (i in 0..countStories) {
                    apiInt.getArticle(topStories[i]).enqueue(object : Callback<TopStoriesModel> {
                        override fun onResponse(call: Call<TopStoriesModel>, response: Response<TopStoriesModel>) {
                            val title = response.body()!!.title.toString()
                            val score = response.body()!!.score.toString()
                            val jmlComment = response.body()!!.kids?.size.toString()
                            val id = response.body()!!.id!!
                            val articleModel = ArticleModel(id, title, jmlComment,score)
                            list.add(articleModel)
                            val adapter = TopStoryAdapter(list)
                            recyclerview.setAdapter(adapter)
                            adapter.notifyDataSetChanged()
                        }

                        override fun onFailure(call: Call<TopStoriesModel>, t: Throwable) {
                            Toast.makeText(applicationContext, "Something wrong", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            }

        })

    }
}

