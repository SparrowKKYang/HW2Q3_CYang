package com.example.hw2q3

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hw2q3.ui.theme.HW2Q3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HW2Q3Theme {
                CalculatorApp()
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CalculatorApp() {
    var input by remember { mutableStateOf("0") }
    var operand1 by remember { mutableStateOf<Double?>(null) }
    var operand2 by remember { mutableStateOf<Double?>(null) }
    var operation by remember { mutableStateOf<Char?>(null) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = {
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Display
                Text(
                    text = input,
                    fontSize = 36.sp,
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    maxLines = 1
                )

                // Button Grid Layout
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // First Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        CalculatorButton("7", Modifier.size(80.dp)) { input = updateInput(input, "7") }
                        CalculatorButton("8", Modifier.size(80.dp)) { input = updateInput(input, "8") }
                        CalculatorButton("9", Modifier.size(80.dp)) { input = updateInput(input, "9") }
                        CalculatorButton("÷", Modifier.size(80.dp)) { operation = '÷'; operand1 = input.toDoubleOrNull(); input = "0" }
                    }

                    // Second Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        CalculatorButton("4", Modifier.size(80.dp)) { input = updateInput(input, "4") }
                        CalculatorButton("5", Modifier.size(80.dp)) { input = updateInput(input, "5") }
                        CalculatorButton("6", Modifier.size(80.dp)) { input = updateInput(input, "6") }
                        CalculatorButton("×", Modifier.size(80.dp)) { operation = '×'; operand1 = input.toDoubleOrNull(); input = "0" }
                    }

                    // Third Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        CalculatorButton("1", Modifier.size(80.dp)) { input = updateInput(input, "1") }
                        CalculatorButton("2", Modifier.size(80.dp)) { input = updateInput(input, "2") }
                        CalculatorButton("3", Modifier.size(80.dp)) { input = updateInput(input, "3") }
                        CalculatorButton("-", Modifier.size(80.dp)) { operation = '-'; operand1 = input.toDoubleOrNull(); input = "0" }
                    }

                    // Fourth Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        CalculatorButton("0", Modifier.size(80.dp)) { input = updateInput(input, "0") }
                        CalculatorButton(".", Modifier.size(80.dp)) { if (!input.contains(".")) input += "." }
                        CalculatorButton("C", Modifier.size(80.dp)) { input = "0"; operand1 = null; operand2 = null; operation = null }
                        CalculatorButton("+", Modifier.size(80.dp)) { operation = '+'; operand1 = input.toDoubleOrNull(); input = "0" }
                    }

                    // Fifth Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        CalculatorButton("√", Modifier.size(80.dp)) { input = sqrt(input) }
                        CalculatorButton("=", Modifier.size(80.dp)) {
                            operand2 = input.toDoubleOrNull()
                            if (operand1 != null && operand2 != null) {
                                input = calculateResult(operand1!!, operand2!!, operation).toString()
                                operand1 = null
                                operand2 = null
                                operation = null
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun CalculatorButton(symbol: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier.padding(4.dp)
    ) {
        Text(text = symbol, fontSize = 24.sp)
    }
}

fun updateInput(current: String, newChar: String): String {
    return if (current == "0") newChar else current + newChar
}

fun sqrt(input: String): String {
    val number = input.toDoubleOrNull()
    return if (number != null) {
        kotlin.math.sqrt(number).toString()
    } else {
        "Error"
    }
}

fun calculateResult(operand1: Double, operand2: Double, operation: Char?): Double {
    return when (operation) {
        '+' -> operand1 + operand2
        '-' -> operand1 - operand2
        '×' -> operand1 * operand2
        '÷' -> if (operand2 != 0.0) operand1 / operand2 else Double.NaN
        else -> Double.NaN
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorAppPreview() {
    HW2Q3Theme {
        CalculatorApp()
    }
}
