package com.example.tema1b // Înlocuiește cu numele pachetului tău

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import androidx.compose.runtime.mutableStateListOf

object CalculatorData {
    val history = mutableStateListOf<String>()
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorScreen(onNavigateToHistory = {
                startActivity(Intent(this, HistoryActivity::class.java))
            })
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorScreen(onNavigateToHistory: () -> Unit) {
    var isBase10 by remember { mutableStateOf(true) }
    var textA by remember { mutableStateOf("0") }
    var textC by remember { mutableStateOf("0") }
    var operator by remember { mutableStateOf("") }
    var isEqualsEnabled by remember { mutableStateOf(false) }

    val baseColor = if (isBase10) Color(0xFF4CAF50) else Color(0xFFE91E63) // Verde vs Roz

    val onDigitClick: (String) -> Unit = { digit ->
        if (textC == "0") textC = digit else textC += digit
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Calculator ${if (isBase10) "10" else "16"}") },
                actions = {
                    IconButton(onClick = onNavigateToHistory) {
                        Icon(Icons.Default.List, contentDescription = "Istoric")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp)) {
            // Display
            Column(modifier = Modifier.fillMaxWidth().height(100.dp), horizontalAlignment = Alignment.End) {
                Text(text = "$textA $operator", style = MaterialTheme.typography.headlineSmall, color = Color.Gray)
                Text(text = textC, style = MaterialTheme.typography.displayMedium, color = baseColor)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Rândul 1: 7, 8, 9, A
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalcButton("7", Modifier.weight(1f)) { onDigitClick("7") }
                CalcButton("8", Modifier.weight(1f)) { onDigitClick("8") }
                CalcButton("9", Modifier.weight(1f)) { onDigitClick("9") }
                CalcButton("A", Modifier.weight(1f), enabled = !isBase10) { onDigitClick("A") }
            }
            // Rândul 2: 4, 5, 6, B
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalcButton("4", Modifier.weight(1f)) { onDigitClick("4") }
                CalcButton("5", Modifier.weight(1f)) { onDigitClick("5") }
                CalcButton("6", Modifier.weight(1f)) { onDigitClick("6") }
                CalcButton("B", Modifier.weight(1f), enabled = !isBase10) { onDigitClick("B") }
            }
            // Rândul 3: 1, 2, 3, C
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalcButton("1", Modifier.weight(1f)) { onDigitClick("1") }
                CalcButton("2", Modifier.weight(1f)) { onDigitClick("2") }
                CalcButton("3", Modifier.weight(1f)) { onDigitClick("3") }
                CalcButton("C", Modifier.weight(1f), enabled = !isBase10) { onDigitClick("C") }
            }
            // Rândul 4: 0, D, E, F
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalcButton("0", Modifier.weight(1f)) { onDigitClick("0") }
                CalcButton("D", Modifier.weight(1f), enabled = !isBase10) { onDigitClick("D") }
                CalcButton("E", Modifier.weight(1f), enabled = !isBase10) { onDigitClick("E") }
                CalcButton("F", Modifier.weight(1f), enabled = !isBase10) { onDigitClick("F") }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Rândul 5: 10/16, Back, Operatii
            Row(Modifier.fillMaxWidth(), Arrangement.spacedBy(8.dp)) {
                Button(onClick = {
                    val from = if (isBase10) 10 else 16
                    val to = if (isBase10) 16 else 10
                    textC = textC.toLong(from).toString(to).uppercase()
                    if (textA != "0") textA = textA.toLong(from).toString(to).uppercase()
                    isBase10 = !isBase10
                }, Modifier.weight(1f), colors = ButtonDefaults.buttonColors(containerColor = baseColor)) { Text("10/16") }

                Button(onClick = {
                    textC = if (textC.length > 1) textC.dropLast(1) else "0"
                }, Modifier.weight(1f), colors = ButtonDefaults.buttonColors(containerColor = Color.Red)) { Text("Back") }
            }

            Row(Modifier.fillMaxWidth().padding(top = 8.dp), Arrangement.spacedBy(8.dp)) {
                listOf("+", "-", "*").forEach { op ->
                    Button(onClick = {
                        operator = op; textA = textC; CalculatorData.history.add("$textA $op"); textC = "0"; isEqualsEnabled = true
                    }, Modifier.weight(1f), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800))) { Text(op) }
                }
                Button(onClick = {
                    val b = if (isBase10) 10 else 16
                    val res = when(operator) {
                        "+" -> textA.toLong(b) + textC.toLong(b)
                        "-" -> textA.toLong(b) - textC.toLong(b)
                        "*" -> textA.toLong(b) * textC.toLong(b)
                        else -> 0L
                    }
                    val resStr = res.toString(b).uppercase()
                    CalculatorData.history.add(textC)
                    CalculatorData.history.add("= $resStr")
                    textA = resStr; operator = "="; textC = "0"; isEqualsEnabled = false
                }, Modifier.weight(1f), enabled = isEqualsEnabled, colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)) { Text("=") }
            }
        }
    }
}

@Composable
fun CalcButton(text: String, modifier: Modifier, enabled: Boolean = true, onClick: () -> Unit) {
    ElevatedButton(onClick = onClick, modifier = modifier, enabled = enabled,
        colors = ButtonDefaults.elevatedButtonColors(containerColor = Color.LightGray, contentColor = Color.Black)
    ) { Text(text) }
}