package com.kilin.ast.declaration;

import com.kilin.ast.Node;
import com.kilin.ast.Stream;
import com.kilin.ast.expression.Expression;
import com.kilin.ast.expression.Name;
import com.kilin.ast.type.Type;
import com.kilin.ast.lexer.TokenType;

import java.util.List;
import java.util.Objects;

public class InstanceOfDeclaration extends Declaration {
    private Expression expression;
    private Type type;

    public InstanceOfDeclaration(Node prarent, Expression expression, Type type) {
        super(prarent);
        this.expression = expression;
        this.type = type;

        this.expression.setPrarent(this);
        this.type.setPrarent(this);

        getChildrens().addAll(expression, type);
    }

    public static void parser(Node node) {
        Stream.of(node.getChildrens()).reduce((list, a, b, c) -> {
            if (a instanceof Name && Objects.nonNull(b) && b.equals(TokenType.INSTANCEOF)) {
                InstanceOfDeclaration declare = new InstanceOfDeclaration(node, (Expression) a, (Type) c);
                node.replace(a, declare);
                list.removeAll(List.of(b, c));
            }
        });
    }

    public Expression getExpression() {
        return expression;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() { return expression.toString().concat(TokenType.INSTANCEOF.toString()).concat(type.toString()); }
}