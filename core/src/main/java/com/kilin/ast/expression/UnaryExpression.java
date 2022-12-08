package com.kilin.ast.expression;

import com.kilin.ast.Node;
import com.kilin.ast.NodeList;
import com.kilin.ast.Stream;
import com.kilin.ast.lexer.TokenType;
import com.kilin.ast.literal.NumericLiteral;

import java.sql.CallableStatement;
import java.util.Objects;

public class UnaryExpression extends Expression {
    private Expression expression;
    private TokenType operator;

    public UnaryExpression(Node prarent, Expression expression, TokenType operator) {
        super(prarent);
        this.operator = operator;
        this.expression = expression;
        this.expression.setPrarent(this);
        getChildrens().add(expression);
    }

    public static void parser(Node node) {
        Stream.of(node.getChildrens()).reduce((list, m, n) -> {
            if (Objects.nonNull(m) && Objects.nonNull(n)) {
                if (Stream.of(TokenType.INC, TokenType.DEC).contains(n.getTokenType())) {
                    UnaryExpression expression = new UnaryExpression(node, (Expression) m, n.getTokenType());
                    node.replace(m, expression);
                    node.getChildrens().remove(n);
                    list.clear();
                    parser(node);
                }
            }
        });

        Stream.of(node.getChildrens()).reduce((list, m, n, o) -> {
            if (Objects.nonNull(m) && Objects.nonNull(n)) {
                NodeList<TokenType> nodeList = Stream.of(TokenType.ADD, TokenType.SUB, TokenType.TILDE, TokenType.BANG);
                if (!(m instanceof BinaryExpression ||m instanceof CallableStatement|| m instanceof Name|| m instanceof NumericLiteral) && nodeList.contains(n.getTokenType())) {
                    UnaryExpression expression = new UnaryExpression(node, (Expression) o, n.getTokenType());
                    node.replace(n, expression);
                    node.getChildrens().remove(o);
                    list.clear();
                    parser(node);
                }else if (Stream.of(TokenType.INC, TokenType.DEC).contains(m.getTokenType())) {
                    UnaryExpression expression = new UnaryExpression(node, (Expression) n, m.getTokenType());
                    node.replace(m, expression);
                    node.getChildrens().remove(n);
                    list.clear();
                    parser(node);
                }
            }
        });
    }

    public Expression getExpression() {
        return expression;
    }

    public TokenType getOperator() {
        return operator;
    }
}