package com.kilin.ast.statement;

import com.kilin.ast.Node;
import com.kilin.ast.Stream;
import com.kilin.ast.expression.Expression;
import com.kilin.ast.expression.ParametersExpression;
import com.kilin.ast.lexer.TokenType;

import java.util.List;

public class ForStatement extends Statement {
    private Expression initializer;
    private Expression condition;
    private Expression update;
    private BlockStatement body;

    public ForStatement(Node prarent, Expression initializer, Expression condition, Expression update, BlockStatement body) {
        super(prarent);
        this.initializer = initializer;
        this.condition = condition;
        this.update = update;
        this.body = body;

        this.initializer.setPrarent(this);
        this.condition.setPrarent(this);
        this.update.setPrarent(this);
        this.body.setPrarent(this);

        getChildrens().addAll(initializer, condition, update, body);
    }

    public static void parser(Node node) {
        Stream.of(node.getChildrens()).reduce((list, a, b, c) -> {
            if (a.equals(TokenType.FOR) && (b instanceof ParametersExpression && !b.getChildrens().get(0).getChildrens().stream().anyMatch(e -> e.equals(TokenType.COLON)))) {
                List<Node> nodes = b.getChildrens();
                if (c instanceof BlockStatement) {
                    //create ForNode and set Prarent，Parameters
                    ForStatement statement = new ForStatement(node, (Expression) nodes.get(0), (Expression) nodes.get(1), (Expression) nodes.get(2), (BlockStatement) c);
                    //remove ForNode and Parameters
                    node.getChildrens().removeAll(a, b);
                    node.getPrarent().replace(node, statement);
                } else {
                    //remove ForNode and Parameters
                    node.getChildrens().removeAll(a, b);
                    //create BlockNode and set Childrens
                    BlockStatement block = new BlockStatement(null, node.getChildrens());
                    //create ForNode and set Prarent , Parameters
                    ForStatement statement = new ForStatement(node, (Expression) nodes.get(0), (Expression) nodes.get(1), (Expression) nodes.get(2), block);
                    //replace this node whit ForNode
                    node.getPrarent().replace(node, statement);
                }
            }
        });
    }
}