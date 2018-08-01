package com.example.jaimee_x.test

import android.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast

import com.example.jaimee_x.test.Adapter.ViewHolder.ListSourceAdapter
import com.example.jaimee_x.test.Common.Common
import com.example.jaimee_x.test.Interface.NewsService
import com.example.jaimee_x.test.Model.Website
import com.google.gson.Gson
import dmax.dialog.SpotsDialog
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var layoutManager: LinearLayoutManager
    lateinit var newsService: NewsService
    lateinit var adapter: ListSourceAdapter
    lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Paper.init(this)

        //init Service
        newsService = Common.newsService

        swipe_to_refresh.setOnRefreshListener {
            loadWebsiteSource(true)
        }

        source_list.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        source_list.layoutManager = layoutManager

        dialog = SpotsDialog(this)

        loadWebsiteSource(false)

    }

    private fun loadWebsiteSource(isRefresh: Boolean) {

        if (isRefresh)
        {
            val cache = Paper.book().read<String>("cache")
            if (cache != null && !cache.isBlank() && cache != "null")
            {
                val website = Gson().fromJson<Website>(cache, Website::class.java)
                adapter = ListSourceAdapter(baseContext,website)
                adapter.notifyDataSetChanged()
                source_list.adapter = adapter
            }
            else
            {
                //load website and write db
                dialog.show()
                //fetch data
                newsService.sources.enqueue(object:retrofit2.Callback<Website>{
                    override fun onFailure(call: Call<Website>?, t: Throwable?) {
                        Toast.makeText(baseContext, "Failed", Toast.LENGTH_SHORT).show()

                    }

                    //ctrl+o
                    override fun onResponse(call: Call<Website>?, response: Response<Website>?) {
                        adapter = ListSourceAdapter(baseContext,response!!.body()!!)
                        adapter.notifyDataSetChanged()
                        source_list.adapter = adapter

                        //save to cache
                        Paper.book().write("cache", Gson().toJson(response!!.body()!!))

                        dialog.dismiss()


                    }
                })

            }
        }
        else
        {
            swipe_to_refresh.isRefreshing=true
            //Fetch new dta

            newsService.sources.enqueue(object:retrofit2.Callback<Website>{
                override fun onFailure(call: Call<Website>?, t: Throwable?) {
                    Toast.makeText(baseContext, "Failed", Toast.LENGTH_SHORT).show()

                }

                //ctrl+o
                override fun onResponse(call: Call<Website>?, response: Response<Website>?) {
                    adapter = ListSourceAdapter(baseContext, response!!.body()!!)
                    adapter.notifyDataSetChanged()
                    source_list.adapter = adapter

                    //save to cahe
                    Paper.book().write("cache", Gson().toJson(response!!.body()!!))

                    swipe_to_refresh.isRefreshing=false
                }
            })
        }

    }
}
