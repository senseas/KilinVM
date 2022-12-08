package com.kilin.ast.expression;

import com.kilin.ast.Node;

import java.util.List;

public class Expression extends Node {
    public Expression(Node prarent) {
        super(prarent);
    }

    public Expression(Node prarent, List<Node> childrens) {
        super(prarent);
        getChildrens().addAll(childrens);
    }

}