package com.example.drg.core.lang;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor
@ToString
public class FuncDescriptor {
    String funcName;
    List<?> args;
}