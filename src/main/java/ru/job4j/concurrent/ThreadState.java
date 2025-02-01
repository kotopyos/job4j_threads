package ru.job4j.concurrent;

import static java.lang.System.out;
import static java.lang.Thread.*;

public class ThreadState {
    private static final String PROGRAM_FINISHED_TASKS_MESSAGE = "Done.";

    public static void main(String[] args) {
        Runnable task  = () -> out.println(currentThread().getState() + " " + currentThread().getName());

        Thread first = new Thread(task);
        Thread second = new Thread(task);
        soutStateAndNameOfThread(first);
        soutStateAndNameOfThread(second);

        first.start();
        second.start();

        while (first.getState() != Thread.State.TERMINATED &&
               second.getState() != Thread.State.TERMINATED) {
            soutStateAndNameOfThread(first);
            soutStateAndNameOfThread(second);

        }
        soutStateAndNameOfThread(first);
        soutStateAndNameOfThread(second);
        out.println(PROGRAM_FINISHED_TASKS_MESSAGE);

    }
    private static void soutStateAndNameOfThread(Thread thread){
        out.println(thread.getState() + " " + thread.getName());
    }
}
