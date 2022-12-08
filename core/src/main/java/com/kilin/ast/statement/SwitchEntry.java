package com.kilin.ast.statement;

import com.kilin.ast.Node;
import com.kilin.ast.Stream;
import com.kilin.ast.lexer.TokenType;

import java.util.Objects;

public class SwitchEntry extends Statement {

    private static SwitchEntry statement;

    public static void parser(Node node) {
        statement = null;
        Stream.of(node.getChildrens()).reduce((list, a, b) -> {
            Stream.of(a.getChildrens()).reduce((c, m, n) -> {
                if (m.equals(TokenType.CASE)) {
                    //create ForNode and set Prarent，Parameters
                    statement = new SwitchEntry();
                    statement.setPrarent(node);
                    node.replace(a, statement);

                    //remove ForNode and Parameters
                    a.getChildrens().remove(m);
                } else if (m.equals(TokenType.DEFAULT)) {
                    //create ForNode and set Prarent，Parameters
                    statement = new SwitchEntry();
                    statement.setPrarent(node);
                    node.replace(a, statement);

                    //remove ForNode and Parameters
                    a.getChildrens().remove(m);
                } else if (Objects.nonNull(statement)) {
                    statement.getChildrens().add(m);
                    a.getChildrens().remove(m);
                    if (a.getChildrens().isEmpty()) node.getChildrens().remove(a);
                }
            });
        });
    }

}