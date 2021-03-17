package com.sd.laborator.services

import com.sd.laborator.interfaces.DirijorInterface
import com.sd.laborator.interfaces.LocationSearchInterface
import com.sd.laborator.interfaces.TimeInterface
import com.sd.laborator.interfaces.WeatherForecastInterface
import com.sd.laborator.pojo.WeatherForecastData
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PathVariable

@Service
class DirijorService (private var locationSearchService: LocationSearchInterface, private var weatherForecastService: WeatherForecastInterface,private var timeService: TimeInterface):DirijorInterface
{

    override fun getForecast(location: String): String {
        val locationId = locationSearchService.getLocationId(location)
        if (locationId == -1) {
            return "Nu s-au putut gasi date meteo pentru cuvintele cheie \"$location\"!"
        }
        val data = timeService.getCurrentTime()

        val rawForecastData: WeatherForecastData = weatherForecastService.getForecastData(locationId, data)

        return rawForecastData.toString()
    }
}