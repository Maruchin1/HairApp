package com.example.core.domain;

public class Singleton {

    private Singleton() {

    }

    private static Singleton instance;

    static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
