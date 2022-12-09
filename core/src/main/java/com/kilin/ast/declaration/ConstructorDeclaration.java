package com.kilin.ast.declaration;

import com.kilin.ast.Node;
import com.kilin.ast.Stream;
import com.kilin.ast.expression.Expression;
import com.kilin.ast.expression.Name;
import com.kilin.ast.expression.TypeParametersExpression;
import com.kilin.ast.lexer.TokenType;
import com.kilin.ast.statement.BlockStatement;

import java.util.List;

public class ConstructorDeclaration extends Declaration {
    private List<TokenType> modifiers;
    private Name name;
    private Expression parameters;
    private BlockStatement body;

    public ConstructorDeclaration(Node prarent, List<TokenType> modifiers, Name name, Expression parameters, BlockStatement body) {
        super(prarent);
        this.modifiers = modifiers;
        this.name = name;
        this.parameters = parameters;
        this.body = body;

        this.name.setPrarent(this);
        this.parameters.setPrarent(this);
        this.body.setPrarent(this);

        getChildrens().addAll(name, parameters, body);
    }

    public static void parser(Node node) {
        if (!(node.getPrarent() instanceof TypeDeclaration)) return;
        if (node instanceof ConstructorDeclaration) return;

        Stream.of(node.getChildrens()).reduce((c, m, n, b) -> {
            if (n instanceof CallableDeclaration o) {
                List<TokenType> modifiers = node.getMethodModifiers();
                if (node.isFirst(n)) {
                    ConstructorDeclaration declare = new ConstructorDeclaration(node.getPrarent(), modifiers, (Name) o.getExpression(), o.getParameters(), (BlockStatement) b);
                    TypeParametersExpression.parser(declare);
                    node.getPrarent().replaceAndRemove(node, declare, b);
                }
            }
        });
    }

    public List<TokenType> getModifiers() {
        return modifiers;
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

}