package com.kilin.ast.declaration;

import com.kilin.ast.Node;
import com.kilin.ast.Stream;
import com.kilin.ast.expression.Expression;
import com.kilin.ast.expression.Name;
import com.kilin.ast.expression.TypeParametersExpression;
import com.kilin.ast.lexer.TokenType;
import com.kilin.ast.statement.BlockStatement;
import com.kilin.ast.type.Type;

import java.util.List;

public class MethodDeclaration extends Declaration {
    private List<TokenType> modifiers;
    private Type returnValue;
    private Name name;
    private Expression parameters;
    private BlockStatement body;

    public MethodDeclaration(Node prarent, List<TokenType> modifiers, Type returnValue, Name name, Expression parameters, BlockStatement body) {
        super(prarent);
        this.modifiers = modifiers;
        this.returnValue = returnValue;
        this.name = name;
        this.parameters = parameters;
        this.body = body;

        this.returnValue.setPrarent(this);
        this.name.setPrarent(this);
        this.parameters.setPrarent(this);
        this.body.setPrarent(this);

        getChildrens().addAll(returnValue, name, parameters, body);
    }

    public static void parser(Node node) {
        if (!(node.getPrarent() instanceof TypeDeclaration)) return;
        if (node instanceof MethodDeclaration) return;
        Stream.of(node.getChildrens()).reduce((list, a, b) -> {
            if (b instanceof BlockStatement) {
                CallableDeclaration.parser(a);
                Stream.of(a.getChildrens()).reduce((c, m, n) -> {
                    if (n instanceof CallableDeclaration o) {
                        List<TokenType> modifiers = a.getMethodModifiers();
                        MethodDeclaration declare = new MethodDeclaration(node.getPrarent(), modifiers, Type.getType(m), (Name) o.getExpression(), o.getParameters(), (BlockStatement) b);
                        TypeParametersExpression.parser(declare);
                        node.replaceAndRemove(a, declare, b);
                    }
                });
            }
        });
    }

    public List<TokenType> getModifiers() {
        return modifiers;
    }

    public Type getReturnValue() {
        return returnValue;
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