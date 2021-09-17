package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HistoryListener implements Listener, HistoryReader {

    private final Map<Long, Message> hist;

    public HistoryListener() {
        this.hist = new HashMap<>();
    }

    @Override
    public void onUpdated(Message msg) {
        Message msgCopy = msg.clone();
        try {
            hist.put(msgCopy.getId(), msgCopy);
        } catch (Exception ex) {
            throw new UnsupportedOperationException(ex.getCause());
        }
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        Message msgCopy = null;
        try {
            var msg = hist.get(id).clone();
            if (msg != null) {
                msgCopy = msg.clone();
            }
        } catch (Exception ex) {
            throw new UnsupportedOperationException(ex.getCause());
        }
        return Optional.ofNullable(msgCopy);
    }
}
