package com.kilin.ast.statement;

import com.kilin.ast.Node;
import com.kilin.ast.NodeList;

public class BlockStatement extends Statement {
    public BlockStatement(Node prarent, NodeList<Node> childrens) {
        super(prarent);
        childrens.forEach(a -> a.setPrarent(this));
        getChildrens().addAll(childrens);
    }
}