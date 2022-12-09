package com.kilin.ast.expression;

import com.kilin.ast.Node;
import com.kilin.ast.Stream;
import com.kilin.ast.lexer.TokenType;
import com.kilin.ast.statement.BlockStatement;

import java.util.Objects;

public class LambdaExpression extends Expression {
    private Expression parameters;
    private BlockStatement body;

    public LambdaExpression(Node prarent, Expression parameters, BlockStatement body) {
        super(prarent);
        this.parameters = parameters;
        this.body = body;

        this.parameters.setPrarent(this);
        this.body.setPrarent(this);

        getChildrens().addAll(parameters, body);
    }

    public static void parser(Node node) {
        if (node instanceof LambdaExpression) return;

        Stream.of(node.getChildrens()).reduce((c, m, n) -> {
            if (m instanceof ParametersExpression && Objects.nonNull(n) && n.equals(TokenType.ARROW)) {
                Node b = node.next();
                node.getChildrens().removeAll(m, n);
                if (b instanceof BlockStatement) {
                    LambdaExpression expression = new LambdaExpression(node, (Expression) m, (BlockStatement) b);
                    node.getPrarent().replace(node, expression);
                    node.getPrarent().getChildrens().remove(b);
                } else {
                    BlockStatement block = new BlockStatement(null, node.getChildrens());
                    LambdaExpression expression = new LambdaExpression(node, (Expression) m, block);
                    node.getPrarent().replace(node, expression);
                }
            } else if (m instanceof Name && Objects.nonNull(n) && n.equals(TokenType.ARROW)) {
                Node b = node.next();
                node.getChildrens().removeAll(m, n);
                if (b instanceof BlockStatement) {
                    ParametersExpression parameters = new ParametersExpression(null);
                    parameters.getChildrens().add(m);
                    LambdaExpression expression = new LambdaExpression(node, parameters, (BlockStatement) b);
                    node.getPrarent().replace(node, expression);
                    node.getPrarent().getChildrens().remove(b);
                } else {
                    ParametersExpression parameters = new ParametersExpression(null);
                    parameters.getChildrens().add(m);
                    BlockStatement block = new BlockStatement(null, node.getChildrens());
                    LambdaExpression expression = new LambdaExpression(node, parameters, block);
                    node.getPrarent().replace(node, expression);
                }
            }
        });
    }

    public Expression getParameters() {
        return parameters;
    }

    public BlockStatement getBody() {
        return body;
    }

    @Override
    public String toString() {
        return parameters.toString().concat("->").concat(body.toString());
    }
}