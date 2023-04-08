package com.kilin.ast.expression;

import com.kilin.ast.Node;
import com.kilin.ast.Stream;
import com.kilin.ast.lexer.TokenType;
import com.kilin.ast.statement.BlockStatement;
import com.kilin.ast.type.ArrayType;
import com.kilin.ast.type.Type;

public class ArrayCreationExpression extends Expression {
    private Type type;
    private BlockStatement initializer;

    public ArrayCreationExpression(Node prarent, Type type) {
        super(prarent);
        this.type = type;
        this.type.setPrarent(this);
        getChildrens().add(type);
        setParsed(true);
    }

    public ArrayCreationExpression(Node prarent, Type type, BlockStatement initializer) {
        super(prarent);
        this.type = type;
        this.initializer = initializer;

        this.type.setPrarent(this);
        this.initializer.setPrarent(this);

        getChildrens().addAll(type, initializer);
        setParsed(true);
    }

    public static void parser(Node node) {
        Stream.of(node.getChildrens()).reduce((list, a, b, c) -> {
            if (a.equals(TokenType.NEW) && b instanceof ArrayType o) {
                if (c instanceof BlockStatement) {
                    ArrayCreationExpression expression = new ArrayCreationExpression(node, o, (BlockStatement) c);
                    node.replace(a, expression);
                    node.getChildrens().removeAll(b, c);
                } else {
                    ArrayCreationExpression expression = new ArrayCreationExpression(node, o);
                    node.replace(a, expression);
                    node.getChildrens().remove(b);
                }
            }
        });
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return "new ".concat(type.toString());
    }
}