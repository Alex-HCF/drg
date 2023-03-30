package com.example.drg.core.exception;

import lombok.Getter;

@Getter
public class UnmatchedBracketException extends ParserException {

    private int position;

    public UnmatchedBracketException(int position) {
        super(String.format("Unmatched bracket at position %s", position));
        this.position = position;
    }
}
