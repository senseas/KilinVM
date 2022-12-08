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
        Stream.of(node.getChildrens()).reduce((list, a, b) -> {
            Stream.of(a.getChildrens()).reduce((c, m, n) -> {
                if (m.equals(TokenType.FOR) && n instanceof ParametersExpression && n.getChildrens().get(0).getChildrens().stream().anyMatch(e -> e.equals(TokenType.COLON))) {
                    NodeList<Node> split = n.getChildrens().get(0).split(TokenType.COLON);
                    //create ForNode and set Prarent , Parameters
                    if (b instanceof BlockStatement) {
                        //remove ForNode and Parameters
                        a.getChildrens().removeAll(List.of(m, n));
                        ForEachStatement statement = new ForEachStatement(node, new Expression(null, split.get(0).getChildrens()), new Expression(null, List.of(split.get(1))), (BlockStatement) b);
                        node.replace(a, statement);
                        node.getChildrens().remove(b);
                        list.remove(b);
                    } else {
                        //remove ForNode and Parameters
                        a.getChildrens().removeAll(List.of(m, n));
                        //create BlockNode and set Childrens
                        BlockStatement block = new BlockStatement(null, a.getChildrens());
                        //create ForNode and set Prarent，Parameters
                        ForEachStatement statement = new ForEachStatement(node, new Expression(null, split.get(0).getChildrens()), new Expression(null, List.of(split.get(1))), block);
                        //replace this node whit ForNode
                        node.replace(a, statement);
                    }
                }
            });
        });
    }

}