package com.ilhamamirullah.testtelkom.activities

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.widget.Toast
import com.ilhamamirullah.testtelkom.R
import com.ilhamamirullah.testtelkom.adapters.CommentAdapter
import com.ilhamamirullah.testtelkom.adapters.TopStoryAdapter
import com.ilhamamirullah.testtelkom.api.ApiClient
import com.ilhamamirullah.testtelkom.api.ApiInterface
import com.ilhamamirullah.testtelkom.models.ArticleModel
import com.ilhamamirullah.testtelkom.models.CommentResponse
import com.ilhamamirullah.testtelkom.models.TopStoriesModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_story_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class StoryDetailActivity : AppCompatActivity() {
    internal var list = ArrayList<CommentResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story_detail)
        val id: Int = intent.getIntExtra("id", 0)
        val apiInterface = ApiClient()
        val apiInt = apiInterface.getClient().create(ApiInterface::class.java)
        var clickImage: Boolean = false

        apiInt.getArticle(id).enqueue(object : Callback<TopStoriesModel> {
            @SuppressLint("SimpleDateFormat")
            override fun onResponse(call: Call<TopStoriesModel>, response: Response<TopStoriesModel>) {
                val title = response.body()!!.title.toString()
                val score = response.body()!!.score.toString()
                val jmlComment = response.body()!!.kids?.size.toString()
//                val id = response.body()!!.id.toString()
                val by = "By "+response.body()!!.by.toString()
                val time = response.body()!!.time!!.toLong()
                val sdf = java.text.SimpleDateFormat("yyyy-MM-dd")
                val date = java.util.Date(time * 1000)
                val kidsList:List<Int?>? = response.body()!!.kids
                val kidsCount = if (kidsList!=null) if (kidsList!!.size>20) 20 else kidsList.size-1 else 0

                detail_imageView.setOnClickListener {
                    if (clickImage){
                        detail_imageView.setImageResource(R.drawable.ic_star_border_black_24dp)
                        clickImage = false
                    }else{
                        detail_imageView.setImageResource(R.drawable.ic_star_black_24dp)
                        clickImage = true
                    }

                }

                detail_coment_recyclerview.layoutManager = LinearLayoutManager(applicationContext)
                detail_coment_recyclerview.setHasFixedSize(true)
                detail_coment_recyclerview.isNestedScrollingEnabled = false

                detail_title.setText(title)
                detail_by.setText(by)
                detail_date.setText(sdf.format(date))
                if (kidsList!=null){
                    for (i in 0..kidsCount) {
                        apiInt.gatComment(kidsList[i]!!).enqueue(object : Callback<CommentResponse> {
                            override fun onResponse(call: Call<CommentResponse>, response: Response<CommentResponse>) {
                                list.add(response.body()!!)
                                val adapter = CommentAdapter(applicationContext,list)
                                detail_coment_recyclerview.setAdapter(adapter)
                                adapter.notifyDataSetChanged()
                            }

                            override fun onFailure(call: Call<CommentResponse>, t: Throwable) {
                                Toast.makeText(applicationContext, "Something wrong", Toast.LENGTH_SHORT).show()
                            }
                        })
                    }
                }


            }

            override fun onFailure(call: Call<TopStoriesModel>, t: Throwable) {
                Toast.makeText(applicationContext, "Something wrong", Toast.LENGTH_SHORT).show()
            }
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
