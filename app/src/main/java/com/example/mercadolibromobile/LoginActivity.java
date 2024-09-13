package com.example.mercadolibromobile;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import android.text.TextUtils;

import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout usernameLayout;  // Layout para el campo de correo electrónico
    private TextInputLayout passwordLayout;  // Layout para el campo de contraseña
    private TextInputEditText usernameEditText; // Campo de texto para el correo electrónico
    private TextInputEditText passwordEditText; // Campo de texto para la contraseña
    private Button loginButton;  // Botón para iniciar sesión
    private Button registerButton; // Botón para registrarse

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;


        });
        // Vinculación de las vistas con el XML
        usernameLayout = findViewById(R.id.textInputLayout2); // TextInputLayout para el correo electrónico
        passwordLayout = findViewById(R.id.textInputLayout); // TextInputLayout para la contraseña
        usernameEditText = findViewById(R.id.textInputEditText); // Campo de texto para el correo electrónico
        passwordEditText = findViewById(R.id.textInputEditText); // Campo de texto para la contraseña
        loginButton = findViewById(R.id.button); // Botón de iniciar sesión
        registerButton = findViewById(R.id.button3); // Botón de registrarse


        loginButton.setOnClickListener(v -> validateFields());
    }

    // Método para validar los campos
    private void validateFields() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        boolean isValid = true; // Variable para controlar si la validación es exitosa

        // Validar el campo de correo electrónico
        if (TextUtils.isEmpty(username)) {
            usernameLayout.setError("El correo electrónico es obligatorio");
            isValid = false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
            usernameLayout.setError("Ingrese un correo electrónico válido");
            isValid = false;
        } else {
            usernameLayout.setError(null); // Quitar el error si es válido
        }


        if (TextUtils.isEmpty(password)) {
            passwordLayout.setError("La contraseña es obligatoria");
            isValid = false;
        } else if (password.length() < 6) {
            passwordLayout.setError("La contraseña debe tener al menos 6 caracteres");
            isValid = false;
        } else {
            passwordLayout.setError(null); // Quitar el error si es válido
        }

    }
}