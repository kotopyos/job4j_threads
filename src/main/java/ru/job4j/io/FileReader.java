package ru.job4j.io;

import java.io.*;
import java.nio.charset.Charset;
import java.util.function.Predicate;

public final class FileReader {
    private final File file;
    private final Charset charset;

    public FileReader(File file, Charset charset) {
        this.file = file;
        this.charset = charset;
    }

    public String getContent(Predicate<Character> filter) throws IOException {
        StringBuilder output = new StringBuilder();
        try (BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset))) {
            int data;
            while ((data = input.read()) != -1) {
                char character = (char) data;
                if (filter.test(character)) {
                    output.append(character);
                }
            }
        }
        return output.toString();
    }

    public String getContent() throws IOException {
        return getContent(c -> true);
    }

    public String getContentWithoutUnicode() throws IOException {
        return getContent(c -> c < 0x80);
    }
}
