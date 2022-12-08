package com.kilin.ast.expression;

import com.kilin.ast.Node;
import com.kilin.ast.Stream;
import com.kilin.ast.declaration.CallableDeclaration;
import com.kilin.ast.lexer.TokenType;
import com.kilin.ast.type.ArrayType;

import java.util.Objects;

public class Name extends Expression {
    private String identifier;
    private Name qualifier;
    private TypeParametersExpression typeParameters;

    private static Node name;

    public Name() {
        super(null);
    }

    public Name(String identifier) {
        super(null);
        this.identifier = identifier;
    }

    public Name(TokenType type) {
        super(null);
        setTokenType(type);
        this.identifier = type.getName();
    }

    public static void parser(Node node) {
        name = null;
        CallableDeclaration.parser(node);
        ArrayType.parser(node);
        Stream.of(node.getChildrens()).reduce((list, m, n) -> {
            if (Objects.nonNull(n)) {
                if (m instanceof Name a && n instanceof TypeParametersExpression b) {
                    a.setTypeParameters(b);
                    node.getChildrens().remove(b);
                    list.clear();
                } else if (m instanceof Name a && n.equals(TokenType.DOT)) {
                    name = a;
                    node.getChildrens().remove(a);
                } else if (m.equals(TokenType.DOT) && n instanceof CallableDeclaration c) {
                    c.setPrarent(node);
                    c.getExpression().getChildrens().add(name);
                    name.setPrarent(c.getExpression());
                    node.getChildrens().removeAll(m, name);
                    name = n;
                    list.remove(n);
                } else if (m.equals(TokenType.DOT) && n instanceof ArrayAccessExpression c) {
                    c.setPrarent(node);
                    c.getExpression().getChildrens().add(name);
                    name.setPrarent(c.getExpression());
                    node.getChildrens().removeAll(m, name);
                    name = n;
                    list.remove(n);
                } else if (m.equals(TokenType.DOT)) {
                    n.setPrarent(node);
                    n.getChildrens().add(name);
                    name.setPrarent(n);
                    node.getChildrens().removeAll(m, name);
                    name = n;
                    list.remove(n);
                }
            }
        });
    }

    public String getIdentifier() {
        return identifier;
    }

    public Name getQualifier() {
        return qualifier;
    }

    public TypeParametersExpression getTypeParameters() {
        return typeParameters;
    }

    public void setTypeParameters(TypeParametersExpression typeParameters) {
        this.typeParameters = typeParameters;
    }

    public String toString() {
        return identifier;
    }
}