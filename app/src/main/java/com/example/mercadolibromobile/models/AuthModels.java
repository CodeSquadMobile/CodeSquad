// AuthModels.java
package com.example.mercadolibromobile.models;

import com.google.gson.annotations.SerializedName;

public class AuthModels {

    // Clase para la solicitud de registro
    public static class SignupRequest {
        @SerializedName("email")
        private String email;

        @SerializedName("password")
        private String password;

        @SerializedName("username")
        private String username;

        // Constructor
        public SignupRequest(String email, String password, String username) {
            this.email = email;
            this.password = password;
            this.username = username;
        }

        // Getters y Setters
        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }

    // Clase para la respuesta de registro
    public static class SignupResponse {
        @SerializedName("message")
        private String message;

        // Constructor
        public SignupResponse(String message) {
            this.message = message;
        }

        // Getter
        public String getMessage() {
            return message;
        }
    }

    // Clase para la solicitud de inicio de sesión
    public static class LoginRequest {
        @SerializedName("email")
        private String email;

        @SerializedName("password")
        private String password;

        // Constructor
        public LoginRequest(String email, String password) {
            this.email = email;
            this.password = password;
        }

        // Getters y Setters
        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    // Clase para la respuesta de inicio de sesión
    public static class LoginResponse {
        @SerializedName("user_id")
        private int userId;

        @SerializedName("token")
        private String token;

        public LoginResponse(int userId, String token) {
            this.userId = userId;
            this.token = token;
        }

        public int getUserId() {
            return userId;
        }

        public String getToken() {
            return token;
        }
    }

}
