package com.ilhamamirullah.testtelkom.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.TextView
import com.ilhamamirullah.testtelkom.models.ArticleModel
import com.ilhamamirullah.testtelkom.R
import java.util.ArrayList

class TopStoryAdapter(private val list: ArrayList<ArticleModel>) : RecyclerView.Adapter<TopStoryAdapter.MyviewHolder>() {
    private var context: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyviewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_top_stories, parent, false)
        context = parent.context
        return MyviewHolder(view)
    }

    override fun onBindViewHolder(holder: MyviewHolder, position: Int) {
        holder.title.setText(list[position].title)
        holder.jumlahComment.setText(list[position].jmlComment.toString())
        holder.score.setText(list[position].score.toString())

        holder.title.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyviewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var title: TextView
        internal var jumlahComment: TextView
        internal var score: TextView

        init {
            title = itemView.findViewById(R.id.title) as TextView
            jumlahComment = itemView.findViewById(R.id.jml_komentar) as TextView
            score = itemView.findViewById(R.id.score) as TextView
        }
    }
}