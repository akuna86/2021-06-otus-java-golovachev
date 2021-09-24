package ru.otus.dataprocessor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileLoaderTest {

    @Test
    void load() {
        var fl = new FileLoader("inputData.json");
        var result = fl.load();

        Assertions.assertEquals(9, result.size());
    }
}