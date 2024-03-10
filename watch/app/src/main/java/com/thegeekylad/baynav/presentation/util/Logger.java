package com.thegeekylad.baynav.presentation.util;

public class Logger {
    private final String category;

    public Logger() {
        this.category = null;
    }
    public Logger(String category) {
        this.category = category;
    }

    public void print(String message, int indents) {
        for (int i = 0; i < indents; i++)
            System.out.print("----");
        System.out.print(" ");
        if (category != null)
            System.out.printf("[%s] ", category);
        System.out.print(message);
    }

    public void println(String message, int indents) {
        print(message + "\n", indents);
    }

    public void printHeader(String message, int indents) {
        println("\n", 0);
        println(message, indents);
    }

    public void printBlock(String message, int indents) {
        println("\n", 0);
        println(message, indents);
        println("", 0);
    }
}
