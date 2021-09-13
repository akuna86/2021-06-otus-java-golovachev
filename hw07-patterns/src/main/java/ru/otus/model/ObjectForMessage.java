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
        try {
            ObjectForMessage clone = (ObjectForMessage) super.clone();
            if(data != null) {
            clone.setData(this.data.stream().collect(Collectors.toList()));
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public String toString(){
        return data.toString();
    }
}
