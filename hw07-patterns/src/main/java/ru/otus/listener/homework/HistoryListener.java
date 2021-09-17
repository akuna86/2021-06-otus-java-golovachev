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
        hist.put(msgCopy.getId(), msgCopy);
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        var msg = hist.get(id);

        return Optional.ofNullable(msg).map(Message::clone);
    }
}
