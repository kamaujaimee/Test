package com.example.jaimee_x.test.Adapter.ViewHolder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.jaimee_x.test.Interface.ItemClickListener
import kotlinx.android.synthetic.main.list_news_layout.view.*

class  ListNewsViewHolder(itemView:View):RecyclerView.ViewHolder(itemView),View.OnClickListener{

    private lateinit var itemClickListener: ItemClickListener

    var article_title = itemView.article_title
    var article_time = itemView.article_time
    var article_image = itemView.article_image

    init {
        itemView.setOnClickListener(this)
    }

    fun  setItemClickListener(itemClickListener: ItemClickListener)
    {
        this.itemClickListener = itemClickListener
    }

    override fun onClick(p0: View) {
       itemClickListener.onClick(p0,adapterPosition)
    }

}