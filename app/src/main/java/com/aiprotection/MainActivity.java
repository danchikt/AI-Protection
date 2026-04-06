package com.aiprotection;

import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    
    private Button selectedButton = null;
    private String selectedMessenger = "";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Создаем главный экран
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(40, 60, 40, 40);
        
        // Заголовок
        TextView title = new TextView(this);
        title.setText("AI PROTECTION");
        title.setTextSize(32);
        title.setTextColor(0xFF00E5FF);
        title.setPadding(0, 0, 0, 20);
        
        // Описание
        TextView desc = new TextView(this);
        desc.setText("Защищайся от мошенников и спамеров с помощью ИИ");
        desc.setTextSize(16);
        desc.setTextColor(0xFF8895A4);
        desc.setPadding(0, 0, 0, 40);
        
        // Кнопка подключить
        Button btnConnect = new Button(this);
        btnConnect.setText("ПОДКЛЮЧИТЬ");
        btnConnect.setTextSize(18);
        btnConnect.setEnabled(false);
        
        // Выбор мессенджера
        TextView selectTitle = new TextView(this);
        selectTitle.setText("ВЫБЕРИ МЕССЕНДЖЕР");
        selectTitle.setTextSize(14);
        selectTitle.setTextColor(0xFF00E5FF);
        selectTitle.setPadding(0, 30, 0, 15);
        
        Button btnTelegram = new Button(this);
        btnTelegram.setText("TELEGRAM");
        btnTelegram.setTextSize(16);
        
        Button btnVk = new Button(this);
        btnVk.setText("VK");
        btnVk.setTextSize(16);
        
        // Как подключить
        TextView howTitle = new TextView(this);
        howTitle.setText("• КАК ПОДКЛЮЧИТЬ?");
        howTitle.setTextSize(18);
        howTitle.setTextColor(0xFF00E5FF);
        howTitle.setPadding(0, 40, 0, 15);
        
        TextView howText = new TextView(this);
        howText.setText("1) Включи уведомления в мессенджере\n2) Выбери мессенджер\n3) Нажми подключить");
        howText.setTextSize(14);
        howText.setTextColor(0xFF8895A4);
        howText.setPadding(0, 0, 0, 30);
        
        // Что гарантируем
        TextView guarTitle = new TextView(this);
        guarTitle.setText("• ЧТО МЫ ГАРАНТИРУЕМ?");
        guarTitle.setTextSize(18);
        guarTitle.setTextColor(0xFF00E5FF);
        guarTitle.setPadding(0, 0, 0, 15);
        
        TextView guarText = new TextView(this);
        guarText.setText("✓ Безопасность\n✓ Защита персональных данных\n✓ Шифрование");
        guarText.setTextSize(14);
        guarText.setTextColor(0xFF8895A4);
        
        // Логика выбора мессенджера
        btnTelegram.setOnClickListener(v -> {
            selectedMessenger = "TELEGRAM";
            btnConnect.setEnabled(true);
            btnConnect.setText("ПОДКЛЮЧИТЬ - " + selectedMessenger);
            Toast.makeText(this, "Выбран Telegram", Toast.LENGTH_SHORT).show();
        });
        
        btnVk.setOnClickListener(v -> {
            selectedMessenger = "VK";
            btnConnect.setEnabled(true);
            btnConnect.setText("ПОДКЛЮЧИТЬ - " + selectedMessenger);
            Toast.makeText(this, "Выбран VK", Toast.LENGTH_SHORT).show();
        });
        
        // Логика подключения
        btnConnect.setOnClickListener(v -> {
            if (!selectedMessenger.isEmpty()) {
                startActivity(new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS));
                Toast.makeText(this, "Включи доступ к уведомлениям для AI Protection", Toast.LENGTH_LONG).show();
            }
        });
        
        // Добавляем всё на экран
        layout.addView(title);
        layout.addView(desc);
        layout.addView(btnConnect);
        layout.addView(selectTitle);
        layout.addView(btnTelegram);
        layout.addView(btnVk);
        layout.addView(howTitle);
        layout.addView(howText);
        layout.addView(guarTitle);
        layout.addView(guarText);
        
        setContentView(layout);
    }
    }
