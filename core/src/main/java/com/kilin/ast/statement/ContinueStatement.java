package com.kilin.ast.statement;

import com.kilin.ast.lexer.TokenType;
import com.kilin.ast.Node;
import com.kilin.ast.Stream;

public class ContinueStatement extends Statement {

    public ContinueStatement(Node prarent) {
        super(prarent);
    }

    public static void parser(Node node) {
        Stream.of(node.getChildrens()).reduce2((list, a, b) -> {
            Stream.of(a.getChildrens()).reduce2((c, m, n) -> {
                if (m.equals(TokenType.CONTINUE)) {
                    //create SynchronizedNode and set Prarent，Parameters
                    ContinueStatement statement = new ContinueStatement(node);
                    //replace this node with SynchronizedNode
                    c.replace(m, statement);
                }
            });
        });
    }
}