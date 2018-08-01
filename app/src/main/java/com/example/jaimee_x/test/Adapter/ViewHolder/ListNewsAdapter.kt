package com.example.jaimee_x.test.Adapter.ViewHolder

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jaimee_x.test.Adapter.ViewHolder.ListNewsViewHolder
import com.example.jaimee_x.test.Common.ISO8601Parser
import com.example.jaimee_x.test.Interface.ItemClickListener
import com.example.jaimee_x.test.Model.Articles
import com.example.jaimee_x.test.NewsDetails
import com.example.jaimee_x.test.R
import com.squareup.picasso.Picasso
import java.text.ParseException
import java.util.*

class  ListNewsAdapter(val  articleList:List<Articles>,private  val context: Context):RecyclerView.Adapter<ListNewsViewHolder>()
{
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ListNewsViewHolder {

        var inflater = LayoutInflater.from(p0!!.context)
        var itemView = inflater.inflate(R.layout.list_news_layout,p0,false)
        return ListNewsViewHolder(itemView)

    }

    override fun getItemCount(): Int {
       return articleList.size
    }

    override fun onBindViewHolder(p0: ListNewsViewHolder, p1: Int) {
        //load image
        Picasso.with(context)
                .load(articleList[p1].urlToImage)
                .into(p0.article_image)
        if (articleList[p1].title!!.length > 65 )
        {
            p0.article_title.text = articleList[p1].title!!.substring(0,65)+"..."
        }
        else
        {
            p0.article_title.text = articleList[p1].title!!
        }

        if (articleList[p1].publishedAt != null)
        {
            var date: Date?=null
            try
            {
                date = ISO8601Parser.parse(articleList[p1].publishedAt!!)
            }catch (ex:ParseException)
            {
                ex.printStackTrace()
            }
            p0.article_time.setReferenceTime(date!!.time)
        }


        p0.setItemClickListener(object :ItemClickListener {
            override fun onClick(view: View, position: Int) {
                var detail = Intent(context,NewsDetails::class.java)
                detail.putExtra("webURL",articleList[position].url)
                context.startActivity(detail)
            }

        })

    }

}