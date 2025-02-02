package ru.job4j.threads.threadlocal;

public class SecondThread extends Thread {
    @Override
    public void run() {
        ThreadLocalDemo.threadLocal.set("Поток 2");
        System.out.println(ThreadLocalDemo.threadLocal.get());
    }
}
