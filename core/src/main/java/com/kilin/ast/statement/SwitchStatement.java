package com.kilin.ast.statement;

import com.kilin.ast.lexer.TokenType;
import com.kilin.ast.Node;
import com.kilin.ast.Stream;
import com.kilin.ast.expression.Expression;
import com.kilin.ast.expression.ParametersExpression;

public class SwitchStatement extends Statement {
    private Expression expression;
    private BlockStatement body;

    public SwitchStatement(Node prarent, Expression expression, BlockStatement body) {
        super(prarent);
        this.expression = expression;
        this.body = body;

        this.expression.setPrarent(this);
        this.body.setPrarent(this);

        getChildrens().addAll(expression, body);
    }

    public static void parser(Node node) {
        Stream.of(node.getChildrens()).reduce((list, a, b) -> {
            Stream.of(a.getChildrens()).reduce((c, m, n) -> {
                if (m.equals(TokenType.SWITCH) && n instanceof ParametersExpression) {
                    //create SwitchNode and set Prarent，Parameters
                    SwitchStatement statement = new SwitchStatement(node, (Expression) n, (BlockStatement) b);
                    SwitchEntry.parser(b);
                    //remove SwitchNode and Parameters
                    node.replace(a, statement);
                    node.getChildrens().remove(b);
                    list.remove(b);
                }
            });
        });
    }

}