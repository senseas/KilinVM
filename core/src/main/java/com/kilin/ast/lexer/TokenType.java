
package com.kilin.ast.lexer;


import com.kilin.ast.Node;
import com.kilin.ast.expression.Name;
import com.kilin.ast.literal.Literal;
import com.kilin.ast.type.PrimitiveType;

import javax.lang.model.SourceVersion;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public enum TokenType {
    //@formatter:off

    // Keywords
    ABSTRACT      (TokenKind.KEYWORD,"abstract"),
    ASSERT        (TokenKind.KEYWORD,"assert"),
    BOOLEAN       (TokenKind.PRIMITIVE,"boolean"),
    BREAK         (TokenKind.KEYWORD,"break"),
    BYTE          (TokenKind.PRIMITIVE,"byte"),
    CASE          (TokenKind.KEYWORD,"case"),
    CATCH         (TokenKind.KEYWORD,"catch"),
    CHAR          (TokenKind.PRIMITIVE,"char"),
    CLASS         (TokenKind.KEYWORD,"class"),
    CONST         (TokenKind.KEYWORD,"const"),
    CONTINUE      (TokenKind.KEYWORD,"continue"),
    DEFAULT       (TokenKind.KEYWORD,"default"),
    DO            (TokenKind.KEYWORD,"do"),
    DOUBLE        (TokenKind.PRIMITIVE,"double"),
    ELSE          (TokenKind.KEYWORD,"else"),
    ENUM          (TokenKind.KEYWORD,"enum"),
    EXTENDS       (TokenKind.KEYWORD,"extends"),
    FINAL         (TokenKind.KEYWORD,"final"),
    FINALLY       (TokenKind.KEYWORD,"finally"),
    FLOAT         (TokenKind.PRIMITIVE,"float"),
    FOR           (TokenKind.KEYWORD,"for"),
    IF            (TokenKind.KEYWORD,"if"),
    GOTO          (TokenKind.KEYWORD,"goto"),
    IMPLEMENTS    (TokenKind.KEYWORD,"implements"),
    IMPORT        (TokenKind.KEYWORD,"import"),
    INSTANCEOF    (TokenKind.KEYWORD,"instanceof"),
    INT           (TokenKind.PRIMITIVE,"int"),
    INTERFACE     (TokenKind.KEYWORD,"interface"),
    LONG          (TokenKind.PRIMITIVE,"long"),
    NATIVE        (TokenKind.KEYWORD,"native"),
    NEW           (TokenKind.KEYWORD,"new"),
    PACKAGE       (TokenKind.KEYWORD,"package"),
    PRIVATE       (TokenKind.KEYWORD,"private"),
    PROTECTED     (TokenKind.KEYWORD,"protected"),
    PUBLIC        (TokenKind.KEYWORD,"public"),
    RETURN        (TokenKind.KEYWORD,"return"),
    SHORT         (TokenKind.PRIMITIVE,"short"),
    STATIC        (TokenKind.KEYWORD,"static"),
    STRICTFP      (TokenKind.KEYWORD,"strictfp"),
    SUPER         (TokenKind.IDENT,"super"),
    SWITCH        (TokenKind.KEYWORD,"switch"),
    SYNCHRONIZED  (TokenKind.KEYWORD,"synchronized"),
    THIS          (TokenKind.IDENT,"this"),
    THROW         (TokenKind.KEYWORD,"throw"),
    THROWS        (TokenKind.KEYWORD,"throws"),
    TRANSIENT     (TokenKind.KEYWORD,"transient"),
    TRY           (TokenKind.KEYWORD,"try"),
    VOID          (TokenKind.KEYWORD,"void"),
    VOLATILE      (TokenKind.KEYWORD,"volatile"),
    WHILE         (TokenKind.KEYWORD,"while"),
    UNDER_SCORE   (TokenKind.KEYWORD,"_"),

    // Separators
    LPAREN        (TokenKind.BRACKET,"("),
    RPAREN        (TokenKind.BRACKET,")"),
    LBRACE        (TokenKind.BRACKET,"{"),
    RBRACE        (TokenKind.BRACKET,"}"),
    LBRACK        (TokenKind.BRACKET,"["),
    RBRACK        (TokenKind.BRACKET,"]"),
    SEMI          (TokenKind.BINARY,";"),
    COMMA         (TokenKind.BINARY,","),
    DOT           (TokenKind.BINARY,"."),
    ELLIPSIS      (TokenKind.BINARY,"..."),
    AT            (TokenKind.BINARY,"@"),
    COLONCOLON    (TokenKind.BINARY,"::"),

    // Operators
    ASSIGN         (TokenKind.BINARY, "="),
    GT             (TokenKind.BINARY, ">"),
    LT             (TokenKind.BINARY, "<"),
    BANG           (TokenKind.BINARY, "!"),
    TILDE          (TokenKind.BINARY, "~"),
    QUESTION       (TokenKind.BINARY, "?"),
    COLON          (TokenKind.BINARY, ":"),
    ARROW          (TokenKind.BINARY, "->"),
    EQUAL          (TokenKind.BINARY, "=="),
    LE             (TokenKind.BINARY, "<="),
    GE             (TokenKind.BINARY, ">="),
    NOTEQUAL       (TokenKind.BINARY, "!="),
    AND            (TokenKind.BINARY, "&&"),
    OR             (TokenKind.BINARY, "||"),
    INC            (TokenKind.BINARY, "++"),
    DEC            (TokenKind.BINARY, "--"),
    ADD            (TokenKind.BINARY, "+"),
    SUB            (TokenKind.BINARY, "-"),
    MUL            (TokenKind.BINARY, "*"),
    DIV            (TokenKind.BINARY, "/"),
    BITAND         (TokenKind.BINARY, "&"),
    BITOR          (TokenKind.BINARY, "|"),
    CARET          (TokenKind.BINARY, "^"),
    MOD            (TokenKind.BINARY, "%"),
    LSHIFT         (TokenKind.BINARY, "<<"),
    RSHIFT         (TokenKind.BINARY, ">>"),
    URSHIFT        (TokenKind.BINARY, ">>>"),
    ADD_ASSIGN     (TokenKind.BINARY, "+="),
    SUB_ASSIGN     (TokenKind.BINARY, "-="),
    MUL_ASSIGN     (TokenKind.BINARY, "*="),
    DIV_ASSIGN     (TokenKind.BINARY, "/="),
    AND_ASSIGN     (TokenKind.BINARY, "&="),
    OR_ASSIGN      (TokenKind.BINARY, "|="),
    XOR_ASSIGN     (TokenKind.BINARY, "^="),
    MOD_ASSIGN     (TokenKind.BINARY, "%="),
    LSHIFT_ASSIGN  (TokenKind.BINARY, "<<="),
    RSHIFT_ASSIGN  (TokenKind.BINARY, ">>="),
    URSHIFT_ASSIGN (TokenKind.BINARY, ">>>="),
    IDENTIFIER     (TokenKind.IDENT,      "");

    //@formatter:on

    /**
     * Classification of token.
     */
    private final TokenKind kind;

    public Node getToken() {
        if (PrimitiveType.isPrimitive(this)) return PrimitiveType.getPrimitiveType(this);
        if (List.of(TokenKind.IDENT).contains(this.kind)) return new Name(this);
        return new Node(this);
    }

    /**
     * Printable name of token.
     */
    private final String name;

    private static final Map<String, TokenType> map = Arrays.stream(TokenType.values()).collect(Collectors.toMap(TokenType::getName, a -> a));

    TokenType(final TokenKind kind, final String name) {
        this.kind = kind;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public TokenKind getKind() {
        return kind;
    }

    public String getNameOrType() {
        return name == null ? super.name().toLowerCase(Locale.ENGLISH) : name;
    }

    public static boolean contains(final String name) {
        return map.containsKey(name);
    }

    public static boolean startsWith(final String name) {
        return map.keySet().stream().anyMatch(a -> a.startsWith(name));
    }

    public static TokenType getType(String name) {
        return map.get(name);
    }

    public static boolean isNumber(String value){
        return Literal.isNumeric(value);
    }

    public static boolean isIdentifier(CharSequence name) {
        return SourceVersion.isIdentifier(name);
    }

    public static boolean isWhitespace(String name) {
        for (char a : name.toCharArray()) {
            if (!Character.isWhitespace(a)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return getNameOrType();
    }

}