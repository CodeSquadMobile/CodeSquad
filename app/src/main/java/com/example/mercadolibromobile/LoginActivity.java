package com.example.mercadolibromobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mercadolibromobile.api.LoginApi;
import com.example.mercadolibromobile.api.RetrofitClient;
import com.example.mercadolibromobile.models.AuthModels;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout usernameLayout, passwordLayout, nameLayout;
    private TextInputEditText usernameEditText, passwordEditText, nameEditText;
    private Button loginButton, toggleModeButton, poliButton;
    private ProgressBar progressBar;
    private boolean isLoginMode = true;
    private final String BASE_URL = "https://backend-mercado-libro-mobile.onrender.com/api/";
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);

        usernameLayout = findViewById(R.id.textInputLayout2);
        passwordLayout = findViewById(R.id.textInputLayout);
        nameLayout = findViewById(R.id.textInputLayoutName);
        usernameEditText = findViewById(R.id.textInputEditTextUsername);
        passwordEditText = findViewById(R.id.textInputEditTextPassword);
        nameEditText = findViewById(R.id.textInputEditTextName);
        loginButton = findViewById(R.id.buttonMainAction);
        toggleModeButton = findViewById(R.id.buttonToggleMode);
        poliButton = findViewById(R.id.buttonpoli);
        progressBar = findViewById(R.id.progressBar);

        final Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        final Animation fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);

        toggleModeButton.setOnClickListener(v -> {
            toggleLoginMode(fadeIn, fadeOut);
        });

        loginButton.setOnClickListener(v -> {
            if (isLoginMode) {
                loginUser();
            } else {
                registerUser();
            }
        });

        poliButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, Politicas.class);
            startActivity(intent);
        });

        usernameEditText.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateButtonState();
            }
        });

        passwordEditText.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateButtonState();
            }
        });

        nameEditText.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateButtonState();
            }
        });
    }

    private void toggleLoginMode(Animation fadeIn, Animation fadeOut) {
        isLoginMode = !isLoginMode;
        if (isLoginMode) {
            loginButton.setText(R.string.ingresar);
            toggleModeButton.setText(R.string.registrarse);
            nameLayout.setVisibility(View.GONE);
            findViewById(R.id.textViewName).setVisibility(View.GONE);
        } else {
            loginButton.setText(R.string.registrarse);
            toggleModeButton.setText(R.string.volver);
            nameLayout.setVisibility(View.VISIBLE);
            findViewById(R.id.textViewName).setVisibility(View.VISIBLE);
            findViewById(R.id.textViewName).startAnimation(fadeIn);
        }
        nameLayout.setError(null);
        usernameLayout.setError(null);
        passwordLayout.setError(null);
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
        String email = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        progressBar.setVisibility(View.VISIBLE);

        LoginApi api = RetrofitClient.getInstance(BASE_URL).create(LoginApi.class);
        Call<AuthModels.LoginResponse> call = api.login(email, password);

        call.enqueue(new Callback<AuthModels.LoginResponse>() {
            @Override
            public void onResponse(Call<AuthModels.LoginResponse> call, Response<AuthModels.LoginResponse> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    AuthModels.LoginResponse loginResponse = response.body();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("access_token", loginResponse.getToken());
                    editor.putString("user_id", String.valueOf(loginResponse.getUserId()));
                    editor.apply();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    usernameLayout.setError("Credenciales incorrectas");
                }
            }

            @Override
            public void onFailure(Call<AuthModels.LoginResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void registerUser() {
        String email = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String username = nameEditText.getText().toString().trim();

        AuthModels.SignupRequest signupRequest = new AuthModels.SignupRequest(email, password, username);
        LoginApi api = RetrofitClient.getInstance(BASE_URL).create(LoginApi.class);
        Call<AuthModels.SignupResponse> call = api.register(signupRequest);
        call.enqueue(new Callback<AuthModels.SignupResponse>() {
            @Override
            public void onResponse(Call<AuthModels.SignupResponse> call, Response<AuthModels.SignupResponse> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Registro exitoso. Por favor, inicie sesión.", Toast.LENGTH_SHORT).show();
                    toggleLoginMode(null, null); // Cambiar a modo de inicio de sesión
                } else {
                    usernameLayout.setError("Error al registrarse. Intenta nuevamente.");
                }
            }

            @Override
            public void onFailure(Call<AuthModels.SignupResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private abstract class SimpleTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {}
    }
}
