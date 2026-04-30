package by.exam.prep.pattern.observer;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import by.exam.prep.entity.TestAttempt;

@Component
@RequiredArgsConstructor
public class TestEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public void publishTestCompletion(TestAttempt attempt) {
        eventPublisher.publishEvent(new TestCompletionEvent(attempt));
    }
}
