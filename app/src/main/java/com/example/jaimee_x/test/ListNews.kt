package com.example.jaimee_x.test

import android.app.AlertDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.jaimee_x.test.Adapter.ViewHolder.ListNewsAdapter
import com.example.jaimee_x.test.Common.Common
import com.example.jaimee_x.test.Interface.NewsService
import com.example.jaimee_x.test.Model.News
import com.squareup.picasso.Picasso
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_list_news.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListNews : AppCompatActivity() {

    var source=""
    var webHotUrl:String?=""

    lateinit var dialog: AlertDialog
    lateinit var newsService: NewsService
    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: ListNewsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_news)

        //init view\
        newsService = Common.newsService

        dialog = SpotsDialog(this)

        //swipe
        swipe_news.setOnRefreshListener {
            loadNews(source, true)
        }

            //diagonal layer
            diagonal_layout.setOnClickListener(){
             //implement
                var detail = Intent(baseContext,NewsDetails::class.java)
                detail.putExtra("webURL",webHotUrl)
                startActivity(detail)
            }

        //recycler
        news_list.setHasFixedSize(true)
        news_list.layoutManager = LinearLayoutManager(this)




        if (intent != null)
        {
            source = intent.getStringExtra("source")
            if (!source.isEmpty())
                loadNews(source,false)
        }

    }

    private fun loadNews(source: String?, isRefreshed: Boolean) {

        if (isRefreshed)
        {
            dialog.show()
            newsService.getNewsFromSource(Common.getNewsAPI(source!!))
                    .enqueue(object : Callback<News>{
                        override fun onFailure(call: Call<News>?, t: Throwable?) {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }

                        override fun onResponse(call: Call<News>?, response: Response<News>?) {
                           dialog.dismiss()
                            //set first news to main ui
                            Picasso.with(baseContext)
                                    .load(response!!.body()!!.articles!![0].urlToImage)
                                    .into(top_image)

                            top_title.text = response!!.body()!!.articles!![0].title
                            top_author.text = response!!.body()!!.articles!![0].author

                            webHotUrl = response!!.body()!!.articles!![0].url

                            val removeFirstItem = response!!.body()!!.articles
                            removeFirstItem!!.removeAt(0)

                            adapter = ListNewsAdapter(removeFirstItem!!,baseContext)
                            adapter.notifyDataSetChanged()
                            news_list.adapter = adapter


                        }

                    })
        }
        else
        {
           swipe_news.isRefreshing = true
            newsService.getNewsFromSource(Common.getNewsAPI(source!!))
                    .enqueue(object : Callback<News>{
                        override fun onFailure(call: Call<News>?, t: Throwable?) {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }

                        override fun onResponse(call: Call<News>?, response: Response<News>?) {
                           swipe_news.isRefreshing = false
                            //set first news to main ui
                            Picasso.with(baseContext)
                                    .load(response!!.body()!!.articles!![0].urlToImage)
                                    .into(top_image)

                            top_title.text = response!!.body()!!.articles!![0].title
                            top_author.text = response!!.body()!!.articles!![0].author

                            webHotUrl = response!!.body()!!.articles!![0].url

                            val removeFirstItem = response!!.body()!!.articles
                            removeFirstItem!!.removeAt(0)

                            adapter = ListNewsAdapter(removeFirstItem!!,baseContext)
                            adapter.notifyDataSetChanged()
                            news_list.adapter = adapter


                        }

                    })

        }

    }
}
