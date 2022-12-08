package com.kilin.ast.node;

import com.kilin.ast.expression.Expression;

public interface ArrayAccessNode {

    Expression getExpression();

    Expression getIndex();
}