package com.kilin.ast.expression;

import com.kilin.ast.Node;
import com.kilin.ast.Stream;
import com.kilin.ast.declaration.*;
import com.kilin.ast.statement.BlockStatement;

import static com.kilin.ast.lexer.TokenType.NEW;

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
        Stream.of(node.getChildrens()).reduce((list, m, n) -> {
            if (m.equals(NEW) && n instanceof CallableDeclaration o) {
                Node b = node.next();
                if (b instanceof BlockStatement) {
                    ObjectCreationExpression expression = new ObjectCreationExpression(node, (Name) o.getExpression(), o.getParameters(), (BlockStatement) b);
                    node.replace(m, expression);
                    node.getChildrens().remove(n);

                    ConstructorDeclaration.parser(b);
                    MethodDeclaration.parser(b);
                    FieldDeclaration.parser(b);
                    node.getPrarent().getChildrens().remove(b);
                } else {
                    ObjectCreationExpression expression = new ObjectCreationExpression(node, (Name) o.getExpression(), o.getParameters());
                    node.replace(m, expression);
                    node.getChildrens().remove(n);
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