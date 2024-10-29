package com.example.mercadolibromobile.models;


public class User {
    private String refresh;
    private String access;
    private UserInfo user; // Nueva clase para los datos del usuario

    public User() {
    }

    public User(String refresh, String access, UserInfo user) {
        this.refresh = refresh;
        this.access = access;
        this.user = user;
    }

    public String getRefresh() {
        return refresh;
    }

    public String getAccess() {
        return access;
    }

    public UserInfo getUser() {
        return user;
    }

    public static class UserInfo {
        private String email;
        private String username;

        public UserInfo() {
        }

        public String getEmail() {
            return email;
        }

        public String getUsername() {
            return username;
        }
    }
}