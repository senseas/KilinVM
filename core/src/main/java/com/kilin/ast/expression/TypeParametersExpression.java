package com.kilin.ast.expression;

import com.kilin.ast.Node;
import com.kilin.ast.Stream;
import com.kilin.ast.lexer.TokenType;

import java.util.List;
import java.util.Objects;

public class TypeParametersExpression extends Expression {
    private Expression expression;

    public TypeParametersExpression(Node prarent, Expression expression) {
        super(prarent);
        this.expression = expression;
        this.expression.setPrarent(this);
        getChildrens().add(expression);
        setParsed(true);
    }

    public static void parser(Node node) {
        Name.parser(node);
        Stream.of(node.getChildrens()).reduce((list, m, n, o) -> {
            if (Objects.nonNull(m) && Objects.nonNull(n) && Objects.nonNull(o)) {
                if (m.equals(TokenType.LT) && o.equals(TokenType.GT)) {
                    TypeParametersExpression parameters = new TypeParametersExpression(node, (Expression) n);
                    node.replace(n, parameters);
                    node.getChildrens().removeAll(List.of(m, o));
                    list.clear();
                    parser(node);
                } else if (m.equals(TokenType.LT) && o.equals(TokenType.RSHIFT)) {
                    TypeParametersExpression parameters = new TypeParametersExpression(node, (Expression) n);
                    node.replace(n, parameters);
                    node.replace(o, TokenType.GT.getToken());
                    node.getChildrens().remove(m);
                    list.clear();
                    parser(node);
                } else if (m.equals(TokenType.LT) && o.equals(TokenType.URSHIFT)) {
                    TypeParametersExpression parameters = new TypeParametersExpression(node, (Expression) n);
                    node.replace(n, parameters);
                    node.replace(o, TokenType.RSHIFT.getToken());
                    node.getChildrens().remove(m);
                    list.clear();
                    parser(node);
                }
            }
        });
    }
}