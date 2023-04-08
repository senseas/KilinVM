package com.kilin.ast.declaration;

import com.kilin.ast.Node;
import com.kilin.ast.Stream;
import com.kilin.ast.expression.Expression;
import com.kilin.ast.expression.LambdaExpression;
import com.kilin.ast.expression.ParametersExpression;

public class CallableDeclaration extends Declaration {
    private Expression expression;
    private Expression parameters;

    public CallableDeclaration(Node prarent, Expression expression, Expression parameters) {
        super(prarent);
        this.expression = expression;
        this.parameters = parameters;

        this.expression.setPrarent(this);
        this.parameters.setPrarent(this);

        getChildrens().addAll(expression, parameters);
        setParsed(true);
    }

    public static void parser(Node node) {
        LambdaExpression.parser(node);
        Stream.of(node.getChildrens()).reduce2((list, m, n) -> {
            if (m instanceof Expression && n instanceof ParametersExpression) {
                CallableDeclaration declare = new CallableDeclaration(node, (Expression) m, (Expression) n);
                list.replace(m, declare);
                list.remove(n);
            }
        });
    }

    public Expression getExpression() {
        return expression;
    }

    public Expression getParameters() {
        return parameters;
    }

    @Override
    public String toString() {
        return expression.toString().concat(parameters.toString());
    }
}