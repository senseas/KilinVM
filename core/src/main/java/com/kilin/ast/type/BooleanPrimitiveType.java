package com.kilin.ast.type;

import com.kilin.ast.lexer.TokenType;

public class BooleanPrimitiveType extends PrimitiveType {

    public BooleanPrimitiveType(TokenType type) {
        super(type);
        setTokenType(type);
    }

}