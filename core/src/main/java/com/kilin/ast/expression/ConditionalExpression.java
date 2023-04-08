package com.kilin.ast.expression;

import com.kilin.ast.Node;
import com.kilin.ast.NodeList;
import com.kilin.ast.Stream;
import com.kilin.ast.lexer.TokenType;

import java.util.List;
import java.util.Objects;

public class ConditionalExpression extends Expression {
    private Expression condition;
    private Expression trueExpression;
    private Expression falseExpression;

    public ConditionalExpression(Node prarent, Expression condition, Expression trueExpression, Expression falseExpression) {
        super(prarent);
        this.condition = condition;
        this.trueExpression = trueExpression;
        this.falseExpression = falseExpression;

        this.condition.setPrarent(this);
        this.trueExpression.setPrarent(this);
        this.falseExpression.setPrarent(this);

        getChildrens().addAll(condition, trueExpression, falseExpression);
        setParsed(true);
    }

    public static void parser(Node node) {
        BinaryExpression.parser(node);
        Stream.of(node.getChildrens()).reduce((list, a, b, c, d) -> {
            if (Objects.nonNull(b) && b.equals(TokenType.QUESTION) && Objects.nonNull(d) && d.equals(TokenType.COLON)) {
                NodeList<Node> splita = node.split(TokenType.QUESTION);
                NodeList<Node> splitb = splita.get(1).split(TokenType.COLON);
                node.getChildrens().removeAll(List.of(b, d));
                //create SynchronizedNode and set Prarent，Parameters
                ConditionalExpression expression = new ConditionalExpression(node, (Expression) splita.get(0), (Expression) splitb.get(0), (Expression) splitb.get(1));
                //replace this node with SynchronizedNode
                node.replace(a, expression);
                node.getChildrens().removeAll(List.of(expression.getCondition(), expression.getTrueExpression(), expression.getFalseExpression()));
                list.clear();
                parser(node);
            }
        });
    }

    public Expression getCondition() {
        return condition;
    }

    public Expression getTrueExpression() {
        return trueExpression;
    }

    public Expression getFalseExpression() {
        return falseExpression;
    }

}