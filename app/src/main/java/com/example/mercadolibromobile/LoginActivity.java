package com.example.mercadolibromobile;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.view.View;

import com.example.mercadolibromobile.api.LoginApi;
import com.example.mercadolibromobile.models.AuthModels;
import com.example.mercadolibromobile.api.RetrofitClient;

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout usernameLayout, passwordLayout, nameLayout;
    private TextInputEditText usernameEditText, passwordEditText, nameEditText;
    private Button loginButton, toggleModeButton;
    private boolean isLoginMode = true;
    private final String BASE_URL = "http://192.168.0.50:8000/api/"; // Cambia por tu URL del backend

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Vinculación de las vistas con el XML
        usernameLayout = findViewById(R.id.textInputLayout2);
        passwordLayout = findViewById(R.id.textInputLayout);
        nameLayout = findViewById(R.id.textInputLayoutName);
        usernameEditText = findViewById(R.id.textInputEditTextUsername);
        passwordEditText = findViewById(R.id.textInputEditTextPassword);
        nameEditText = findViewById(R.id.textInputEditTextName);
        loginButton = findViewById(R.id.buttonMainAction);
        toggleModeButton = findViewById(R.id.buttonToggleMode);

        // cambio entre login y registro
        toggleModeButton.setOnClickListener(v -> toggleLoginMode());

        // Validar los campos de texto
        loginButton.setOnClickListener(v -> {
            if (isLoginMode) {
                loginUser();
            } else {
                registerUser();
            }
        });

        // textwacher para habilitar los botones
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
        nameEditText.addTextChangedListener(textWatcher);
    }

    private void toggleLoginMode() {
        isLoginMode = !isLoginMode;
        if (isLoginMode) {
            loginButton.setText(R.string.ingresar);
            toggleModeButton.setText(R.string.registrarse);
            nameLayout.setVisibility(View.GONE);
        } else {
            loginButton.setText(R.string.registrarse);  // se cambia el texto del botón a "registrarse"
            toggleModeButton.setText(R.string.ingresar);  // Cambia el texto a "Ingresar"
            nameLayout.setVisibility(View.VISIBLE);
        }
        validateButtonState();
    }

    private void validateButtonState() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String name = nameEditText.getText().toString().trim();

        boolean isEnabled = isLoginMode ?
                !TextUtils.isEmpty(username) && !TextUtils.isEmpty(password) :
                !TextUtils.isEmpty(username) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(name);

        loginButton.setEnabled(isEnabled);
    }

    private void loginUser() {
        String email = usernameEditText.getText().toString().trim(); //username para el mail
        String password = passwordEditText.getText().toString().trim();

        // Usar RetrofitClient con baseUrl
        LoginApi api = RetrofitClient.getInstance(BASE_URL).create(LoginApi.class);
        Call<AuthModels.LoginResponse> call = api.login(email, password);

        call.enqueue(new Callback<AuthModels.LoginResponse>() {
            @Override
            public void onResponse(Call<AuthModels.LoginResponse> call, Response<AuthModels.LoginResponse> response) {
                if (response.isSuccessful()) {
                    // Manejar el inicio de sesión exitoso
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Manejar el error de inicio de sesión
                    usernameLayout.setError("Credenciales incorrectas");
                }
            }

            @Override
            public void onFailure(Call<AuthModels.LoginResponse> call, Throwable t) {
                // Manejar fallo o errores  en la conexión
                usernameLayout.setError("Error de conexión");
            }
        });
    }

    private void registerUser() {
        String email = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String username = nameEditText.getText().toString().trim();

        // Usar RetrofitClient con baseUrl
        AuthModels.SignupRequest signupRequest = new AuthModels.SignupRequest(email, password, username);
        LoginApi api = RetrofitClient.getInstance(BASE_URL).create(LoginApi.class);
        Call<AuthModels.SignupResponse> call = api.register(signupRequest);

        call.enqueue(new Callback<AuthModels.SignupResponse>() {
            @Override
            public void onResponse(Call<AuthModels.SignupResponse> call, Response<AuthModels.SignupResponse> response) {
                if (response.isSuccessful()) {
                    //  si el registro es 200 (exitoso), redirigir a la pantalla principal
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // si da  error de registro
                    nameLayout.setError("Error en el registro");
                }
            }

            @Override
            public void onFailure(Call<AuthModels.SignupResponse> call, Throwable t) {
                // Manejar fallo en la conexión
                nameLayout.setError("Error de conexión");
            }
        });
    }
}
