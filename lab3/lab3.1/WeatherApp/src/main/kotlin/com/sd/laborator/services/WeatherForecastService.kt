package com.sd.laborator.services

import com.sd.laborator.interfaces.handler
import com.sd.laborator.pojo.WeatherForecastData
import org.json.JSONObject
import org.springframework.stereotype.Service
import java.net.URL
import kotlin.math.roundToInt

@Service
class WeatherForecastService (private val timeService: TimeService) : handler {
    lateinit var raspuns:WeatherForecastData

    var urmatorul: handler?=null


    override fun getResponse(): Any {
        return raspuns
    }

    override fun setNext(h: handler) {
        urmatorul=h
    }

    override fun handle(request: Any) {
        val parametru:List<Any> = request as List<Any>
        val id:Int=parametru.elementAt(0) as Int
        // ID-ul locaţiei nu trebuie codificat, deoarece este numeric
        val forecastDataURL = URL("https://www.metaweather.com/api/location/"+id+"/")

        // preluare conţinut răspuns HTTP la o cerere GET către URL-ul de mai sus
        val rawResponse: String = forecastDataURL.readText()

        // parsare obiect JSON primit
        val responseRootObject = JSONObject(rawResponse)
        val weatherDataObject = responseRootObject.getJSONArray("consolidated_weather").getJSONObject(0)
        val data1:String=parametru.elementAt(1) as String
        // construire şi returnare obiect POJO care încapsulează datele meteo
        raspuns=WeatherForecastData(
            location = responseRootObject.getString("title"),
            date =data1,
            weatherState = weatherDataObject.getString("weather_state_name"),
            weatherStateIconURL =
            "https://www.metaweather.com/static/img/weather/png/${weatherDataObject.getString("weather_state_abbr")}.png",
            windDirection = weatherDataObject.getString("wind_direction_compass"),
            windSpeed = weatherDataObject.getFloat("wind_speed").roundToInt(),
            minTemp = weatherDataObject.getFloat("min_temp").roundToInt(),
            maxTemp = weatherDataObject.getFloat("max_temp").roundToInt(),
            currentTemp = weatherDataObject.getFloat("the_temp").roundToInt(),
            humidity = weatherDataObject.getFloat("humidity").roundToInt()
        )
    }
}