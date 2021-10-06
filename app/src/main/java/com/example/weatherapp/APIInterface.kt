package com.example.weatherapp
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url


interface APIInterface {
    @GET
    fun getWeatherData(@Url url: String?): Call<Weather?>?}
