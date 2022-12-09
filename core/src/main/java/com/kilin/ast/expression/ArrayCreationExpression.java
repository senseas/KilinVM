package com.kilin.ast.expression;

import com.kilin.ast.Node;
import com.kilin.ast.Stream;
import com.kilin.ast.statement.BlockStatement;
import com.kilin.ast.type.ArrayType;
import com.kilin.ast.type.Type;

import static com.kilin.ast.lexer.TokenType.NEW;

public class ArrayCreationExpression extends Expression {
    private Type type;
    private BlockStatement initializer;

    public ArrayCreationExpression(Node prarent, Type type) {
        super(prarent);
        this.type = type;
        this.type.setPrarent(this);
        getChildrens().add(type);
    }

    public ArrayCreationExpression(Node prarent, Type type, BlockStatement initializer) {
        super(prarent);
        this.type = type;
        this.initializer = initializer;

        this.type.setPrarent(this);
        this.initializer.setPrarent(this);

        getChildrens().addAll(type, initializer);
    }

    public static void parser(Node node) {
        Stream.of(node.getChildrens()).reduce((list, m, n) -> {
            if (m.equals(NEW) && n instanceof ArrayType o) {
                if (node.next() instanceof BlockStatement b) {
                    ArrayCreationExpression expression = new ArrayCreationExpression(node, o, b);
                    node.replace(m, expression);
                    node.getChildrens().remove(n);
                    node.getPrarent().getChildrens().remove(b);
                } else {
                    ArrayCreationExpression expression = new ArrayCreationExpression(node, o);
                    node.replace(m, expression);
                    node.getChildrens().remove(n);
                }
            }
        });
    }

    public Type getType() {
        return type;
    }

    public BlockStatement getInitializer() {
        return initializer;
    }

    @Override
    public String toString() {
        return "new ".concat(type.toString());
    }
}