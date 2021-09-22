package ru.otus.dataprocessor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.otus.model.Measurement;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProcessorAggregatorTest {

    @Test
    void process() {
        var proc = new ProcessorAggregator();
        List<Measurement> list = Arrays.asList(
                new Measurement("t1", 1d),
                new Measurement("t1", 2d),
                new Measurement("t3", 1d),
                new Measurement("t2", 5d));
        var result= proc.process(list);

        Assertions.assertAll(
                () -> assertEquals(new AbstractMap.SimpleEntry<>("t1", 3d), result.entrySet().iterator().next()),
                () -> assertEquals(3, result.size())
        );
    }
}