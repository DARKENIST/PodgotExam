package com.example.podgotexam;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class SecondActivity {
    private Switch switch1, switch2, switch3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        // Показать текущие состояния при запуске
        //Toast.makeText(this, "Switch1: " + (switch1.isChecked() ? "ВКЛ" : "ВЫКЛ"), Toast.LENGTH_SHORT).show();

        switch1 = findViewById(R.id.switch1);
        switch2 = findViewById(R.id.switch2);
        switch3 = findViewById(R.id.switch3);

        // Обработчик для Switch 1
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(SecondActivity.this, "Переключатель 1 ВКЛЮЧЁН", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SecondActivity.this, "Переключатель 1 ВЫКЛЮЧЕН", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Обработчик для Switch 2
        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(SecondActivity.this, "Режим 2 активирован", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SecondActivity.this, "Режим 2 деактивирован", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Обработчик для Switch 3
        switch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(SecondActivity.this, "Третий переключатель включён", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SecondActivity.this, "Третий переключатель выключен", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // RadioButton
        radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio1) {
                    Toast.makeText(SecondActivity.this, "Выбран вариант 1", Toast.LENGTH_SHORT).show();
                } else if (checkedId == R.id.radio2) {
                    Toast.makeText(SecondActivity.this, "Выбран вариант 2", Toast.LENGTH_SHORT).show();
                } else if (checkedId == R.id.radio3) {
                    Toast.makeText(SecondActivity.this, "Выбран вариант 3", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

