package com.ilhamamirullah.testtelkom.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
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
    private lateinit var loading: MaterialDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loading = MaterialDialog.Builder(this)
            .cancelable(false)
            .content("Loading")
            .progress(true, 0)
            .build()
        recyclerview.layoutManager = LinearLayoutManager(applicationContext)
        recyclerview.setHasFixedSize(true)
        val id: Int = intent.getIntExtra("id", 0)

        getData(id)
    }

    fun getData(id: Int) {
        loading.show()
        val apiInterface = ApiClient()
        val apiInt = apiInterface.getClient().create(ApiInterface::class.java)

        apiInt.getTopStories().enqueue(object : Callback<List<Int>> {
            override fun onFailure(call: Call<List<Int>>, t: Throwable) {
                runOnUiThread { loading.dismiss() }
                Toast.makeText(applicationContext, "Something wrong", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<Int>>, response: Response<List<Int>>) {
                val topStories = response.body()
                val countStories: Int = if (topStories!!.size > 20) 20 else topStories.size - 1

                for (i in 0..countStories) {
                    apiInt.getArticle(topStories[i]).enqueue(object : Callback<TopStoriesModel> {
                        override fun onResponse(call: Call<TopStoriesModel>, response: Response<TopStoriesModel>) {
                            val title = response.body()!!.title.toString()
                            val score = response.body()!!.score.toString()
                            val jmlComment =
                                if (response.body()!!.kids != null) response.body()!!.kids?.size.toString() else "0"
                            val idRes = response.body()!!.id!!
                            if (idRes == id) {
                                fav_textView.setText(title)
                            }
                            val articleModel = ArticleModel(idRes, title, jmlComment, score)
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
                runOnUiThread { loading.dismiss() }
            }
        })
    }
}

