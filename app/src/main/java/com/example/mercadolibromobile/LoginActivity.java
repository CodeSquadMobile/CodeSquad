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
import android.widget.TextView; // Importar TextView
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout usernameLayout;  // Layout para el campo de correo electrónico
    private TextInputLayout passwordLayout;  // Layout para el campo de contraseña
    private TextInputEditText usernameEditText; // Campo de texto para el correo electrónico
    private TextInputEditText passwordEditText; // Campo de texto para la contraseña
    private Button loginButton;  // Botón para iniciar sesión


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
        usernameEditText = findViewById(R.id.textInputEditTextUsername); // Campo de texto para el correo electrónico
        passwordEditText = findViewById(R.id.textInputEditTextPassword); // Campo de texto para la contraseña
        loginButton = findViewById(R.id.button); // Botón de iniciar sesión
        

        // Vincular el TextView de políticas de privacidad y seg
        TextView politicasTextView = findViewById(R.id.textView11);
        politicasTextView.setOnClickListener(v -> {
            // Iniciar cuando hace click
            Intent intent = new Intent(LoginActivity.this, Politicas.class);
            startActivity(intent);
        });

        // Deshabilitar el botón y ponerlo en gris al inicio
        loginButton.setEnabled(false);
        loginButton.setBackgroundColor(getResources().getColor(android.R.color.darker_gray)); // Color gris al deshabilitar

        // Agregar TextWatcher a ambos campos
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateButtonState(); // Verificar el estado del botón
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        usernameEditText.addTextChangedListener(textWatcher);
        passwordEditText.addTextChangedListener(textWatcher);

        loginButton.setOnClickListener(v -> validateFields());
    }

    // Método para validar los campos
    private void validateFields() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        boolean esValido = true; // Variable para controlar si la validación es exitosa

        // Validar el campo de correo electrónico
        if (TextUtils.isEmpty(username)) {
            usernameLayout.setError("El correo electrónico es obligatorio");
            esValido = false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
            usernameLayout.setError("Ingrese un correo electrónico válido");
            esValido = false;
        } else {
            usernameLayout.setError(null); // Eliminar el error si es válido
        }

        // Validar el campo de contraseña
        if (TextUtils.isEmpty(password)) {
            passwordLayout.setError("La contraseña es obligatoria");
            esValido = false;
        } else if (password.length() < 8) {
            passwordLayout.setError("La contraseña debe tener al menos 8 caracteres");
            esValido = false; //si no tiene 8 caracteres o mas , se marca como no valido
        } else {
            passwordLayout.setError(null); // Eliminar el error si la contraseña es válida
        }


    }

    // Método para verificar el estado del botón
    private void validateButtonState() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Habilita el botón si se ingresa cualquier palabra en el campo de usuario
        boolean isEnabled = !TextUtils.isEmpty(username);
        loginButton.setEnabled(isEnabled);

        // Cambia el color de fondo del botón según su estado
        if (isEnabled) {
            loginButton.setBackgroundColor(getResources().getColor(R.color.azul)); // Color original (puedes cambiarlo a tu color deseado)
        } else {
            loginButton.setBackgroundColor(getResources().getColor(android.R.color.darker_gray)); // Color gris al deshabilitar
        }
    }
}
