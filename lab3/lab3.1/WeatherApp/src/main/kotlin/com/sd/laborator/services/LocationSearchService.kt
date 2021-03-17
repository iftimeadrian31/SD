package com.sd.laborator.services

import com.sd.laborator.interfaces.handler
import org.springframework.stereotype.Service
import java.net.URL
import org.json.JSONObject
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Service
class LocationSearchService : handler {
    var urmatorul: handler?=null
    var raspuns:String=""

    override fun getResponse(): Any {
        return raspuns
    }

    override fun setNext(h: handler) {
        urmatorul=h
    }
    override  fun handle(request:Any)
    {
        // codificare parametru URL (deoarece poate conţine caractere speciale)
        val encodedLocationName = URLEncoder.encode(request.toString(), StandardCharsets.UTF_8.toString())

        // construire obiect de tip URL
        val locationSearchURL = URL("https://www.metaweather.com/api/location/search/?query=$encodedLocationName")

        // preluare raspuns HTTP (se face cerere GET şi se preia conţinutul răspunsului sub formă de text)
        val rawResponse: String = locationSearchURL.readText()

        // parsare obiect JSON
        val responseRootObject = JSONObject("{\"data\": ${rawResponse}}")
        val responseContentObject = responseRootObject.getJSONArray("data").takeUnless { it.isEmpty }
            ?.getJSONObject(0)
        val locationId= responseContentObject?.getInt("woeid") ?: -1
        // dacă locaţia nu a fost găsită, răspunsul va fi corespunzător
        if (locationId == -1) {
            raspuns= "Nu s-au putut gasi date meteo pentru cuvintele cheie \""+request.toString()+"\"!"
        }
        else
        {
            urmatorul?.handle(locationId)
        }

    }
}