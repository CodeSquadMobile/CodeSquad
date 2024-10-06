package com.example.mercadolibromobile;

import android.content.Intent;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout usernameLayout;
    private TextInputLayout passwordLayout;
    private TextInputEditText usernameEditText;
    private TextInputEditText passwordEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // Ajustes para manejar insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Vinculación de las vistas con el XML
        usernameLayout = findViewById(R.id.textInputLayout2);
        passwordLayout = findViewById(R.id.textInputLayout);
        usernameEditText = findViewById(R.id.textInputEditTextUsername);
        passwordEditText = findViewById(R.id.textInputEditTextPassword);
        loginButton = findViewById(R.id.button);  // Aquí estamos usando el android:id="@+id/button"

        // Vincular el TextView de políticas de privacidad
        TextView politicasTextView = findViewById(R.id.textView11);
        politicasTextView.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, Politicas.class);
            startActivity(intent);
        });

        // Deshabilitar el botón de inicio al inicio
        loginButton.setEnabled(false);
        loginButton.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));

        // Agregar TextWatcher a ambos campos
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateButtonState();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        usernameEditText.addTextChangedListener(textWatcher);
        passwordEditText.addTextChangedListener(textWatcher);

        // Botón de login (navegar a otra actividad sin autenticación)
        loginButton.setOnClickListener(v -> validateFields());
    }

    // Método para validar los campos
    private void validateFields() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        boolean esValido = true;

        // Validar el campo de correo electrónico
        if (TextUtils.isEmpty(username)) {
            usernameLayout.setError("El correo electrónico es obligatorio");
            esValido = false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
            usernameLayout.setError("Ingrese un correo electrónico válido");
            esValido = false;
        } else {
            usernameLayout.setError(null);
        }

        // Validar el campo de contraseña
        if (TextUtils.isEmpty(password)) {
            passwordLayout.setError("La contraseña es obligatoria");
            esValido = false;
        } else if (password.length() < 8) {
            passwordLayout.setError("La contraseña debe tener al menos 8 caracteres");
            esValido = false;
        } else {
            passwordLayout.setError(null);
        }

        // Si ambos campos son válidos, navega a MainActivity
        if (esValido) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();  // Cierra LoginActivity
        }
    }

    // Método para verificar el estado del botón
    private void validateButtonState() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        boolean isEnabled = !TextUtils.isEmpty(username);
        loginButton.setEnabled(isEnabled);

        // color de fondo del botón según su estado
        if (isEnabled) {
            loginButton.setBackgroundColor(getResources().getColor(R.color.azul_electrico)); // Cambiar color a azul
        } else {
            loginButton.setBackgroundColor(getResources().getColor(android.R.color.darker_gray)); // Color gris si está deshabilitado
        }
    }
}
