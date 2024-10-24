package com.example.mercadolibromobile.models;

public class AuthModels {
    public static class LoginResponse {
        private String access;
        private String refresh;

        public String getAccess() {
            return access;
        }

        public String getRefresh() {
            return refresh;
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
}