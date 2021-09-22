package ru.otus.dataprocessor;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import ru.otus.model.Measurement;

import java.io.IOException;

public class MyDeserializer extends StdDeserializer<Measurement> {

    protected MyDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Measurement deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        String name = node.get("name").asText();
        double value = node.get("value").doubleValue();
        return new Measurement(name,value);
    }
}
