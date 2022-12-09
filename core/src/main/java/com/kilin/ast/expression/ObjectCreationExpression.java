package com.kilin.ast.expression;

import com.kilin.ast.Node;
import com.kilin.ast.Stream;
import com.kilin.ast.declaration.*;
import com.kilin.ast.lexer.TokenType;
import com.kilin.ast.statement.BlockStatement;

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
    }

    public static void parser(Node node) {
        Stream.of(node.getChildrens()).reduce((list, a, b) -> {
            Stream.of(a.getChildrens()).reduce((c, m, n) -> {
                if (m.equals(TokenType.NEW) && n instanceof CallableDeclaration o) {
                    if (b instanceof BlockStatement) {
                        ObjectCreationExpression expression = new ObjectCreationExpression(node, (Name) o.getExpression(), o.getParameters(), (BlockStatement) b);
                        a.replace(m, expression);
                        a.getChildrens().remove(n);
                        b.setPrarent(expression);

                        ConstructorDeclaration.parser(b);
                        MethodDeclaration.parser(b);
                        FieldDeclaration.parser(b);

                        node.getChildrens().remove(b);
                        list.remove(b);
                    } else {
                        ObjectCreationExpression expression = new ObjectCreationExpression(node, (Name) o.getExpression(), o.getParameters());
                        a.replace(m, expression);
                        a.getChildrens().remove(n);
                    }
                }
            });
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