package ru.otus.processor;

import ru.otus.model.Message;

public class ProcessorEvenSecondThrow implements Processor {

    private DateTimeProvider dateTimeProvider;

    public ProcessorEvenSecondThrow(DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }

    public boolean isSecondEven() {
        return dateTimeProvider.getTime().getSecond() % 2 == 0;
    }

    @Override
    public Message process(Message message) {
        if (isSecondEven()) {
            throw new RuntimeException("Time is even");
        }
        return message;
    }

}
