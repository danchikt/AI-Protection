package com.aiprotection

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class NotificationListener : NotificationListenerService() {
    
    private val client = OkHttpClient()
    
    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        sbn?.let {
            val packageName = it.packageName
            val title = it.notification?.extras?.getString(android.app.Notification.EXTRA_TITLE) ?: ""
            val text = it.notification?.extras?.getString(android.app.Notification.EXTRA_TEXT) ?: ""
            val fullMessage = "$title $text"
            
            when {
                packageName.contains("telegram") -> checkWithAI(fullMessage, "TELEGRAM")
                packageName.contains("vk") -> checkWithAI(fullMessage, "VK")
            }
        }
    }
    
    private fun checkWithAI(message: String, messenger: String) {
        val dangerousWords = listOf(
            "код из смс", "выиграл", "приз", "скинь деньги", 
            "аккаунт взломан", "карта заблокирована", "госуслуги",
            "подтверди вход", "перейди по ссылке", "ваучер"
        )
        
        for (word in dangerousWords) {
            if (message.lowercase().contains(word)) {
                Log.w("AI Protection", "⚠️ ОПАСНО в $messenger: $message")
                break
            }
        }
    }
    
    override fun onNotificationRemoved(sbn: StatusBarNotification?) {}
}
