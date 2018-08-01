package com.example.jaimee_x.test.Interface

import com.example.jaimee_x.test.Model.News
import com.example.jaimee_x.test.Model.Website
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface NewsService {

    @get:GET("v2/sources?apiKey=2c3ce76adc734f66a4b9ce847e6408c0")
    val sources:Call<Website>

    @GET
fun getNewsFromSource(@Url url: String): Call<News>

}