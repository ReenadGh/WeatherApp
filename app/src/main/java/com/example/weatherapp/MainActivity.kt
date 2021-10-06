package com.example.weatherapp

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.CoroutineContext
import com.example.weatherapp.Main
import kotlinx.coroutines.Dispatchers.Main
import java.lang.Exception
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity() {


    val apiKey = "5bcb8619e0e49f191f8dea4637e8293f"

    lateinit var l1 : LinearLayout
    lateinit var l2 : LinearLayout
    lateinit var l3 : LinearLayout
    lateinit var l4 : LinearLayout
    lateinit var zipCodeInput : EditText
    lateinit var goToTemp : Button
    lateinit var error : TextView
    lateinit var refresh : LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
       var ZipCode ="12210"
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getCurrentWeather( ZipCode)
        error = findViewById(R.id.error)

        zipCodeInput = findViewById(R.id.zipCode)
        goToTemp = findViewById(R.id.Go)
        l2 = findViewById(R.id.l2)



        l2.setOnClickListener{
            MaketempUnVisable()
            MaketZipEntringVisable()




        }

        goToTemp.setOnClickListener {

            ZipCode = zipCodeInput.text.toString()
            getCurrentWeather(ZipCode)
            error.isVisible = false
            MaketempVisable()
            MaketZipEntringUnVisable()


        }

        findViewById<LinearLayout>(R.id.refreshCard).setOnClickListener {

            getCurrentWeather(ZipCode)
        }







    }






    private fun getCurrentWeather(zipCode : String ) {

        val api = Retrofit.Builder()
            .baseUrl("https://api.adviceslip.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIInterface::class.java)



        CoroutineScope(IO).launch {


                val response: Response<Weather?> =
                    api.getWeatherData("https://api.openweathermap.org/data/2.5/weather?zip=$zipCode&units=metric&appid=$apiKey")!!
                        .awaitResponse()

                if (response.isSuccessful) {
                    val nowWeather = response.body()!!
                    //   Log.d(MainActivity , data.toString())

                    withContext(Main) {

                        var date = findViewById<TextView>(R.id.date)
                        date.text = getCurrentDate()

                        var city = findViewById<TextView>(R.id.city)
                        city.text = nowWeather.name

                        var status = findViewById<TextView>(R.id.status)
                        status.text = nowWeather.weather.get(0).description

                        var temp = findViewById<TextView>(R.id.temp)
                        temp.text = String.format("%.0f", nowWeather.main.temp) + "°C"


                        var templow = findViewById<TextView>(R.id.low)
                        templow.text =
                            "LOW :" + String.format("%.0f", nowWeather.main.temp_min) + "°C"


                        var temphigh = findViewById<TextView>(R.id.high)
                        temphigh.text =
                            "HIGH :" + String.format("%.0f", nowWeather.main.temp_max) + "°C"


                        var sunrice = findViewById<TextView>(R.id.sunrise)
                        sunrice.text =
                            SimpleDateFormat("hh:mm ").format(Date(nowWeather.sys.sunrise!!.toLong() * 1000)) + "Am"

                        var sunset = findViewById<TextView>(R.id.sunset)
                        sunset.text =
                            SimpleDateFormat("hh:mm ").format(Date(nowWeather.sys.sunset!!.toLong() * 1000)) + "Pm"

                        var wind = findViewById<TextView>(R.id.wind)
                        wind.text = String.format("%.2f", nowWeather.wind.speed)


                        var pressure = findViewById<TextView>(R.id.pressure)
                        pressure.text = nowWeather.main.pressure.toString()


                        var humidity = findViewById<TextView>(R.id.humidity)
                        humidity.text = nowWeather.main.humidity.toString()


                    }


                }

                else{

                    withContext(Main){

                        error.isVisible = true
                        MaketempUnVisable()
                        MaketZipEntringVisable()

                    }


                }




        }

    }


    fun getCurrentDate():String{

     var date = Date()
     val formatter = SimpleDateFormat("MMM dd yyyy HH:mma")
     val date1: String = formatter.format(date)

     return date1 ;
 }


    fun MaketempUnVisable(){
        l1 = findViewById(R.id.l1)
        l1.isVisible = false
        l2 = findViewById(R.id.l2)
        l2.isVisible = false
        l3 = findViewById(R.id.l3)
        l3.isVisible = false


    }

    fun MaketempVisable(){
        l1 = findViewById(R.id.l1)
        l1.isVisible = true
        l2 = findViewById(R.id.l2)
        l2.isVisible = true
        l3 = findViewById(R.id.l3)
        l3.isVisible = true


    }

    fun MaketZipEntringVisable(){
        l4 = findViewById(R.id.l4)
        l4.isVisible = true



    }

    fun MaketZipEntringUnVisable(){
        l4 = findViewById(R.id.l4)
        l4.isVisible = false



    }

    }









