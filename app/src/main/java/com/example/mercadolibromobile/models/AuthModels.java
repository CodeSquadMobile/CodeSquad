package com.example.mercadolibromobile.models;

public class AuthModels {
    public static class LoginResponse {
        private String access;
        private String refresh;
        private int user_id;

        public String getAccess() {
            return access;
        }

        public String getRefresh() {
            return refresh;
        }

        public int getUserId() {
            return user_id;
        }
    }

    public static class SignupRequest {
        private String email;
        private String password;
        private String username;

        public SignupRequest(String email, String password, String username) {
            this.email = email;
            this.password = password;
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }

        public String getUsername() {
            return username;
        }
    }

    public static class SignupResponse {
        private String access;
        private String refresh;

        public String getAccess() {
            return access;
        }

        public String getRefresh() {
            return refresh;
        }
    }

    public static class LoginRequest {  // Cambiado a 'static' para mantener la coherencia
        private String email;
        private String password;

        public LoginRequest(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public String getEmail() {  // Agregado método getter para email
            return email;
        }

        public String getPassword() {  // Agregado método getter para password
            return password;
        }
    }
}
