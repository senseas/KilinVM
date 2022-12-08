package com.kilin.ast.expression;

import com.kilin.ast.lexer.TokenType;
import com.kilin.ast.Node;
import com.kilin.ast.Stream;

import java.util.List;
import java.util.Objects;

public class AssertExpression extends Expression {
    private Expression condition;
    private Expression detail;

    public AssertExpression(Node prarent, Expression condition, Expression detail) {
        super(prarent);
        this.condition = condition;
        this.detail = detail;

        this.condition.setPrarent(this);
        this.detail.setPrarent(this);

        getChildrens().addAll(condition, detail);
    }

    public static void parser(Node node) {
        ArrayAccessExpression.parser(node);
        Stream.of(node.getChildrens()).reduce((list, a, b, c, d) -> {
            if (a.equals(TokenType.ASSERT) && Objects.nonNull(c) && c.equals(TokenType.COLON)) {
                AssertExpression expression = new AssertExpression(node, (Expression) b, (Expression) d);
                node.replace(a, expression);
                list.removeAll(List.of(b, c, d));
            }
        });
    }

    public Expression getCondition() {
        return condition;
    }

    public Expression getDetail() {
        return detail;
    }

    @Override
    public String toString() {
        return TokenType.ASSERT.toString().concat(" ").concat(condition.toString()).concat(":").concat(detail.toString());
    }

}