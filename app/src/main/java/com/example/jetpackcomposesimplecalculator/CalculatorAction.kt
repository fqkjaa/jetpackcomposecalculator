package com.example.jetpackcomposesimplecalculator

sealed class CalculatorAction {
    data class Number(val number: Int) : CalculatorAction()
    data class Operation(val operation: CalculatorOperation) : CalculatorAction()
    data object Clear : CalculatorAction()
    data object Delete : CalculatorAction()
    data object PlaceDecimal : CalculatorAction()
    data object Calculate : CalculatorAction()
}