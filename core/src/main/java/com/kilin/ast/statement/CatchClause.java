package com.kilin.ast.statement;

import com.kilin.ast.lexer.TokenType;
import com.kilin.ast.Node;
import com.kilin.ast.Stream;
import com.kilin.ast.expression.Expression;
import com.kilin.ast.expression.ParametersExpression;

public class CatchClause extends Statement {
    private Expression parameter;
    private Statement body;

    public CatchClause(Node prarent, Expression parameter, Statement body) {
        super(prarent);

        this.parameter = parameter;
        this.body = body;

        this.parameter.setPrarent(this);
        this.body.setPrarent(this);

        getChildrens().addAll(parameter, body);
    }

    public static void parser(Node node) {
        Stream.of(node.getChildrens()).reduce((list, a, b) -> {
            Stream.of(a.getChildrens()).reduce((c, m, n) -> {
                if (m.equals(TokenType.CATCH) && n instanceof ParametersExpression) {
                    //create CatchClause and set Prarent，Parameters
                    CatchClause statement = new CatchClause(node, (Expression) n, (BlockStatement) b);
                    //remove CatchNode and Parameters
                    node.replaceAndRemove(a, statement, b);
                    list.remove(b);
                }
            });
        });
    }
}