package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class HistoryListener implements Listener, HistoryReader {

    private final Map<Long, Message> hist;

    public HistoryListener() {
        this.hist = new LinkedHashMap<>();
    }

    @Override
    public void onUpdated(Message msg) {
        Message msgCopy = msg.toBuilder()
                .field13(msg.getField13() == null ? null : msg.getField13().clone())
                .build();
        try {
            hist.put(msgCopy.getId(), msgCopy);
        } catch (Exception ex) {
            throw new UnsupportedOperationException(ex.getCause());
        }
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        Message msg;
        try {
            msg = hist.get(id).toBuilder().build();
        } catch (Exception ex) {
            throw new UnsupportedOperationException(ex.getCause());
        }
        return Optional.ofNullable(msg);
    }
}
