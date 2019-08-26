package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.serializer.SerializationStrategy;

public class ObjectFileStorageTest extends AbstractStorageTest {

    public ObjectFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new SerializationStrategy()));
    }
}