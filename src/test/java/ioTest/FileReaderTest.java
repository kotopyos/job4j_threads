package ioTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static java.nio.charset.StandardCharsets.*;
import static org.junit.Assert.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import ru.job4j.io.FileReader;


public class FileReaderTest {
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void testGetContentFull() throws IOException {
        File file = tempFolder.newFile("test.txt");
        Files.write(file.toPath(), "Hello, job4ru! привет".getBytes(UTF_8));

        FileReader reader = new FileReader(file, UTF_8);
        String result = reader.getContent();
        
        assertEquals("Hello, job4ru! привет", result);
    }

    @Test
    public void testGetContentWithoutUnicode() throws IOException {
        File file = tempFolder.newFile("test.txt");
        Files.write(file.toPath(), "A→B↑job4j".getBytes(UTF_8));
        
        FileReader reader = new FileReader(file, UTF_8);
        String result = reader.getContentWithoutUnicode();
        
        assertEquals("ABjob4j", result);
    }

    @Test
    public void testCustomFilter() throws IOException {
        File file = tempFolder.newFile("test.txt");
        Files.write(file.toPath(), "a1b2c3".getBytes());
        
        FileReader reader = new FileReader(file, UTF_8);
        String result = reader.getContent(Character::isLetter);
        
        assertEquals("abc", result);
    }
}

