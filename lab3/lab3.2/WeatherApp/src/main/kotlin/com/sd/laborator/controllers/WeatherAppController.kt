package com.sd.laborator.controllers

import com.sd.laborator.interfaces.DirijorInterface
import com.sd.laborator.interfaces.LocationSearchInterface
import com.sd.laborator.interfaces.TimeInterface
import com.sd.laborator.interfaces.WeatherForecastInterface
import com.sd.laborator.pojo.WeatherForecastData
import com.sd.laborator.services.DirijorService
import com.sd.laborator.services.TimeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class WeatherAppController {
    @Autowired
    private lateinit var locationSearchService: LocationSearchInterface

    @Autowired
    private lateinit var weatherForecastService: WeatherForecastInterface

    @Autowired
    private lateinit var timeService: TimeInterface

    @Autowired
    private lateinit var dirijor: DirijorInterface



    @RequestMapping("/getforecast/{location}", method = [RequestMethod.GET])
    @ResponseBody
    fun getForecast(@PathVariable location: String): String {

        dirijor=DirijorService(locationSearchService,weatherForecastService,timeService)
        return dirijor.getForecast(location)
    }
}