package com.kilin.ast.declaration;

import com.kilin.ast.Node;
import com.kilin.ast.Stream;
import com.kilin.ast.expression.Expression;
import com.kilin.ast.expression.Name;
import com.kilin.ast.lexer.TokenType;
import com.kilin.ast.type.Type;

import java.util.List;

public class FieldDeclaration extends Declaration {
    private List<TokenType> modifiers;
    private Type type;
    private Name name;
    private Expression initializer;

    public FieldDeclaration(Node prarent, List<TokenType> modifiers, Type type, Name name) {
        super(prarent);
        this.modifiers = modifiers;
        this.type = type;
        this.name = name;

        this.type.setPrarent(this);
        this.name.setPrarent(this);

        getChildrens().addAll(type, name);
    }

    public FieldDeclaration(Node prarent, List<TokenType> modifiers, Type type, Name name, Expression initializer) {
        super(prarent);
        this.modifiers = modifiers;
        this.type = type;
        this.name = name;
        this.initializer = initializer;

        this.type.setPrarent(this);
        this.name.setPrarent(this);
        this.initializer.setPrarent(this);

        getChildrens().addAll(type, name, initializer);
    }

    public static void parser(Node node) {
        if (!(node.getPrarent() instanceof ClassOrInterfaceDeclaration)) return;
        if (node instanceof FieldDeclaration) return;
        Stream.of(node.getChildrens()).reduce((list, a, b) -> {
            Stream.of(a.getChildrens()).reduce((c, m, n) -> {
                if (!a.endsTypeof(CallableDeclaration.class) && m instanceof Name && n instanceof Name) {
                    List<TokenType> modifiers = a.getFieldModifiers();
                    FieldDeclaration declare = new FieldDeclaration(node.getPrarent(), modifiers, Type.getType(m), (Name) n);
                    node.replace(a, declare);
                    c.clear();
                }
            });
        });
    }

    public List<TokenType> getModifiers() {
        return modifiers;
    }

    public Type getType() {
        return type;
    }

    public Name getName() {
        return name;
    }

    public Expression getInitializer() {
        return initializer;
    }

}