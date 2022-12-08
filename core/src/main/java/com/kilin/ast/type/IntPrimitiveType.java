package com.kilin.ast.type;

import com.kilin.ast.lexer.TokenType;

public class IntPrimitiveType extends PrimitiveType {

    public IntPrimitiveType(TokenType type) {
        super(type);
        setTokenType(type);
    }

}