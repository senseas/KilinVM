package com.kilin.ast.type;

import com.kilin.ast.lexer.TokenType;

public class LongPrimitiveType extends PrimitiveType {

    public LongPrimitiveType(TokenType type) {
        super(type);
        setTokenType(type);
    }

}