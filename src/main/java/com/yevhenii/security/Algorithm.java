package com.yevhenii.security;

public enum Algorithm {
    SHA256("SHA-256"),
    SHA512("SHA-512");

    private final String name;

    Algorithm(String name) {
        this.name = name;
    }

    public String getAlgorithmName() {
        return name;
    }
}
