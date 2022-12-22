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
        Stream.of(node.getChildrens()).reduce((list, m, n) -> {
            if (m.equals(TokenType.RETURN)) {
                node.getChildrens().remove(m);
                //create SynchronizedNode and set Prarent，Parameters
                ReturnStatement statement = new ReturnStatement(node, (Expression) node);
                //replace this node with SynchronizedNode
                node.getPrarent().replace(node, statement);
            }
        });
    }
}