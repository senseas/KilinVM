package com.kilin.ast.declaration;

import com.kilin.ast.Stream;
import com.kilin.ast.expression.Expression;
import com.kilin.ast.lexer.TokenType;
import com.kilin.ast.Node;

import java.util.List;

public class Declaration extends Expression {
    public static List<TokenType> Method_Modifiers = Stream.of(TokenType.PUBLIC, TokenType.PROTECTED, TokenType.PRIVATE, TokenType.STATIC, TokenType.FINAL, TokenType.ABSTRACT, TokenType.DEFAULT, TokenType.SYNCHRONIZED);
    public static List<TokenType> Field_Modifiers = Stream.of(TokenType.PUBLIC, TokenType.PROTECTED, TokenType.PRIVATE, TokenType.STATIC, TokenType.FINAL, TokenType.VOLATILE, TokenType.TRANSIENT);

    public Declaration(Node prarent) {
        super(prarent);
    }
}