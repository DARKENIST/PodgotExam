package com.example.podgotexam;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {

    private static final String DB_URL = "jdbc:postgresql://10.0.2.2:5432/ExamDatabase";
    private static final String DB_USER = "user";
    private static final String DB_PASS = "password";

    private EditText etUsername, etPassword;
    private Button btnSave;
    Button btnToRadio = findViewById(R.id.btnToRadio);
        btnToRadio.setOnClickListener(new View.OnClickListener() {
        @Override
        protected void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, RadioActivity.class);
            startActivity(intent);
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Заполните оба поля", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Запускаем фоновую задачу
                new SaveUserTask().execute(username, password);
            }
        });
    }

    // AsyncTask для сохранения в БД
    private class SaveUserTask extends AsyncTask<String, Void, Boolean> {
        private String errorMessage;

        @Override
        protected Boolean doInBackground(String... params) {
            String username = params[0];
            String password = params[1];

            Connection connection = null;
            try {
                // Загружаем драйвер (для старых версий)
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

                String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, username);
                stmt.setString(2, password); // В реальности пароль нужно хешировать!
                stmt.executeUpdate();

                return true;
            } catch (Exception e) {
                e.printStackTrace();
                errorMessage = e.getMessage();
                return false;
            } finally {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException ignored) {}
                }
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                Toast.makeText(MainActivity.this, "Пользователь сохранён", Toast.LENGTH_LONG).show();
                etUsername.setText("");
                etPassword.setText("");
            } else {
                Toast.makeText(MainActivity.this, "Ошибка: " + errorMessage, Toast.LENGTH_LONG).show();
            }
        }

    }
}