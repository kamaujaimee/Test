package com.example.jaimee_x.test.Adapter.ViewHolder

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.jaimee_x.test.Interface.ItemClickListener
import com.example.jaimee_x.test.ListNews
import com.example.jaimee_x.test.Model.Website
import com.example.jaimee_x.test.R
import java.util.zip.Inflater

class ListSourceAdapter(private val context: Context,private val website: Website):RecyclerView.Adapter<ListSourceViewHolder>()
{
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ListSourceViewHolder {

        val inflater = LayoutInflater.from(p0!!.context)
        val itemView = inflater.inflate(R.layout.source_news_layout,p0,false)
        return ListSourceViewHolder(itemView)
    }

    override fun getItemCount(): Int {
       return website.sources!!.size
    }

    override fun onBindViewHolder(p0: ListSourceViewHolder, p1: Int) {

        p0!!.source_title.text = website.sources!![p1].name

        p0.setItemClickListener(object : ItemClickListener
        {
            override fun onClick(view: View, position: Int) {
               val intent = Intent(context,ListNews::class.java)
                intent.putExtra("source",website.sources!![position].id)
                context.startActivity(intent)
            }
        })
    }

}