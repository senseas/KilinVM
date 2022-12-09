package com.kilin.ast.statement;

import com.kilin.ast.Node;
import com.kilin.ast.Stream;
import com.kilin.ast.expression.Expression;
import com.kilin.ast.expression.ParametersExpression;
import com.kilin.ast.lexer.TokenType;

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
        Stream.of(node.getChildrens()).reduce((list, m, n, o) -> {
            if (m.equals(TokenType.SWITCH) && n instanceof ParametersExpression) {
                //create SwitchNode and set Prarent，Parameters
                SwitchStatement statement = new SwitchStatement(node, (Expression) n, (BlockStatement) o);
                SwitchEntry.parser(o);
                //remove SwitchNode and Parameters
                node.getPrarent().replace(node, statement);
                list.clear();
            }
        });
    }

}