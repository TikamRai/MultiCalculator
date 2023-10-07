package com.example.multicalculator

class Calculator {
    fun Add (left: Int, right: Int): Int {
        return left + right
    }

    fun Subtract (left: Int, right: Int): Int {
        return left - right
    }

    fun Divide (left: Int, right: Int): Double {
        return (left / right).toDouble()
    }

    fun Multiply (left: Int, right: Int): Int {
        return left * right
    }
}