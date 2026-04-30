package by.exam.prep.pattern.observer;

import by.exam.prep.entity.TestAttempt;

public record TestCompletionEvent(TestAttempt attempt) {}
