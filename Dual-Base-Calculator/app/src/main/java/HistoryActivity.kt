package com.example.tema1b // Înlocuiește cu numele pachetului tău

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class HistoryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HistoryScreen(
                onBack = { finish() },
                onSendMail = {
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_SUBJECT, "Istoric Calculator")
                        putExtra(Intent.EXTRA_TEXT, CalculatorData.history.joinToString("\n"))
                    }
                    startActivity(Intent.createChooser(intent, "Trimite Email"))
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(onBack: () -> Unit, onSendMail: () -> Unit) {
    val listState = rememberLazyListState()

    // Scroll automat la final
    LaunchedEffect(CalculatorData.history.size) {
        if (CalculatorData.history.isNotEmpty()) listState.animateScrollToItem(CalculatorData.history.size - 1)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Istoric") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Inapoi") } },
                actions = { IconButton(onClick = onSendMail) { Icon(Icons.Default.Email, "Mail") } }
            )
        }
    ) { padding ->
        LazyColumn(state = listState, modifier = Modifier.padding(padding).fillMaxSize()) {
            items(CalculatorData.history) { entry ->
                // Detectare bază pentru colorare (dacă conține litere hexa)
                val isHex = entry.any { it in 'A'..'F' }
                Text(
                    text = entry,
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    color = if (isHex) Color(0xFFE91E63) else Color(0xFF4CAF50),
                    style = MaterialTheme.typography.bodyLarge
                )
                Divider()
            }
        }
    }
}