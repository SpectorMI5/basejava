package ru.javawebinar.basejava.storage;

import org.junit.Test;

public class MapStorageTest extends AbstractArrayStorageTest {

    public MapStorageTest() {
        super(new MapStorage());
    }

    @Test
    public void saveOverflow() {
    }
}
