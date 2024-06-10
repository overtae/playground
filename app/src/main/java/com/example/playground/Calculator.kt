package com.example.playground

class Calculator(private val operation: AbstractOperation) {
    fun calculate(x: Int, y: Int): Int {
        return operation.operate(x, y)
    }
}