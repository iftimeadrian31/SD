package com.sd.laborator.services

import com.sd.laborator.interfaces.CartesianProductOperation
import com.sd.laborator.interfaces.PrimeNumberGenerator
import com.sd.laborator.model.Stack
import org.springframework.stereotype.Service

@Service
class PrimeNumberGeneratorService: PrimeNumberGenerator {
    private var A: Stack? = null
    private var B: Stack? = null
    override fun getA(): Stack? {
        return A
    }
    override fun getB(): Stack? {
        return B
    }
    private var next:CartesianProductOperation?=null
    override fun setNext(Next:CartesianProductOperation)
    {
        next=Next
    }

    private val primeNumbersIn1To100: Set<Int> = setOf(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97)
    private fun generateStack(count: Int): Stack? {
        if (count < 1)
            return null
        var X: MutableSet<Int> = mutableSetOf()
        while (X.count() < count)
            X.add(primeNumbersIn1To100.elementAt((0 until primeNumbersIn1To100.count()).random()))
        return Stack(X)
    }
    override fun executeOperation() : Set<Pair<Int, Int>>? {
        A=generateStack(20)
        B=generateStack(20)
        return next?.executeOperation(A!!.data,B!!.data)


    }

}