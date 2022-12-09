package com.kilin.ast.statement;

import com.kilin.ast.Node;
import com.kilin.ast.NodeList;
import com.kilin.ast.Stream;
import com.kilin.ast.expression.Expression;
import com.kilin.ast.expression.ParametersExpression;
import com.kilin.ast.lexer.TokenType;

import java.util.List;
import java.util.Objects;

public class IfStatement extends Statement {
    private Expression condition;
    private NodeList thenStatement = new NodeList();
    private Statement elseStatement;
    private Statement body;
    private static IfStatement statement;

    public IfStatement(Node prarent, Expression condition, Statement body) {
        super(prarent);
        this.condition = condition;
        this.body = body;

        this.condition.setPrarent(this);
        this.body.setPrarent(this);

        getChildrens().addAll(condition, body);
    }

    public static void parser(Node node) {
        statement = null;
        Stream.of(node.getChildrens()).reduce((list, a, b) -> {
            Stream.of(a.getChildrens()).reduce((e, m, n, c, k) -> {
                if (m.equals(TokenType.IF) && n instanceof ParametersExpression) {
                    if (c instanceof BlockStatement) {
                        a.getChildrens().removeAll(m, n, c);
                        statement = new IfStatement(node, (Expression) n, (BlockStatement) c);
                        node.replace(a, statement);
                    } else {
                        a.getChildrens().removeAll(m, n);
                        BlockStatement block = new BlockStatement(null, a.getChildrens());
                        statement = new IfStatement(node, (Expression) n, block);
                        node.replace(a, statement);
                        e.clear();
                    }
                } else if (m.equals(TokenType.ELSE) && Objects.nonNull(n) && n.equals(TokenType.IF)) {
                    if (k instanceof BlockStatement) {
                        a.getChildrens().removeAll(m, n, c, k);
                        IfStatement elseifStatement = new IfStatement(statement, (Expression) c, (BlockStatement) k);
                        statement.getThenStatement().add(elseifStatement);
                        node.getChildrens().removeAll(a);
                        list.remove(a);
                        e.clear();
                    } else {
                        a.getChildrens().removeAll(m, n, c);
                        BlockStatement block = new BlockStatement(null, a.getChildrens());
                        IfStatement elseifStatement = new IfStatement(statement, (Expression) c, block);
                        statement.getThenStatement().add(elseifStatement);
                        node.getChildrens().remove(a);
                        list.remove(a);
                        e.clear();
                    }
                } else if (m.equals(TokenType.ELSE)) {
                    if (b instanceof BlockStatement) {
                        a.getChildrens().removeAll(m, n);
                        statement.setElseStatement((BlockStatement) b);
                        node.getChildrens().removeAll(a, b);
                        list.removeAll(List.of(a, b));
                        e.clear();
                    } else {
                        a.getChildrens().remove(m);
                        BlockStatement block = new BlockStatement(null, a.getChildrens());
                        statement.setElseStatement(block);
                        node.getChildrens().remove(a);
                        list.remove(a);
                        e.clear();
                    }
                }
            });
        });
    }

    public Expression getCondition() {
        return condition;
    }

    public void setCondition(Expression condition) {
        this.condition = condition;
    }

    public NodeList getThenStatement() {
        return thenStatement;
    }

    public void setThenStatement(NodeList thenStatement) {
        this.thenStatement = thenStatement;
    }

    public Statement getElseStatement() {
        return elseStatement;
    }

    public void setElseStatement(Statement elseStatement) {
        this.elseStatement = elseStatement;
    }

    public Statement getBody() {
        return body;
    }

    public void setBody(Statement body) {
        this.body = body;
    }

    public static IfStatement getStatement() {
        return statement;
    }

    public static void setStatement(IfStatement statement) {
        IfStatement.statement = statement;
    }
}