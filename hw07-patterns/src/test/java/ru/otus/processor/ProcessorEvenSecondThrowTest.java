package ru.otus.processor;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.model.Message;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

class ProcessorEvenSecondThrowTest {

    ProcessorEvenSecondThrow processorSpy;
    Message message;

    @BeforeEach
    public void init() {
        message = new Message.Builder(1L)
                .field1("Hi")
                .build();
        var processor = new ProcessorEvenSecondThrow(LocalDateTime::now);
        processorSpy = Mockito.spy(processor);
    }

    @Test
    @DisplayName("2 запуска с четной секундой = 2 ошибки. И наоборот")
    public void throwTestMockEven() {
        //always even
        doReturn(true).when(processorSpy).isSecondEven();
        for (int i = 1; i <= 2; i++) {
            Assertions.assertThatThrownBy(() -> processorSpy.process(message)).hasMessage("Time is even");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        verify(processorSpy, times(2)).process(message);

        //always odd
        doReturn(false).when(processorSpy).isSecondEven();
        for (int i = 1; i <= 2; i++) {
            Assertions.assertThat(processorSpy.process(message)).isNotNull();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        verify(processorSpy, times(4)).process(message);
    }

    @Test
    @DisplayName("функции с четной и нечетной секундой")
    public void throwTestRealTime() {
        //even
        var evenDateTime = LocalDateTime.of(2021, 9, 18, 15, 30, 10);

        var processorEven = new ProcessorEvenSecondThrow(() -> evenDateTime);
        var msg = new Message.Builder(1L).build();
        Assertions.assertThatThrownBy(() -> processorEven.process(msg)).hasMessage("Time is even");

        //odd
        var notEvenDateTime = LocalDateTime.of(2021, 9, 18, 15, 30, 11);
        var processorNotEven = new ProcessorEvenSecondThrow(() -> notEvenDateTime);
        Assertions.assertThat(processorNotEven.process(message)).isNotNull();
    }
}