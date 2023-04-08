package com.kilin.ast.statement;

import com.kilin.ast.Node;
import com.kilin.ast.Stream;
import com.kilin.ast.expression.Expression;
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
        DoWhileStatement.parser(node);
        Stream.of(node.getChildrens()).reduce((list, m, condition, body) -> {
            if (m.equals(TokenType.WHILE)) {
                if (body instanceof BlockStatement) {
                    //remove WhileNode and Parameters
                    //create WhileNode and set prarent, condition
                    WhileStatement statement = new WhileStatement(node, (Expression) condition, (BlockStatement) body);
                    //remove WhileNode body
                    node.getPrarent().replace(node, statement);
                    list.remove(body);
                } else {
                    //remove WhileNode and Parameters
                    node.getChildrens().removeAll(m, condition);
                    //create BlockNode and set Childrens
                    BlockStatement block = new BlockStatement(null, node.getChildrens());
                    WhileStatement statement = new WhileStatement(node, (Expression) condition, block);
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

    public String toString() {
        return "while ".concat(condition.toString()).concat("{ ").concat(body.toString()).concat(" }");
    }
}