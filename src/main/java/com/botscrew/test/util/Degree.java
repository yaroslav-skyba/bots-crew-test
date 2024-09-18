package com.botscrew.test.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Degree {
    ASSISTANT("assistant"), ASSOCIATE_PROFESSOR("associate professor"), PROFESSOR("professor");

    private final String value;
}
