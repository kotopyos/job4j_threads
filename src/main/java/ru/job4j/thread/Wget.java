package ru.job4j.thread;

import org.apache.commons.validator.routines.UrlValidator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private final String fileName;

    public Wget(String url, int speed, String fileName) {
        this.url = url;
        this.speed = speed;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        var startAt = System.currentTimeMillis();
        var file = new File(fileName);
        try (var input = new URL(url).openStream(); var output = new FileOutputStream(file)) {
            System.out.println("Open connection: " + (System.currentTimeMillis() - startAt) + " ms");
            var dataBuffer = new byte[512];
            int bytesRead;

            long intervalStart = System.currentTimeMillis();
            long totalBytes = 0;

            while ((bytesRead = input.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                output.write(dataBuffer, 0, bytesRead);
                totalBytes += bytesRead;
                System.out.println("Total bytes: " + totalBytes);

                if (totalBytes >= speed) {
                    long elapsedTime = System.currentTimeMillis() - intervalStart;
                    long expectedTime = totalBytes / speed;
                    System.out.println("Elapsed: " + elapsedTime + "ms");
                    System.out.println("Expected: " + expectedTime + "ms");

                    if (elapsedTime < expectedTime) {
                        long pause = expectedTime - elapsedTime;
                        Thread.sleep(pause);
                        System.out.println("Pause: " + pause + "ms\n");
                    }
                }

                totalBytes = 0;
                intervalStart = System.currentTimeMillis();
            }
            System.out.println(Files.size(file.toPath()) + " bytes");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        } catch (IOException  e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length < 3) {
            throw new IllegalArgumentException("Missing arguments. Start the program with args <url> <speed> <name of the downloaded file>");
        }
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        if (speed <= 0) {
            throw new IllegalArgumentException("<speed> must be a positive integer.");
        }
        String fileName = args[2];
        UrlValidator validator = new UrlValidator();
        if (!validator.isValid(url)) {
            throw new RuntimeException("Invalid URL");
        }
        Thread wget = new Thread(new Wget(url, speed, fileName));
        wget.start();
        wget.join();
    }

}