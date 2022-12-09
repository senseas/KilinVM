package com.kilin.ast.statement;

import com.kilin.ast.Node;
import com.kilin.ast.Stream;
import com.kilin.ast.expression.Expression;
import com.kilin.ast.expression.ParametersExpression;
import com.kilin.ast.lexer.TokenType;

public class WhileStatement extends Statement {
    private Expression condition;
    private Statement body;

    public WhileStatement(Node prarent, Expression condition, Statement body) {
        super(prarent);
        this.condition = condition;
        this.body = body;

        this.condition.setPrarent(this);
        this.body.setPrarent(this);

        getChildrens().addAll(condition, body);
    }

    public static void parser(Node node) {
        if (node instanceof WhileStatement) return;
        DoWhileStatement.parser(node);
        Stream.of(node.getChildrens()).reduce((c, m, n) -> {
            if (m.equals(TokenType.WHILE) && n instanceof ParametersExpression) {
                if (node.next() instanceof BlockStatement b) {
                    node.getChildrens().removeAll(m, n);
                    //remove WhileNode and Parameters
                    //create WhileNode and set prarent, condition
                    WhileStatement statement = new WhileStatement(node, (Expression) n, (BlockStatement) b);
                    //remove WhileNode body
                    node.getPrarent().replace(node, statement);
                    node.getPrarent().getChildrens().remove(b);
                } else {
                    //remove WhileNode and Parameters
                    node.getChildrens().removeAll(m, n);
                    //create BlockNode and set Childrens
                    BlockStatement block = new BlockStatement(null, node.getChildrens());
                    WhileStatement statement = new WhileStatement(node, (Expression) n, block);
                    //replace this node whit WhileNode
                    node.getPrarent().replace(node, statement);
                }
            }
        });
    }

    public Expression getCondition() {
        return condition;
    }

    public Statement getBody() {
        return body;
    }
}