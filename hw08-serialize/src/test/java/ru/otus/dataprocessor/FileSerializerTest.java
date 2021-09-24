package ru.otus.dataprocessor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FileSerializerTest {

    @Test
    void serialize() {
        String name = "outputData.json";
        var ser = new FileSerializer(name);
        Map<String, Double> map = Map.of("t1", 1d, "t2", 3d);
        ser.serialize(map);

        File file = Paths.get(name).toFile();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode node = objectMapper.readTree(file);
            Assertions.assertEquals(1d, node.get("t1").doubleValue());
            Assertions.assertEquals(3d, node.get("t2").doubleValue());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}