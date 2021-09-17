package ru.otus.model;

import java.util.List;
import java.util.stream.Collectors;

public class ObjectForMessage implements Cloneable {
    private List<String> data;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    @Override
    public ObjectForMessage clone() {
        ObjectForMessage clone = new ObjectForMessage();
        if (this.data != null) {
            clone.setData(this.data.stream().collect(Collectors.toList()));
        }
        return clone;
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
