package com.example.podgotexam;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ThirdActivity {
    private static final String DB_URL = "jdbc:postgresql://10.0.2.2:5432/ваша_база";
    private static final String DB_USER = "ваш_пользователь";
    private static final String DB_PASS = "ваш_пароль";

    private TextView tvGreeting;
    private String login; // переданный логин

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        tvGreeting = findViewById(R.id.tvGreeting);

        // Получаем логин из Intent (он был передан после сохранения)
        login = getIntent().getStringExtra("USERNAME");
        if (login == null || login.isEmpty()) {
            tvGreeting.setText("Ошибка: пользователь не указан");
            return;
        }

        // Запускаем фоновый запрос к БД
        new GetUserTask().execute(login);
    }

    // AsyncTask для получения имени из БД
    private class GetUserTask extends AsyncTask<String, Void, String> {
        private String errorMsg;

        @Override
        protected String doInBackground(String... params) {
            String username = params[0];
            String resultName = null;
            Connection connection = null;

            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

                // Запрос: получаем имя пользователя (или username, если отдельного поля нет)
                String sql = "SELECT username FROM users WHERE username = ?";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    resultName = rs.getString("username"); // или "full_name", если есть
                } else {
                    errorMsg = "Пользователь не найден в БД";
                }

                rs.close();
                stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
                errorMsg = e.getMessage();
            } finally {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException ignored) {}
                }
            }
            return resultName;
        }

        @Override
        protected void onPostExecute(String name) {
            if (name != null) {
                tvGreeting.setText("Привет, " + name + "!");
            } else {
                tvGreeting.setText("Ошибка загрузки данных");
                Toast.makeText(ThirdActivity.this, "Ошибка: " + errorMsg, Toast.LENGTH_LONG).show();
            }
        }
    }
}
