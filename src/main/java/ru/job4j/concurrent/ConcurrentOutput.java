package ru.job4j.concurrent;

import static java.lang.Thread.currentThread;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class ConcurrentOutput {
    public static void main(String[] args) {
        Thread second = new Thread(() -> System.out.println(currentThread().getName()));
        second.start();
        System.out.println(currentThread().getName());
    }
}