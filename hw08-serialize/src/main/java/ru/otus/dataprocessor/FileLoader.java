package ru.otus.dataprocessor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import ru.otus.model.Measurement;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileLoader implements Loader {

    private final String fileName;

    public FileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() {
        //читает файл, парсит и возвращает результат
        List<Measurement> list;
        try{
            var fileUrl = ClassLoader.getSystemResource(fileName);

            ObjectMapper objectMapper = new ObjectMapper();
            SimpleModule simpleModule = new SimpleModule();
            simpleModule.addDeserializer(Measurement.class, new MyDeserializer(Measurement.class));
            objectMapper.registerModule(simpleModule);
            list = objectMapper.readValue(fileUrl, new TypeReference<>() {
            });
        } catch (Exception ex){
            throw new FileProcessException(ex);
        }
        return list;
    }
}
