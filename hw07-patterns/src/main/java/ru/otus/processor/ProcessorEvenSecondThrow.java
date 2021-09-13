package ru.otus.processor;

import ru.otus.model.Message;

import java.time.LocalDateTime;

public class ProcessorEvenSecondThrow implements Processor{

    public boolean isSecondEven(LocalDateTime time){
        return time.getSecond() % 2 == 0;
    }

    @Override
    public Message process(Message message){
        if ( isSecondEven(LocalDateTime.now()) ){
            throw new RuntimeException("Time is even");
        };
        return message;
    }
}
