package com.kilin.ast.statement;

import com.kilin.ast.Node;
import com.kilin.ast.NodeList;
import com.kilin.ast.Stream;
import com.kilin.ast.expression.Expression;
import com.kilin.ast.expression.ParametersExpression;
import com.kilin.ast.lexer.TokenType;

import java.util.List;

public class ForEachStatement extends Statement {
    private Expression variable;
    private Expression expression;
    private BlockStatement body;

    public ForEachStatement(Node prarent, Expression variable, Expression expression, BlockStatement body) {
        super(prarent);
        this.variable = variable;
        this.expression = expression;
        this.body = body;

        this.variable.setPrarent(this);
        this.expression.setPrarent(this);
        this.body.setPrarent(this);

        getChildrens().addAll(variable, expression, body);
    }

    public static void parser(Node node) {
        if (node instanceof ForStatement) return;
        Stream.of(node.getChildrens()).reduce((list, a, b, c) -> {
            if (a.equals(TokenType.FOR) && b instanceof ParametersExpression && b.getChildrens().get(0).getChildrens().stream().anyMatch(e -> e.equals(TokenType.COLON))) {
                NodeList<Node> split = b.getChildrens().get(0).split(TokenType.COLON);
                //create ForNode and set Prarent , Parameters
                if (c instanceof BlockStatement) {
                    //remove ForNode and Parameters
                    ForEachStatement statement = new ForEachStatement(node, new Expression(null, split.get(0).getChildrens()), new Expression(null, List.of(split.get(1))), (BlockStatement) c);
                    node.getPrarent().replace(node, statement);
                } else {
                    //remove ForNode and Parameters
                    node.getChildrens().removeAll(a, b);
                    //create BlockNode and set Childrens
                    BlockStatement block = new BlockStatement(null, node.getChildrens());
                    //create ForNode and set Prarent，Parameters
                    ForEachStatement statement = new ForEachStatement(node, new Expression(null, split.get(0).getChildrens()), new Expression(null, List.of(split.get(1))), block);
                    //replace this node whit ForNode
                    node.getPrarent().replace(node, statement);
                }
            }
        });
    }

}