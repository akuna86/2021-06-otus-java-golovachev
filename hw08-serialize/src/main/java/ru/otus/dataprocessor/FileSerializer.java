package ru.otus.dataprocessor;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.Map;

public class FileSerializer implements Serializer {

    String fileName;

    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) {
        //формирует результирующий json и сохраняет его в файл
        try {
            var file = new File(fileName);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(file, data);

        } catch (Exception ex) {
            throw new FileProcessException(ex);
        }
    }
}
