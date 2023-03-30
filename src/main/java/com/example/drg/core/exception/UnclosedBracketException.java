package com.example.drg.core.exception;

import lombok.Getter;

@Getter
public class UnclosedBracketException extends ParserException {

    private int position;

    public UnclosedBracketException(int position) {
        super(String.format("Unclosed bracket at position %s", position));
        this.position = position;
    }
}
