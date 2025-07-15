package com.example.myapplication

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.data.PokerDatabase
import com.example.myapplication.data.PokerRepository
import android.content.Context.RECEIVER_NOT_EXPORTED

class MainActivity : ComponentActivity() {
    
    private val alarmReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == "POKER_ALARM_TRIGGERED") {
                // La alarma se activó desde el sistema
                // El ViewModel se encargará de mostrar el diálogo
            }
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Request notification permission on Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permission = ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
            if (permission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1001)
            }
        }
        
        // Inicializar base de datos
        val database = PokerDatabase.getDatabase(this)
        val repository = PokerRepository(database.pokerSessionDao(), database.pokerPlayerDao())
        
        // Registrar receiver local
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(alarmReceiver, IntentFilter("POKER_ALARM_TRIGGERED"), RECEIVER_NOT_EXPORTED)
        } else {
            registerReceiver(alarmReceiver, IntentFilter("POKER_ALARM_TRIGGERED"))
        }
        
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    PokerScreen(repository)
                }
            }
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        // Desregistrar receiver
        unregisterReceiver(alarmReceiver)
    }
}