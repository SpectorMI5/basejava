package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface IOStrategy {
    Resume doRead(InputStream is) throws IOException;

    void doWrite(OutputStream os, Resume resume) throws IOException;
}