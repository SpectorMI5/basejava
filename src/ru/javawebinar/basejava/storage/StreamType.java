package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface StreamType {
    Resume doRead(InputStream is) throws IOException;

    void doWrite(OutputStream os, Resume r) throws IOException;
}