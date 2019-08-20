package com.ilhamamirullah.testtelkom.adapters

import android.content.Context
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ilhamamirullah.testtelkom.R
import com.ilhamamirullah.testtelkom.models.CommentResponse
import kotlinx.android.synthetic.main.item_comment.view.*

class CommentAdapter (private val context: Context, private val itemList: List<CommentResponse?>?) :
    RecyclerView.Adapter<CommentAdapter.CommentVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentVH =
        CommentVH(LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false))

    override fun getItemCount(): Int = itemList!!.size

    override fun onBindViewHolder(holder: CommentVH, position: Int) {
        holder.bindList(itemList?.get(position))
    }

    inner class CommentVH(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindList(item: CommentResponse?){
            if (item?.text != null){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    itemView.comment_textView.setText(Html.fromHtml(item.text, Html.FROM_HTML_MODE_COMPACT));
                } else {
                    itemView.comment_textView.setText(Html.fromHtml(item.text));
                }
            }else{
                itemView.comment_textView.setText("-")
            }

        }
    }
}