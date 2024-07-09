package com.example.mongodb.model;

public enum Secret {

    SECRETKEY("5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437");

    private final String secretKey;

    Secret(String secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    public String toString() {
        return secretKey;
    }

}
