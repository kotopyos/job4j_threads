//package ru.job4j.ioTest;
//
//
//import static java.nio.charset.StandardCharsets.*;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//
//import org.junit.jupiter.api.Test;
//import ru.job4j.io.FileWriter;
//
//public class FileWriterTest {
//    @Rule //TODO: убрать позже. JUnit5 убрал эту шляпу вместе @Runners https://junit.org/junit5/docs/current/user-guide/#migrating-from-junit4
//    public TemporaryFolder tempFolder = new TemporaryFolder();
//
//    @Test
//    public void testSaveContent() throws IOException {
//        File file = tempFolder.newFile("output.txt");
//        String content = "Test job4j content\nNew line";
//
//        FileWriter writer = new FileWriter(file);
//        writer.saveContent(content);
//
//        byte[] bytes = Files.readAllBytes(file.toPath());
//        assertEquals(content, new String(bytes, UTF_8));
//    }
//
//    @Test
//    public void testSaveEmptyContent() throws IOException {
//        File file = tempFolder.newFile("empty.txt");
//
//        FileWriter writer = new FileWriter(file);
//        writer.saveContent("");
//
//        assertEquals(0, file.length());
//    }
//
//    @Test
//    public void testSpecialCharacters() throws IOException {
//        File file = tempFolder.newFile("special.txt");
//        String content = "€±≠√↑↑↑↑";
//
//        FileWriter writer = new FileWriter(file);
//        writer.saveContent(content);
//
//        byte[] bytes = Files.readAllBytes(file.toPath());
//        assertEquals(content, new String(bytes, UTF_8));
//    }
//}