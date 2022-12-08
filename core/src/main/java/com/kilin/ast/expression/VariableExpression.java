package com.kilin.ast.expression;

import com.kilin.ast.Node;
import com.kilin.ast.NodeList;
import com.kilin.ast.declaration.Declaration;
import com.kilin.ast.declaration.VariableDeclaration;

import java.util.Objects;
import java.util.stream.Collectors;

public class VariableExpression extends Declaration {
    private NodeList<VariableDeclaration> variables;

    public VariableExpression(Node prarent, NodeList<VariableDeclaration> variables) {
        super(prarent);
        this.variables = variables;
        variables.forEach(a -> a.setPrarent(this));
        getChildrens().addAll(variables);
    }

    public NodeList<VariableDeclaration> getVariables() {
        return variables;
    }

    @Override
    public String toString() {
        return variables.stream().map(Objects::toString).collect(Collectors.joining(","));
    }
}