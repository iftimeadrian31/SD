package com.sd.laborator.interfaces

import com.sd.laborator.model.Stack

interface PrimeNumberGenerator {
    fun executeOperation(): Set<Pair<Int, Int>>?
    fun setNext(next:CartesianProductOperation)
    fun getA(): Stack?
    fun getB(): Stack?

}