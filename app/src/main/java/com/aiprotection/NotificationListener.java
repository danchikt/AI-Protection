package com.aiprotection;

import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

public class NotificationListener extends NotificationListenerService {
    
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        String packageName = sbn.getPackageName();
        String title = sbn.getNotification().extras.getString(android.app.Notification.EXTRA_TITLE, "");
        String text = sbn.getNotification().extras.getString(android.app.Notification.EXTRA_TEXT, "");
        
        String message = title + " " + text;
        
        // Простая проверка на опасные слова
        String[] dangerousWords = {"код из смс", "выиграл", "приз", "скинь деньги", "аккаунт взломан"};
        
        boolean isDangerous = false;
        for (String word : dangerousWords) {
            if (message.toLowerCase().contains(word)) {
                isDangerous = true;
                break;
            }
        }
        
        if (isDangerous) {
            Log.w("AI Protection", "⚠️ ОПАСНОЕ СООБЩЕНИЕ: " + message);
        }
    }
    
    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {}
}
