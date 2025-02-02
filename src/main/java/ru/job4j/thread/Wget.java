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

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        var startAt = System.currentTimeMillis();
        var file = new File("tmp.xml");
        try (var input = new URL(url).openStream(); var output = new FileOutputStream(file)) {
            System.out.println("Open connection: " + (System.currentTimeMillis() - startAt) + " ms");
            var dataBuffer = new byte[512];
            int bytesRead;
            while ((bytesRead = input.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                var downloadAt = System.nanoTime();
                output.write(dataBuffer, 0, bytesRead);
                var timeSpentOnPackage = System.nanoTime() - downloadAt;
                System.out.println("Read 512 bytes : " + (timeSpentOnPackage) + " nano.");
                if (speed < 6000) {
                    long pause = bytesRead * 1000000L / timeSpentOnPackage / speed;
                    System.out.println("Pause for " + pause + " milliseconds");
                    Thread.sleep(pause);
                }
            }
            System.out.println(Files.size(file.toPath()) + " bytes");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IOException  e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        UrlValidator validator = new UrlValidator();
        validator.isValid(url);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }

}