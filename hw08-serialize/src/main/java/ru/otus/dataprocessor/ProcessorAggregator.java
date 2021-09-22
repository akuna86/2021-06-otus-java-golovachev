package ru.otus.dataprocessor;

import ru.otus.model.Measurement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class ProcessorAggregator implements Processor {

    @Override
    public Map<String, Double> process(List<Measurement> data) {
        //группирует выходящий список по name, при этом суммирует поля value
        Map<String, Double> result;

        result = data.stream()
                .collect(Collectors.groupingBy(
                                Measurement::getName, //группируем по имени
                                TreeMap::new, //гарантируем сортировку по ключу
                                Collectors.summingDouble(Measurement::getValue) //суммируем значения для группы
                        )
                );
        return result;
    }
}
