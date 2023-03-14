package com.example.drg.core.lang;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class Token {
    public enum Type {
        VAR("[a-zA-Z0-9]*"),
        EQUAL("="),
        FUNC_NAME("[a-zA-Z0-9]*"),
        OPEN_BRACKET("[(]"),
        CLOSED_BRACKET("[)]"),
        ARG("'[a-zA-Z0-9]*'"),
        COMMA(",");

        private final String regex;

        public String getRegex() {
            return regex;
        }

        Type(String regex) {
            this.regex = regex;
        }
    }

    Type type;
    String value;
}