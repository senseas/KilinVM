package com.kilin.ast.type;

import com.kilin.ast.lexer.TokenType;

public class FloatPrimitiveType extends PrimitiveType {

    public FloatPrimitiveType(TokenType type) {
        super(type);
        setTokenType(type);
    }

}