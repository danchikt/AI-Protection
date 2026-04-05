package com.aiprotection

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class NotificationListener : NotificationListenerService() {
    
    private val client = OkHttpClient()
    
    // 🔑 ВСТАВЬ СВОЙ КЛЮЧ СЮДА (вместо "sk-твой_ключ")
    private val DEEPSEEK_API_KEY = "sk-04b96332c4e94cb4add65d2c66d7670a"
    
    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        sbn?.let {
            val packageName = it.packageName
            val title = it.notification?.extras?.getString(android.app.Notification.EXTRA_TITLE) ?: ""
            val text = it.notification?.extras?.getString(android.app.Notification.EXTRA_TEXT) ?: ""
            val fullMessage = "$title $text"
            
            when {
                packageName.contains("telegram") -> checkWithDeepSeek(fullMessage, "TELEGRAM")
                packageName.contains("vk") -> checkWithDeepSeek(fullMessage, "VK")
            }
        }
    }
    
    private fun checkWithDeepSeek(message: String, messenger: String) {
        // Пропускаем пустые сообщения
        if (message.isBlank()) return
        
        val json = JSONObject().apply {
            put("model", "deepseek-chat")
            put("messages", org.json.JSONArray().apply {
                put(JSONObject().apply {
                    put("role", "system")
                    put("content", "Ты антивирус для сообщений. Отвечай только одним словом: ОПАСНО или БЕЗОПАСНО. ОПАСНО если сообщение похоже на мошенничество, спам, фишинг, просьбу денег, кода из смс, выигрыша, взлома аккаунта.")
                })
                put(JSONObject().apply {
                    put("role", "user")
                    put("content", message)
                })
            })
            put("max_tokens", 10)
        }
        
        val request = Request.Builder()
            .url("https://api.deepseek.com/v1/chat/completions")
            .addHeader("Authorization", "Bearer $DEEPSEEK_API_KEY")
            .addHeader("Content-Type", "application/json")
            .post(RequestBody.create(MediaType.parse("application/json"), json.toString()))
            .build()
        
        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                Log.e("AI Protection", "Ошибка: ${e.message}")
            }
            
            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                response.use {
                    if (it.isSuccessful) {
                        val responseBody = it.body?.string()
                        val result = responseBody?.let { parseResponse(it) } ?: "Ошибка"
                        Log.w("AI Protection", "🤖 DeepSeek: $result для [$messenger]: $message")
                    }
                }
            }
        })
    }
    
    private fun parseResponse(jsonResponse: String): String {
        return try {
            val json = JSONObject(jsonResponse)
            val content = json.getJSONArray("choices")
                .getJSONObject(0)
                .getJSONObject("message")
                .getString("content")
            content.trim()
        } catch (e: Exception) {
            "Ошибка парсинга"
        }
    }
    
    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        // Ничего не делаем
    }
}
