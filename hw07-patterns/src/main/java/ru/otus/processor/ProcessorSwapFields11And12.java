package ru.otus.processor;

import ru.otus.model.Message;

public class ProcessorSwapFields11And12 implements Processor {
    @Override
    public Message process(Message message) {
        var msg = message.toBuilder()
                .field12(message.getField11())
                .field11(message.getField12())
                .build();
        return msg;
    }
}
