package ru.job4j.threads.threadlocal;

public class FirstThread extends Thread {
    @Override
    public void run() {
        ThreadLocalDemo.threadLocal.set("Поток 1");
        System.out.println(ThreadLocalDemo.threadLocal.get());
    }
}
