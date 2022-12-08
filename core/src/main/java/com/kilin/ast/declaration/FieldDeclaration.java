package com.kilin.ast.declaration;

import com.kilin.ast.NodeList;
import com.kilin.ast.Stream;
import com.kilin.ast.expression.Expression;
import com.kilin.ast.expression.Name;
import com.kilin.ast.expression.ParametersExpression;
import com.kilin.ast.lexer.Token;
import com.kilin.ast.lexer.TokenType;
import com.kilin.ast.type.Type;
import com.kilin.ast.Node;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FieldDeclaration extends Declaration {
    private List<TokenType> modifiers;
    private Type type;
    private Name name;
    private Expression initializer;

    public FieldDeclaration(Node prarent) {
        super(prarent);
    }

    public static void parser(Node node) {
        if (!(node.getPrarent() instanceof ClassOrInterfaceDeclaration)) return;
        if (node instanceof FieldDeclaration) return;
        Stream.of(node.getChildrens()).reduce((list, a, b) -> {
            Stream.of(a.getChildrens()).reduce((c, m, n) -> {
                if (m instanceof Name && n instanceof Name && !(a.endsTypeof(ParametersExpression.class)||a.endsTypeof(CallableDeclaration.class))) {
                    List<Node> modifiers = a.getChildrens().stream().filter(e -> Field_Modifiers.contains(e.getTokenType())).toList();
                    a.getChildrens().removeAll(modifiers);
                    a.getChildrens().removeAll(List.of(m, n));

                    FieldDeclaration declare = new FieldDeclaration(node.getPrarent());
                    declare.setModifiers(modifiers.stream().map(Token::getTokenType).collect(Collectors.toList()));
                    declare.setType(Type.getType(m));
                    declare.setName((Name) n);
                    declare.getChildrens().addAll(declare.getType(), declare.getName());

                    NodeList<Expression> split = a.split(TokenType.ASSIGN);
                    if (Objects.nonNull(split)) {
                        Expression expression = split.last();
                        expression.setPrarent(declare);
                        declare.setInitializer(expression);
                        declare.getChildrens().add(expression);
                    }

                    node.replace(a, declare);
                    c.clear();
                }
            });
        });
    }

    public List<TokenType> getModifiers() {
        return modifiers;
    }

    public void setModifiers(List<TokenType> modifiers) {
        this.modifiers = modifiers;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Expression getInitializer() {
        return initializer;
    }

    public void setInitializer(Expression initializer) {
        this.initializer = initializer;
    }
}