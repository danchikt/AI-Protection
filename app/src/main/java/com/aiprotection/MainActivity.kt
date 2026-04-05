package com.aiprotection

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    
    private var selectedMessenger = ""
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        val btnConnect = findViewById<Button>(R.id.btnConnect)
        val btnTelegram = findViewById<Button>(R.id.btnTelegram)
        val btnVk = findViewById<Button>(R.id.btnVk)
        
        btnTelegram.setOnClickListener { 
            selectedMessenger = "TELEGRAM"
            btnConnect.isEnabled = true
            Toast.makeText(this, "Выбран Telegram", Toast.LENGTH_SHORT).show()
        }
        
        btnVk.setOnClickListener { 
            selectedMessenger = "VK"
            btnConnect.isEnabled = true
            Toast.makeText(this, "Выбран VK", Toast.LENGTH_SHORT).show()
        }
        
        btnConnect.setOnClickListener {
            if (selectedMessenger.isNotEmpty()) {
                requestNotificationPermission()
            }
        }
    }
    
    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (!notificationManager.areNotificationsEnabled()) {
                startActivity(Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS))
            }
        }
        startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
        Toast.makeText(this, "Включи доступ к уведомлениям", Toast.LENGTH_LONG).show()
    }
}
