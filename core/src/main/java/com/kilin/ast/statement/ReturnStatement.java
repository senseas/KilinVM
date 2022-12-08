package com.kilin.ast.statement;

import com.kilin.ast.Node;
import com.kilin.ast.Stream;
import com.kilin.ast.expression.Expression;
import com.kilin.ast.lexer.TokenType;

public class ReturnStatement extends Statement {
    private Expression expression;

    public ReturnStatement(Node prarent, Expression expression) {
        super(prarent);
        this.expression = expression;
        this.expression.setPrarent(this);
        getChildrens().addAll(expression);
    }

    public static void parser(Node node) {
        if (node instanceof SynchronizedStatement) return;
        Stream.of(node.getChildrens()).reduce2((list, a, b) -> {
            Stream.of(a.getChildrens()).reduce2((c, m, n) -> {
                if (m.equals(TokenType.RETURN)) {
                    c.remove(m);
                    //create SynchronizedNode and set Prarent，Parameters
                    ReturnStatement statement = new ReturnStatement(node, (Expression) a);
                    //replace this node with SynchronizedNode
                    list.replace(a, statement);
                }
            });
        });
    }
}