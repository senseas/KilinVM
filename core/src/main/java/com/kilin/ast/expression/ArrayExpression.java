package com.kilin.ast.expression;

import com.kilin.ast.Node;

import java.util.Objects;
import java.util.stream.Collectors;

public class ArrayExpression extends Expression {
    public ArrayExpression(Node prarent) {
        super(prarent);
    }

    @Override
    public String toString() {
        return "[".concat(getChildrens().stream().map(Objects::toString).collect(Collectors.joining())).concat("]");
    }
}