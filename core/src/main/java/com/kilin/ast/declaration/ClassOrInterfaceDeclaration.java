package com.kilin.ast.declaration;

import com.kilin.ast.Node;
import com.kilin.ast.Stream;
import com.kilin.ast.expression.Name;
import com.kilin.ast.expression.TypeParametersExpression;
import com.kilin.ast.lexer.TokenType;
import com.kilin.ast.statement.BlockStatement;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClassOrInterfaceDeclaration extends TypeDeclaration {
    private List<Object> extendedTypes;
    private List<Object> implementedTypes;
    private List<TokenType> modifiers;
    private Name name;
    private BlockStatement body;

    public ClassOrInterfaceDeclaration(Node prarent, List<TokenType> modifiers, Name name, BlockStatement body) {
        super(prarent);
        this.modifiers = modifiers;
        this.name = name;
        this.body = body;
    }

    public static void parser(Node node) {
        AnnotationDeclaration.parser(node);
        PackageDeclaration.parser(node);
        ImportDeclaration.parser(node);

        Stream.of(node.getChildrens()).reduce((list, a, b) -> {
            if (b instanceof BlockStatement) {
                Stream.of(a.getChildrens()).reduce((c, m, n) -> {
                    if (m.equals(TokenType.CLASS)) {
                        List<TokenType> modifiers = a.getFieldModifiers();
                        ClassOrInterfaceDeclaration declare = new ClassOrInterfaceDeclaration(node.getPrarent(), modifiers, (Name) n, (BlockStatement) b);

                        b.setPrarent(declare);
                        declare.setChildrens(a.getChildrens());
                        declare.getChildrens().add(b);
                        a.getChildrens().remove(m);
                        node.replaceAndRemove(a, declare, b);

                        TypeParametersExpression.parser(declare);
                        parserImplements(declare);
                        parserExtends(declare);

                        ConstructorDeclaration.parser(b);
                        MethodDeclaration.parser(b);
                        FieldDeclaration.parser(b);
                    }
                });
            }
        });
    }

    public static void parserImplements(ClassOrInterfaceDeclaration classDeclare) {
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

    public static void parserExtends(ClassOrInterfaceDeclaration classDeclare) {
        if (classDeclare.getChildrens().contains(TokenType.EXTENDS)) {
            List<Object> list = null;
            for (Object a : List.copyOf(classDeclare.getChildrens())) {
                if (a.equals(TokenType.IMPLEMENTS)) {
                    classDeclare.setExtendedTypes(list);
                    classDeclare.getChildrens().removeAll(list);
                    return;
                } else if (a instanceof BlockStatement) {
                    classDeclare.setExtendedTypes(list);
                    classDeclare.getChildrens().removeAll(list);
                    return;
                } else if (a.equals(TokenType.EXTENDS)) {
                    list = new ArrayList<>();
                    classDeclare.getChildrens().remove(a);
                } else if (Objects.nonNull(list)) {
                    list.add(a);
                }
            }
        }
    }

    public List<Object> getExtendedTypes() {
        return extendedTypes;
    }

    public void setExtendedTypes(List<Object> extendedTypes) {
        this.extendedTypes = extendedTypes;
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