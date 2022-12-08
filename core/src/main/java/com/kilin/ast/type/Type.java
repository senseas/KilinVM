package com.kilin.ast.type;

import com.kilin.ast.Node;
import com.kilin.ast.expression.Name;

import java.util.Objects;

public class Type extends Node {
    private String identifier;
    private Name name;

    public Type(Name name) {
        this.identifier = name.getIdentifier();
        this.name = name;
        getChildrens().add(name);
    }

    public static Type getType(Node node) {
        if (node instanceof Type) {
            return (Type) node;
        } else if (Objects.nonNull(node.getTokenType())) {
            return new PrimitiveType(node.getTokenType());
        } else {
            return new ReferenceType((Name) node);
        }
    }

    public String getIdentifier() {
        return identifier;
    }

    public Name getName() {
        return name;
    }

    @Override
    public String toString() {
        return identifier;
    }
}