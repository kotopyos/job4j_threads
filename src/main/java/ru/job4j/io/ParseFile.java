package ru.job4j.io;

import java.io.*;
/**
 <li>Избавиться от get set за счет передачи File в конструктор.</li>
 <li>Ошибки в многопоточности. Сделать класс Immutable. Все поля final.</li>
 <li>Ошибки в IO. Не закрытые ресурсы. Чтение и запись файла без буфера.</li>
 <li>Нарушен принцип единой ответственности. Тут нужно сделать два класса.</li>
 <li>Методы getContent написаны в стиле копипаста. Нужно применить шаблон стратегия. content(Predicate<Character> filter)</li>
 */

public class ParseFile {
    private File file;

    public synchronized void setFile(File file) {
        this.file = file;
    }

    public synchronized File getFile() {
        return file;
    }

    public String getContent() throws IOException {
        InputStream input = new FileInputStream(file);
        String output = "";
        int data;
        while ((data = input.read()) > 0) {
            output += (char) data;
        }
        return output;
    }

    public String getContentWithoutUnicode() throws IOException {
        InputStream input = new FileInputStream(file);
        String output = "";
        int data;
        while ((data = input.read()) > 0) {
            if (data < 0x80) {
                output += (char) data;
            }
        }
        return output;
    }

    public void saveContent(String content) throws IOException {
        OutputStream o = new FileOutputStream(file);
        for (int i = 0; i < content.length(); i++) {
            o.write(content.charAt(i));
        }
    }
}