package com.kilin.ast.statement;

import com.kilin.ast.Node;
import com.kilin.ast.expression.Expression;

public class Statement extends Expression {
    public Statement() {super(null);}

    public Statement(Node prarent) {super(prarent);}
}