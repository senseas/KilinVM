package com.kilin.ast.statement;

import com.kilin.ast.Node;
import com.kilin.ast.Stream;
import com.kilin.ast.expression.Expression;
import com.kilin.ast.lexer.TokenType;

public class SynchronizedStatement extends Statement {
    private Expression expression;
    private BlockStatement body;

    public SynchronizedStatement(Node prarent, Expression expression, BlockStatement body) {
        super(prarent);
        this.expression = expression;
        this.body = body;

        this.expression.setPrarent(this);
        this.body.setPrarent(this);

        getChildrens().addAll(expression, body);
    }

    public static void parser(Node node) {
        if (node instanceof SynchronizedStatement) return;
        Stream.of(node.getChildrens()).reduce((list, a, b, c) -> {
            if (a.equals(TokenType.SYNCHRONIZED)) {
                //create SynchronizedNode and set Prarent，Parameters
                SynchronizedStatement statement = new SynchronizedStatement(node, (Expression) b, (BlockStatement) c);
                //replace this node with SynchronizedNode
                node.getPrarent().replace(node, statement);
                list.clear();
            }
        });
    }

    @Override
    public String toString() {
        return "synchronized ".concat(expression.toString()).concat("{ ").concat(body.toString()).concat(" }");
    }
}