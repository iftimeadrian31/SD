package com.sd.laborator.interfaces

interface handler {
    fun getResponse(): Any
    fun setNext(h: handler)
    fun handle(request:Any)
}