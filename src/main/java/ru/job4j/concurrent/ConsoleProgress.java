package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    @Override
    public void run() {
        int charIndex = 0;
        try {
            while (!Thread.currentThread().isInterrupted()) {
                char[] process = new char[] {'-', '\\', '|', '/'};
                if (charIndex == 4) {
                    charIndex = 0;
                }
                System.out.print("\rLoading ... " + process[charIndex++] + ".");
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
