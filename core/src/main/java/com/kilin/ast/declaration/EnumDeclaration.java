package com.kilin.ast.declaration;

import com.kilin.ast.Node;
import com.kilin.ast.Stream;
import com.kilin.ast.expression.Name;
import com.kilin.ast.lexer.TokenType;
import com.kilin.ast.statement.BlockStatement;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.kilin.ast.lexer.TokenType.ENUM;

public class EnumDeclaration extends TypeDeclaration {
    private List<Object> implementedTypes;
    private List<TokenType> modifiers;
    private Name name;
    private BlockStatement body;

    public EnumDeclaration(Node prarent, List<TokenType> modifiers, Name name, BlockStatement body) {
        super(prarent);
        this.modifiers = modifiers;
        this.name = name;
        this.body = body;

        this.name.setPrarent(this);
        this.body.setPrarent(this);

        getChildrens().addAll(name, body);
    }

    public static void parser(Node node) {
        PackageDeclaration.parser(node);
        ImportDeclaration.parser(node);

        Stream.of(node.getChildrens()).reduce((list, a, b) -> {
            if (b instanceof BlockStatement) {
                Stream.of(a.getChildrens()).reduce((c, m, n) -> {
                    if (m.equals(ENUM)) {
                        List<TokenType> modifiers = a.getFieldModifiers();
                        EnumDeclaration declare = new EnumDeclaration(node.getPrarent(), modifiers, (Name) n, (BlockStatement) b);
                        a.getChildrens().remove(m);

                        node.replaceAndRemove(a, declare, b);
                        parserImplements(declare);
                        EnumConstantDeclaration.parser(b);
                        ConstructorDeclaration.parser(b);
                        MethodDeclaration.parser(b);
                        FieldDeclaration.parser(b);
                    }
                });
            }
        });
    }

    public static void parserImplements(EnumDeclaration classDeclare) {
        if (classDeclare.getChildrens().contains(TokenType.IMPLEMENTS)) {
            List<Object> list = null;
            for (Object a : List.copyOf(classDeclare.getChildrens())) {
                if (a instanceof BlockStatement) {
                    classDeclare.setImplementedTypes(list);
                    classDeclare.getChildrens().removeAll(list);
                    return;
                } else if (a.equals(TokenType.IMPLEMENTS)) {
                    list = new ArrayList<>();
                    classDeclare.getChildrens().remove(a);
                } else if (Objects.nonNull(list)) {
                    list.add(a);
                }
            }
        }
    }

    public List<Object> getImplementedTypes() {
        return implementedTypes;
    }

    public void setImplementedTypes(List<Object> implementedTypes) {
        this.implementedTypes = implementedTypes;
    }

    public List<TokenType> getModifiers() {
        return modifiers;
    }

    public Name getName() {
        return name;
    }

    public BlockStatement getBody() {
        return body;
    }

}