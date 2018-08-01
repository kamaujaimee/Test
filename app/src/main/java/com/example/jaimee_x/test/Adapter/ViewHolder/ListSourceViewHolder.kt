package com.example.jaimee_x.test.Adapter.ViewHolder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.jaimee_x.test.Interface.ItemClickListener
import kotlinx.android.synthetic.main.source_news_layout.view.*

class ListSourceViewHolder(itemView:View):RecyclerView.ViewHolder(itemView),View.OnClickListener {
    private lateinit var itemClickListener:ItemClickListener

    var source_title = itemView.source_news_name

    init {
        itemView.setOnClickListener(this)
    }

    fun setItemClickListener(itemClickListener: ItemClickListener)
    {
        this.itemClickListener = itemClickListener

    }

    override fun onClick(p0: View?) {
        itemClickListener.onClick(p0!!,adapterPosition)
    }


}
