package com.kilin.ast.expression;

import com.kilin.ast.Node;
import com.kilin.ast.Stream;
import com.kilin.ast.declaration.CallableDeclaration;
import com.kilin.ast.declaration.TypeDeclaration;

public class MethodCallExpression extends Expression {
    private Expression arguments;
    private Expression method;

    public MethodCallExpression(Node prarent, Expression method, Expression arguments) {
        super(prarent);
        this.method = method;
        this.arguments = arguments;

        this.method.setPrarent(this);
        this.arguments.setPrarent(this);

        getChildrens().addAll(method, arguments);
    }

    public static void parser(Node node) {
        if (node instanceof MethodCallExpression) return;
        if (node.getPrarent() instanceof TypeDeclaration) return;
        Stream.of(node.getChildrens()).reduce2((list, a, b) -> {
            if (a instanceof CallableDeclaration c) {
                MethodCallExpression expression = new MethodCallExpression(node, c.getExpression(), c.getParameters());
                list.replace(a, expression);
            }
        });
    }

    public Expression getArguments() {
        return arguments;
    }

    public Expression getMethod() {
        return method;
    }

    @Override
    public String toString() {
        return method.toString().concat(arguments.toString());
    }
}