package com.kilin.ast.declaration;

import com.kilin.ast.Node;
import com.kilin.ast.Stream;
import com.kilin.ast.expression.Expression;
import com.kilin.ast.expression.Name;
import com.kilin.ast.lexer.TokenType;

public class AnnotationDeclaration extends Declaration {
    private Name annotationType;
    private Expression arguments;

    public AnnotationDeclaration(Node prarent, Name annotationType) {
        super(prarent);
        this.annotationType = annotationType;
        this.annotationType.setPrarent(this);

        getChildrens().addAll(annotationType);
    }

    public AnnotationDeclaration(Node prarent, Name annotationType, Expression arguments) {
        super(prarent);
        this.annotationType = annotationType;
        this.arguments = arguments;

        this.annotationType.setPrarent(this);
        this.arguments.setPrarent(this);

        getChildrens().addAll(annotationType, arguments);
    }

    public static void parser(Node node) {
        if (node instanceof AnnotationDeclaration) return;
        Stream.of(node.getChildrens()).reduce((list, a, b) -> {
            if (a.equals(TokenType.AT) && b instanceof Name) {
                AnnotationDeclaration declare = new AnnotationDeclaration(node.getPrarent(), (Name) b);
                node.getChildrens().remove(b);
                node.replace(a, declare);
            } else if (a.equals(TokenType.AT) && b instanceof CallableDeclaration c) {
                AnnotationDeclaration declare = new AnnotationDeclaration(node.getPrarent(), (Name) c.getExpression(), c.getParameters());
                node.getChildrens().remove(b);
                node.replace(a, declare);
            }
        });
    }

    public Name getAnnotationType() {
        return annotationType;
    }

    public Expression getArguments() {
        return arguments;
    }

}