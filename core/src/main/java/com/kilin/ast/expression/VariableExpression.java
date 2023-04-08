package com.kilin.ast.expression;

import com.kilin.ast.Node;
import com.kilin.ast.NodeList;
import com.kilin.ast.declaration.Declaration;

import java.util.Objects;
import java.util.stream.Collectors;

public class VariableExpression extends Declaration {
    private NodeList<Node> variables;

    public VariableExpression(Node prarent, NodeList<Node> variables) {
        super(prarent);
        this.variables = variables;
        variables.forEach(a -> a.setPrarent(this));
        getChildrens().addAll(variables);
        setParsed(true);
    }

    public NodeList<Node> getVariables() {
        return variables;
    }

    @Override
    public String toString() {
        return variables.stream().map(Objects::toString).collect(Collectors.joining(","));
    }
}