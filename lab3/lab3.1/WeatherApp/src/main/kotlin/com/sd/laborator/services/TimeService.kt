package com.sd.laborator.services

import com.sd.laborator.interfaces.handler
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.util.*

@Service
class TimeService : handler {
    var urmatorul: handler?=null
    var raspuns:String=""

    override fun getResponse(): Any {
        return raspuns
    }

    override fun setNext(h: handler) {
        urmatorul=h
    }

    override fun handle(request: Any) {
        val formatter =  SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        val data=formatter.format(Date())
        urmatorul?.handle(listOf(request,data))
    }
}