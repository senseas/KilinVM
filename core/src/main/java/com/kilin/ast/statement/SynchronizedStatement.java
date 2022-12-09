package com.kilin.ast.statement;

import com.kilin.ast.Node;
import com.kilin.ast.Stream;
import com.kilin.ast.expression.Expression;
import com.kilin.ast.expression.ParametersExpression;
import com.kilin.ast.lexer.TokenType;

import java.util.List;

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
        Stream.of(node.getChildrens()).reduce((list, m, n, o) -> {
            if (m.equals(TokenType.SYNCHRONIZED) && n instanceof ParametersExpression) {
                //create SynchronizedNode and set Prarent，Parameters
                SynchronizedStatement statement = new SynchronizedStatement(node, (Expression) n, (BlockStatement) o);
                //replace this node with SynchronizedNode
                node.getPrarent().replace(node, statement);
                list.removeAll(List.of(m, n, o));
                list.clear();
            }
        });
    }

    @Override
    public String toString() {
        return expression.toString().concat("{ ").concat(body.toString()).concat(" }");
    }
}