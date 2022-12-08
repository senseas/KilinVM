package com.kilin.ast.statement;

import com.kilin.ast.Node;
import com.kilin.ast.Stream;
import com.kilin.ast.expression.Expression;
import com.kilin.ast.expression.ParametersExpression;
import com.kilin.ast.lexer.TokenType;

public class SynchronizedStatement extends Statement {
    private BlockStatement body;
    private Expression expression;

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
        Stream.of(node.getChildrens()).reduce2((list, a, b) -> {
            Stream.of(a.getChildrens()).reduce2((c, m, n) -> {
                if (m.equals(TokenType.SYNCHRONIZED) && n instanceof ParametersExpression) {
                    //create SynchronizedNode and set Prarent，Parameters
                    SynchronizedStatement statement = new SynchronizedStatement(node, (Expression) n, (BlockStatement) b);
                    //replace this node with SynchronizedNode
                    list.replace(a, statement);
                    list.remove(b);
                }
            });
        });
    }

    @Override
    public String toString() {
        return expression.toString().concat("{ ").concat(body.toString()).concat(" }");
    }
}