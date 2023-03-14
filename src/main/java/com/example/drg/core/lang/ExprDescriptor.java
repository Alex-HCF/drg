package com.example.drg.core.lang;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class ExprDescriptor {
    String varName;
    FuncDescriptor funcDescriptor;
}