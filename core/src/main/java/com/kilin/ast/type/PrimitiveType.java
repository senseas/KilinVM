package com.kilin.ast.type;

import com.kilin.ast.lexer.TokenType;
import com.kilin.ast.expression.Name;

public class PrimitiveType extends Type {

    public PrimitiveType(TokenType type) {
        super(new Name(type));
    }

    public static Type getPrimitiveType(TokenType type) {
        if (type.equals(TokenType.SHORT)) {
            return new ShortPrimitiveType(type);
        } else if (type.equals(TokenType.INT)) {
            return new IntPrimitiveType(type);
        } else if (type.equals(TokenType.LONG)) {
            return new LongPrimitiveType(type);
        } else if (type.equals(TokenType.FLOAT)) {
            return new FloatPrimitiveType(type);
        } else if (type.equals(TokenType.DOUBLE)) {
            return new DoublePrimitiveType(type);
        } else if (type.equals(TokenType.BYTE)) {
            return new BytePrimitiveType(type);
        } else if (type.equals(TokenType.CHAR)) {
            return new CharPrimitiveType(type);
        } else if (type.equals(TokenType.BOOLEAN)) {
            return new BooleanPrimitiveType(type);
        } else {
            return null;
        }
    }

    public static boolean isPrimitive(TokenType type) {
        if (type.equals(TokenType.SHORT)) {
            return true;
        } else if (type.equals(TokenType.INT)) {
            return true;
        } else if (type.equals(TokenType.LONG)) {
            return true;
        } else if (type.equals(TokenType.FLOAT)) {
            return true;
        } else if (type.equals(TokenType.DOUBLE)) {
            return true;
        } else if (type.equals(TokenType.BYTE)) {
            return true;
        } else if (type.equals(TokenType.CHAR)) {
            return true;
        } else if (type.equals(TokenType.BOOLEAN)) {
            return true;
        } else {
            return false;
        }
    }
}