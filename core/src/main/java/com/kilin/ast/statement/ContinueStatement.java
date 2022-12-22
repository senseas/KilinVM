package com.kilin.ast.statement;

import com.kilin.ast.Node;
import com.kilin.ast.Stream;
import com.kilin.ast.lexer.TokenType;

public class ContinueStatement extends Statement {

    public ContinueStatement(Node prarent) {
        super(prarent);
    }

    public static void parser(Node node) {
        Stream.of(node.getChildrens()).reduce((list, a, b) -> {
            if (a.equals(TokenType.CONTINUE)) {
                node.getChildrens().remove(a);
                //create SynchronizedNode and set Prarent，Parameters
                ContinueStatement statement = new ContinueStatement(node);
                //replace this node with SynchronizedNode
                node.getPrarent().replace(node, statement);
            }
        });
    }
}