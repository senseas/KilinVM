package com.kilin.ast.expression;

import com.kilin.ast.Node;
import com.kilin.ast.Stream;
import com.kilin.ast.declaration.*;
import com.kilin.ast.lexer.TokenType;
import com.kilin.ast.statement.BlockStatement;

import java.util.List;

public class ObjectCreationExpression extends TypeDeclaration {
    private Name name;
    private Expression parameters;
    private BlockStatement body;

    public ObjectCreationExpression(Node prarent, Name name, Expression parameters) {
        super(prarent);
        this.name = name;
        this.parameters = parameters;

        this.name.setPrarent(this);
        this.parameters.setPrarent(this);

        getChildrens().addAll(name, parameters);
        setParsed(true);
    }

    public ObjectCreationExpression(Node prarent, Name name, Expression parameters, BlockStatement body) {
        super(prarent);
        this.name = name;
        this.parameters = parameters;
        this.body = body;

        this.name.setPrarent(this);
        this.parameters.setPrarent(this);
        this.body.setPrarent(this);

        getChildrens().addAll(name, parameters, body);
        setParsed(true);
    }

    public static void parser(Node node) {
        Stream.of(node.getChildrens()).reduce((list, a, b, c) -> {
            if (a.equals(TokenType.NEW) && b instanceof CallableDeclaration o) {
                if (c instanceof BlockStatement) {
                    ObjectCreationExpression expression = new ObjectCreationExpression(node, (Name) o.getExpression(), o.getParameters(), (BlockStatement) c);
                    node.replace(a, expression);
                    node.getChildrens().removeAll(b, c);
                    ConstructorDeclaration.parser(c);
                    MethodDeclaration.parser(c);
                    FieldDeclaration.parser(c);
                    list.removeAll(List.of(b, c));
                } else {
                    ObjectCreationExpression expression = new ObjectCreationExpression(node, (Name) o.getExpression(), o.getParameters());
                    node.replace(a, expression);
                    node.getChildrens().remove(b);
                }
            }
        });
    }

    public Name getName() {
        return name;
    }

    public Expression getParameters() {
        return parameters;
    }

    public BlockStatement getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "new ".concat(name.toString()).concat(parameters.toString());
    }
}