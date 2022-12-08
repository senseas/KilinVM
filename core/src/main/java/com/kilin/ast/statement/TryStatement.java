package com.kilin.ast.statement;

import com.kilin.ast.Node;
import com.kilin.ast.NodeList;
import com.kilin.ast.Stream;
import com.kilin.ast.expression.Expression;
import com.kilin.ast.expression.ParametersExpression;
import com.kilin.ast.lexer.TokenType;

import java.util.Objects;

public class TryStatement extends Statement {
    public Expression resources;
    private BlockStatement tryBody;
    private NodeList<CatchClause> catches;
    private BlockStatement finallyBody;
    private static TryStatement statement;

    public TryStatement(Node prarent, BlockStatement tryBody) {
        super(prarent);
        this.tryBody = tryBody;
        this.catches = new NodeList<>();

        this.tryBody.setPrarent(this);

        getChildrens().addAll(tryBody);
    }

    public TryStatement(Node prarent, Expression resources, BlockStatement tryBody) {
        super(prarent);
        this.resources = resources;
        this.tryBody = tryBody;
        this.catches = new NodeList<>();

        this.resources.setPrarent(this);
        this.tryBody.setPrarent(this);

        getChildrens().addAll(resources, tryBody);
    }

    public void addCatche(CatchClause catchClause) {
        catchClause.setPrarent(this);
        this.getCatches().add(catchClause);
        getChildrens().addAll(catchClause);
    }

    public void setFinallyBody(BlockStatement finallyBody) {
        finallyBody.setPrarent(this);
        this.finallyBody = finallyBody;
        getChildrens().addAll(finallyBody);
    }

    public static void parser(Node node) {
        CatchClause.parser(node);
        statement = null;
        Stream.of(node.getChildrens()).reduce((list, a, b, c) -> {
            Stream.of(a.getChildrens()).reduce((o, m, n) -> {
                if (m.equals(TokenType.TRY) && b instanceof BlockStatement) {
                    if (n instanceof ParametersExpression) {
                        //create TryNode and set Prarent , resources , tryBody
                        statement = new TryStatement(node, (Expression) n, (BlockStatement) b);
                        //remove TryNode and Parameters
                        node.replace(a, statement);
                        node.getChildrens().remove(b);
                        list.remove(b);
                    } else {
                        //create TryNode and set Prarent , tryBody
                        statement = new TryStatement(node, (BlockStatement) b);
                        //remove TryNode and Parameters
                        node.replace(a, statement);
                        node.getChildrens().remove(b);
                        list.remove(b);
                    }
                } else if (Objects.nonNull(statement)) {
                    if (a instanceof CatchClause) {
                        statement.addCatche((CatchClause) a);
                        node.getChildrens().remove(a);
                        o.clear();
                    } else if (m.equals(TokenType.FINALLY)) {
                        statement.setFinallyBody((BlockStatement) b);
                        node.getChildrens().removeAll(a, b);
                        list.remove(b);
                    }
                }
            });
        });
    }

    public Expression getResources() {
        return resources;
    }

    public void setResources(Expression resources) {
        this.resources = resources;
    }

    public BlockStatement getTryBody() {
        return tryBody;
    }

    public void setTryBody(BlockStatement tryBody) {
        this.tryBody = tryBody;
    }

    public NodeList<CatchClause> getCatches() {
        return catches;
    }

    public void setCatches(NodeList<CatchClause> catches) {
        this.catches = catches;
    }

    public BlockStatement getFinallyBody() {
        return finallyBody;
    }
}