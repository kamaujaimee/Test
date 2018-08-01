package com.example.jaimee_x.test.Common

import com.example.jaimee_x.test.Interface.NewsService
import com.example.jaimee_x.test.Remote.RetrofitClient

object Common{

    val BASE_URL = "https://newsapi.org/"
    val API_KEY = "2c3ce76adc734f66a4b9ce847e6408c0"

    val newsService:NewsService
    get() = RetrofitClient.getClient(BASE_URL).create(NewsService::class.java)

    fun getNewsAPI(source:String):String{
        val apiUrl = StringBuilder("https://newsapi.org/v2/top-headlines?sources=")
                .append(source)
                .append("&apiKey=")
                .append(API_KEY)
                .toString()
        return apiUrl
    }

}