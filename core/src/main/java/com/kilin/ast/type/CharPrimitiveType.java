package com.kilin.ast.type;

import com.kilin.ast.lexer.TokenType;

public class CharPrimitiveType extends PrimitiveType {

    public CharPrimitiveType(TokenType type) {
        super(type);
        setTokenType(type);
    }

}