package com.sd.laborator.controllers

import com.sd.laborator.interfaces.handler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class WeatherAppController {
    @Autowired
    private lateinit var locationSearchService: handler

    @Autowired
    private lateinit var weatherForecastService: handler

    @Autowired
    private lateinit var timeService: handler

    @RequestMapping("/getforecast/{location}", method = [RequestMethod.GET])
    @ResponseBody
    fun getForecast(@PathVariable location: String): String {
        // se incearca preluarea WOEID-ului locaţiei primite in URL
        timeService.setNext(weatherForecastService)
        locationSearchService.setNext(timeService)
        locationSearchService.handle(location)
        if(locationSearchService.getResponse()!="")
            return locationSearchService.getResponse().toString()
        else
            return weatherForecastService.getResponse().toString()
    }
}