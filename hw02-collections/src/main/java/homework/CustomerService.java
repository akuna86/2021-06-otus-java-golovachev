package homework;

import java.util.AbstractMap;
import java.util.Map;
import java.util.TreeMap;

public class CustomerService {

    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны
    private final TreeMap<Customer, String> custMap = new TreeMap<>();

    public Map.Entry<Customer, String> getSmallest() {
        //Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk
//        return null; // это "заглушка, чтобы скомилировать"
        var smallestEntry = custMap.firstEntry();
        if (smallestEntry == null) return null;
        return new AbstractMap.SimpleEntry<>(smallestEntry.getKey().clone(), smallestEntry.getValue());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
//        return null; // это "заглушка, чтобы скомилировать"
        var nextEntry = custMap.higherEntry(customer);
        if (nextEntry == null) return null;
        return new AbstractMap.SimpleEntry<>(nextEntry.getKey().clone(), nextEntry.getValue());
    }

    public void add(Customer customer, String data) {
        custMap.put(customer, data);
    }
}
