package com.sd.laborator.services

import com.sd.laborator.interfaces.CartesianProductOperation
import com.sd.laborator.interfaces.UnionOperation
import org.springframework.stereotype.Service

@Service
class CartesianProductService: CartesianProductOperation {
    private var next:UnionOperation?=null

    override fun setNext(Next:UnionOperation)
    {
        next=Next
    }
    override fun executeOperation(A: Set<Int>, B: Set<Int>): Set<Pair<Int, Int>>? {
        var result1: MutableSet<Pair<Int, Int>> = mutableSetOf()
        A.forEach { a -> B.forEach { b -> result1.add(Pair(a, b)) } }
        result1.toSet()
        var result2: MutableSet<Pair<Int, Int>> = mutableSetOf()
        B.forEach { a -> B.forEach { b -> result2.add(Pair(a, b)) } }
        result2.toSet()
        return next?.executeOperation(result1,result2)
    }
}