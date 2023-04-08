package com.kilin.ast.expression;

import com.kilin.ast.Node;
import com.kilin.ast.Stream;
import com.kilin.ast.lexer.TokenType;

import java.util.List;
import java.util.Objects;

public class BinaryExpression extends Expression {
    private Expression left;
    private Expression right;
    private TokenType operator;

    public BinaryExpression(Node prarent, Expression left, Expression right, TokenType operator) {
        super(prarent);
        this.left = left;
        this.right = right;
        this.operator = operator;

        this.left.setPrarent(this);
        this.right.setPrarent(this);

        getChildrens().addAll(left, right);
        setParsed(true);
    }

    public static void parser(Node node) {
        UnaryExpression.parser(node);
        Stream.of(node.getChildrens()).reduce((list, m, n, o) -> {
            if (Objects.nonNull(m) && Objects.nonNull(n) && Objects.nonNull(o)) {
                if (Stream.of(TokenType.MUL, TokenType.DIV, TokenType.MOD).contains(n.getTokenType())) {
                    BinaryExpression expression = new BinaryExpression(node, (Expression) m, (Expression) o, n.getTokenType());
                    node.replace(m, expression);
                    node.getChildrens().removeAll(List.of(n, o));
                    list.clear();
                    parser(node);
                }
            }
        });

        Stream.of(node.getChildrens()).reduce((list, m, n, o) -> {
            if (Objects.nonNull(m) && Objects.nonNull(n) && Objects.nonNull(o)) {
                if (Stream.of(TokenType.ADD, TokenType.SUB).contains(n.getTokenType())) {
                    BinaryExpression expression = new BinaryExpression(node, (Expression) m, (Expression) o, n.getTokenType());
                    node.replace(m, expression);
                    node.getChildrens().removeAll(List.of(n, o));
                    list.clear();
                    parser(node);
                }
            }
        });

        Stream.of(node.getChildrens()).reduce((list, m, n, o) -> {
            if (Objects.nonNull(m) && Objects.nonNull(n) && Objects.nonNull(o)) {
                if (Stream.of(TokenType.RSHIFT, TokenType.URSHIFT, TokenType.LSHIFT).contains(n.getTokenType())) {
                    BinaryExpression expression = new BinaryExpression(node, (Expression) m, (Expression) o, n.getTokenType());
                    node.replace(m, expression);
                    node.getChildrens().removeAll(List.of(n, o));
                    list.clear();
                    parser(node);
                }
            }
        });

        Stream.of(node.getChildrens()).reduce((list, m, n, o) -> {
            if (Objects.nonNull(m) && Objects.nonNull(n) && Objects.nonNull(o)) {
                if (Stream.of(TokenType.GT, TokenType.GE, TokenType.LT, TokenType.LE).contains(n.getTokenType())) {
                    BinaryExpression expression = new BinaryExpression(node, (Expression) m, (Expression) o, n.getTokenType());
                    node.replace(m, expression);
                    node.getChildrens().removeAll(List.of(n, o));
                    list.clear();
                    parser(node);
                }
            }
        });

        Stream.of(node.getChildrens()).reduce((list, m, n, o) -> {
            if (Objects.nonNull(m) && Objects.nonNull(n) && Objects.nonNull(o)) {
                if (Stream.of(TokenType.EQUAL, TokenType.NOTEQUAL).contains(n.getTokenType())) {
                    BinaryExpression expression = new BinaryExpression(node, (Expression) m, (Expression) o, n.getTokenType());
                    node.replace(m, expression);
                    node.getChildrens().removeAll(List.of(n, o));
                    list.clear();
                    parser(node);
                }
            }
        });

        Stream.of(node.getChildrens()).reduce((list, m, n, o) -> {
            if (Objects.nonNull(m) && Objects.nonNull(n) && Objects.nonNull(o)) {
                if (Stream.of(TokenType.BITAND).contains(n.getTokenType())) {
                    BinaryExpression expression = new BinaryExpression(node, (Expression) m, (Expression) o, n.getTokenType());
                    node.replace(m, expression);
                    node.getChildrens().removeAll(List.of(n, o));
                    list.clear();
                    parser(node);
                }
            }
        });

        Stream.of(node.getChildrens()).reduce((list, m, n, o) -> {
            if (Objects.nonNull(m) && Objects.nonNull(n) && Objects.nonNull(o)) {
                if (Stream.of(TokenType.CARET).contains(n.getTokenType())) {
                    BinaryExpression expression = new BinaryExpression(node, (Expression) m, (Expression) o, n.getTokenType());
                    node.replace(m, expression);
                    node.getChildrens().removeAll(List.of(n, o));
                    list.clear();
                    parser(node);
                }
            }
        });

        Stream.of(node.getChildrens()).reduce((list, m, n, o) -> {
            if (Objects.nonNull(m) && Objects.nonNull(n) && Objects.nonNull(o)) {
                if (Stream.of(TokenType.BITOR).contains(n.getTokenType())) {
                    BinaryExpression expression = new BinaryExpression(node, (Expression) m, (Expression) o, n.getTokenType());
                    node.replace(m, expression);
                    node.getChildrens().removeAll(List.of(n, o));
                    list.clear();
                    parser(node);
                }
            }
        });

        Stream.of(node.getChildrens()).reduce((list, m, n, o) -> {
            if (Objects.nonNull(m) && Objects.nonNull(n) && Objects.nonNull(o)) {
                if (Stream.of(TokenType.AND).contains(n.getTokenType())) {
                    BinaryExpression expression = new BinaryExpression(node, (Expression) m, (Expression) o, n.getTokenType());
                    node.replace(m, expression);
                    node.getChildrens().removeAll(List.of(n, o));
                    list.clear();
                    parser(node);
                }
            }
        });

        Stream.of(node.getChildrens()).reduce((list, m, n, o) -> {
            if (Objects.nonNull(m) && Objects.nonNull(n) && Objects.nonNull(o)) {
                if (Stream.of(TokenType.OR).contains(n.getTokenType())) {
                    BinaryExpression expression = new BinaryExpression(node, (Expression) m, (Expression) o, n.getTokenType());
                    node.replace(m, expression);
                    node.getChildrens().removeAll(List.of(n, o));
                    list.clear();
                    parser(node);
                }
            }
        });
    }

    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
    }

    public TokenType getOperator() {
        return operator;
    }

    public String toString() {
        return left.toString().concat(operator.toString()).concat(right.toString());
    }
}