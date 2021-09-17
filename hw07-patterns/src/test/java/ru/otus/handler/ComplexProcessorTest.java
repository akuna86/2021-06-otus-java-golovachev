package ru.otus.handler;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.listener.Listener;
import ru.otus.model.Message;
import ru.otus.processor.ProcessorEvenSecondThrow;
import ru.otus.processor.ProcessorSwapFields11And12;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

class ComplexProcessorTest {

    @Test
    @DisplayName("Тестируем вызовы процессоров")
    void handleProcessorsTest() {
        //given
        var message = new Message.Builder(1L)
                .field11("field11")
                .field12("field12")
                .build();

        var processor1 = new ProcessorSwapFields11And12();
        var processor1Spy = Mockito.spy(processor1);

        var processor2 = new ProcessorEvenSecondThrow(LocalDateTime::now);
        var processor2Spy = Mockito.spy(processor2);

        var processors = List.of(processor1Spy, processor2Spy);

        var complexProcessor = new ComplexProcessor(processors, (ex) -> {
        });

        //when
        var result = complexProcessor.handle(message);

        //then
        verify(processor1Spy).process(message);
        verify(processor2Spy).process(message);
        assertThat(result.getField11()).isEqualTo(message.getField12());
        assertThat(result.getField12()).isEqualTo(message.getField11());
    }

    @Test
    @DisplayName("Тестируем обработку исключения")
    void handleExceptionTest() {
        //given
        var message = new Message.Builder(1L).field8("field8").build();

        var processor1 = new ProcessorSwapFields11And12();
        var processor1Spy = Mockito.spy(processor1);

        var processor2 = new ProcessorEvenSecondThrow(LocalDateTime::now);
        var processor2Spy = Mockito.spy(processor2);
        doReturn(true).when(processor2Spy).isSecondEven();

        var processors = List.of(processor2Spy, processor1Spy);//!! first with exception

        var complexProcessor = new ComplexProcessor(processors, (ex) -> {
            throw new TestException(ex.getMessage());
        });

        //when
        assertThatExceptionOfType(TestException.class).isThrownBy(() -> complexProcessor.handle(message));

        //then
        verify(processor2Spy, times(1)).process(message);
        verify(processor1Spy, never()).process(message);
    }

    @Test
    @DisplayName("Тестируем уведомления")
    void notifyTest() {
        //given
        var message = new Message.Builder(1L).field9("field9").build();

        var listener = mock(Listener.class);

        var complexProcessor = new ComplexProcessor(new ArrayList<>(), (ex) -> {
        });

        complexProcessor.addListener(listener);

        //when
        complexProcessor.handle(message);
        complexProcessor.removeListener(listener);
        complexProcessor.handle(message);

        //then
        verify(listener, times(1)).onUpdated(message);
    }

    private static class TestException extends RuntimeException {
        public TestException(String message) {
            super(message);
        }
    }
}