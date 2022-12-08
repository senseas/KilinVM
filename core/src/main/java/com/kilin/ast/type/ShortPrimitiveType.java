package com.kilin.ast.type;

import com.kilin.ast.lexer.TokenType;

public class ShortPrimitiveType extends PrimitiveType {

    public ShortPrimitiveType(TokenType type) {
        super(type);
        setTokenType(type);
    }

}