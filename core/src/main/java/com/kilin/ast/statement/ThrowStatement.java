package com.kilin.ast.statement;

import com.kilin.ast.Node;
import com.kilin.ast.Stream;
import com.kilin.ast.expression.Expression;

import java.util.Objects;
import java.util.stream.Collectors;

import static com.kilin.ast.lexer.TokenType.THROW;

public class ThrowStatement extends Statement {

    public ThrowStatement(Node prarent){
        super(prarent);
    }

    public static void parser(Node node) {
        if (node instanceof ThrowStatement) return;
        Stream.of(node.getChildrens()).reduce((list, a, b) -> {
            if (a.equals(THROW)) {
                ThrowStatement statement = new ThrowStatement(node);
                statement.getChildrens().addAll(node.getChildrens());
                statement.getChildrens().remove(a);
                node.getPrarent().replace(node, statement);
            }
        });
    }

    public String toString() {
        return "throw ".concat( getChildrens().stream().map(Objects::toString).collect(Collectors.joining(";")));
    }
}