package com.kilin.ast.expression;

import com.kilin.ast.Node;
import com.kilin.ast.Stream;
import com.kilin.ast.lexer.TokenType;
import com.kilin.ast.node.AssignNode;

import java.util.List;
import java.util.Objects;

public class AssignExpression extends Expression implements AssignNode {
    private Expression variable;
    private Expression value;
    private TokenType operator;

    public AssignExpression(Node prarent, Expression variable, Expression value, TokenType operator) {
        super(prarent);
        this.operator = operator;

        this.variable = variable;
        this.value = value;

        this.variable.setPrarent(this);
        this.value.setPrarent(this);

        getChildrens().addAll(variable, value);
    }

    public static void parser(Node node) {
        ConditionalExpression.parser(node);
        Stream.of(node.getChildrens()).reduce((list, m, n, o) -> {
            if (Objects.nonNull(n) && ASSIGN_TYPE.contains(n.getTokenType())) {
                AssignExpression expression = new AssignExpression(node, (Expression) m, (Expression) o, n.getTokenType());
                node.replaceAndRemove(m, expression, n);
                node.getChildrens().remove(o);
                list.removeAll(List.of(n, o));
            }
        });
    }

    @Override
    public Expression getVariable() {
        return variable;
    }

    @Override
    public Expression getValue() {
        return value;
    }

    @Override
    public TokenType getOperator() {
        return operator;
    }

    @Override
    public String toString() {
        return variable.toString().concat(" ").concat(operator.toString()).concat(" ").concat(value.toString());
    }
}