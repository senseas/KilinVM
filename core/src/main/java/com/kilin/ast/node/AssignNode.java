package com.kilin.ast.node;

import com.kilin.ast.Stream;
import com.kilin.ast.expression.Expression;
import com.kilin.ast.lexer.TokenType;

import java.util.List;

import static com.kilin.ast.lexer.TokenType.*;

public interface AssignNode {
    List<TokenType> ASSIGN_TYPE = Stream.of(ASSIGN, ADD_ASSIGN, SUB_ASSIGN, MUL_ASSIGN, DIV_ASSIGN, AND_ASSIGN, OR_ASSIGN, XOR_ASSIGN, MOD_ASSIGN, LSHIFT_ASSIGN, RSHIFT_ASSIGN, URSHIFT_ASSIGN);

    Expression getVariable();

    Expression getValue();

    TokenType getOperator();
}