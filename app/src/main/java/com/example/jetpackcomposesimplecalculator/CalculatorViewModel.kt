package com.example.jetpackcomposesimplecalculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel() {
    var state by mutableStateOf(CalculatorState())
        private set

    fun onAction(action: CalculatorAction) {
        when (action) {
            is CalculatorAction.Number -> enterNumber(action.number)
            is CalculatorAction.Operation -> addOperation(action.operation)
            is CalculatorAction.Clear -> state = CalculatorState()
            is CalculatorAction.Delete -> performDeletion()
            is CalculatorAction.PlaceDecimal -> makeDecimal()
            is CalculatorAction.Calculate -> performCalculation()
        }
    }

    private fun performDeletion() {
        when {
            state.secondNumber.isNotBlank() -> {
                state = state.copy(
                    secondNumber = state.secondNumber.dropLast(1)
                )
            }

            state.operation != null -> {
                state = state.copy(
                    operation = null
                )
            }

            state.firstNumber.isNotBlank() -> {
                state = state.copy(
                    firstNumber = state.firstNumber.dropLast(1)
                )
            }
        }
    }

    private fun makeDecimal() {
        if (state.operation == null &&
            state.firstNumber.isNotBlank() &&
            !state.firstNumber.contains(".")
        ) {
            state = state.copy(
                firstNumber = state.firstNumber + "."
            )
            return
        }

        if (state.secondNumber.isNotBlank() &&
            !state.secondNumber.contains(".")
        ) {
            state = state.copy(
                secondNumber = state.secondNumber + "."
            )
        }
    }

    private fun performCalculation() {
        val number1 = state.firstNumber.toDoubleOrNull()
        val number2 = state.secondNumber.toDoubleOrNull()
        if (number1 != null && number2 != null) {
            val result =
                when (state.operation) {
                    is CalculatorOperation.Add -> number1 + number2
                    is CalculatorOperation.Subtract -> number1 - number2
                    is CalculatorOperation.Multiply -> number1 * number2
                    is CalculatorOperation.Divide -> number1 / number2
                    else -> return
                }
            state = state.copy(
                firstNumber = result.toString().take(15),
                operation = null,
                secondNumber = ""
            )
        }
    }

    private fun addOperation(operation: CalculatorOperation) {
        if (state.firstNumber.isNotBlank()) {
            state = state.copy(
                operation = operation
            )
        }
    }

    private fun enterNumber(number: Int) {
        if (state.operation == null) {
            if (state.firstNumber.length < NUMBER_MAX_LENGTH) {
                state = state.copy(
                    firstNumber = state.firstNumber + number
                )
            }
            return
        }

        if (state.operation != null && state.secondNumber.length < NUMBER_MAX_LENGTH) {
            state = state.copy(
                secondNumber = state.secondNumber + number
            )
            return
        }
    }



    companion object {
        private const val NUMBER_MAX_LENGTH = 8
    }
}