package com.kilin.ast.statement;

import com.kilin.ast.Node;
import com.kilin.ast.Stream;
import com.kilin.ast.expression.Expression;
import com.kilin.ast.lexer.TokenType;

public class DoWhileStatement extends Statement {
    private Expression condition;
    private Statement body;

    public DoWhileStatement(Node prarent, Expression condition, Statement body) {
        super(prarent);
        this.condition = condition;
        this.body = body;

        this.condition.setPrarent(this);
        this.body.setPrarent(this);

        getChildrens().addAll(condition, body);
    }

    public static void parser(Node node) {
        if (node instanceof ForStatement) return;
        Stream.of(node.getChildrens()).reduce((e, m, n) -> {
            if (m.equals(TokenType.DO) && n instanceof BlockStatement) {
                Node next = node.next();
                Node condition = next.getChildrens().get(1);
                //create ForNode and set Prarent , Parameters
                DoWhileStatement statement = new DoWhileStatement(node, (Expression) condition, (Statement) n);
                //remove ForNode and Parameters
                node.getPrarent().replace(node, statement);
                node.getPrarent().getChildrens().remove(next);
            }
        });
    }

    @Override
    public String toString() {
        return "do ".concat("{ ").concat(body.toString()).concat(" }").concat(" while").concat(condition.toString());
    }
}