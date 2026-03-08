package com.example.tema1a

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.tema1a.ui.theme.Tema1aTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Tema1aTheme {
                var historyText by remember { mutableStateOf("") }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Row(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
                        Calculator(
                            modifier = Modifier.weight(3f),
                            onOkClick = { newEntry ->
                                historyText += (if (historyText.isEmpty()) "" else "\n") + newEntry
                            }
                        )
                        History(
                            modifier = Modifier.weight(1f),
                            text = historyText
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Calculator(modifier: Modifier = Modifier, onOkClick: (String) -> Unit) {
    val alphaBack = remember { Animatable(1f) }
    val alphaOk = remember { Animatable(1f) }
    val scope = rememberCoroutineScope()
    var display by remember { mutableStateOf("") }

    Column(modifier = modifier.padding(4.dp)) {
        Row(modifier = Modifier.fillMaxWidth().padding(5.dp)) {
            Text(
                text = display,
                textAlign = TextAlign.Right,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.headlineMedium
            )
        }

        val btnMod = Modifier.padding(2.dp).weight(1f)

        Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
            ElevatedButton(onClick = { display += "1" }, modifier = btnMod) { Text("1") }
            ElevatedButton(onClick = { display += "2" }, modifier = btnMod) { Text("2") }
            ElevatedButton(onClick = { display += "3" }, modifier = btnMod) { Text("3") }
            ElevatedButton(onClick = { display += "4" }, modifier = btnMod) { Text("4") }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
            ElevatedButton(onClick = { display += "5" }, modifier = btnMod) { Text("5") }
            ElevatedButton(onClick = { display += "6" }, modifier = btnMod) { Text("6") }
            ElevatedButton(onClick = { display += "7" }, modifier = btnMod) { Text("7") }
            ElevatedButton(onClick = { display += "8" }, modifier = btnMod) { Text("8") }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
            ElevatedButton(onClick = { display += "9" }, modifier = btnMod) { Text("9") }
            ElevatedButton(onClick = { display += "A" }, modifier = btnMod) { Text("A") }
            ElevatedButton(onClick = { display += "B" }, modifier = btnMod) { Text("B") }
            ElevatedButton(onClick = { display += "C" }, modifier = btnMod) { Text("C") }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
            ElevatedButton(onClick = { display += "D" }, modifier = btnMod) { Text("D") }
            ElevatedButton(onClick = { display += "E" }, modifier = btnMod) { Text("E") }
            ElevatedButton(onClick = { display += "F" }, modifier = btnMod) { Text("F") }
            ElevatedButton(onClick = { display += "0" }, modifier = btnMod) { Text("0") }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
            ElevatedButton(
                onClick = {
                    if (display.isNotEmpty()) display = display.dropLast(1)
                    scope.launch {
                        alphaBack.animateTo(0.4f, tween(100))
                        delay(1000L)
                        alphaBack.animateTo(1f, tween(100))
                    }
                },
                modifier = btnMod.alpha(alphaBack.value),
                colors = ButtonDefaults.elevatedButtonColors(containerColor = Color.Red, contentColor = Color.White)
            ) { Text("Back") }

            ElevatedButton(
                onClick = {
                    if (display.isNotEmpty()) {
                        onOkClick(display)
                        display = ""
                    }
                    scope.launch {
                        alphaOk.animateTo(0.4f, tween(100))
                        delay(1000L)
                        alphaOk.animateTo(1f, tween(100))
                    }
                },
                modifier = btnMod.alpha(alphaOk.value),
                colors = ButtonDefaults.elevatedButtonColors(containerColor = Color.Green, contentColor = Color.White)
            ) { Text("OK") }
        }
    }
}

@Composable
fun History(modifier: Modifier = Modifier, text: String) {
    val scrollState = rememberScrollState()

    LaunchedEffect(text) {
        scrollState.animateScrollTo(scrollState.maxValue)
    }

    Column(
        modifier = modifier
            .fillMaxHeight()
            .padding(4.dp)
            .verticalScroll(scrollState)
    ) {
        Text(
            text = "History",
            style = MaterialTheme.typography.titleSmall,
            color = Color.Gray
        )
        Text(
            text = text,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Left,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
