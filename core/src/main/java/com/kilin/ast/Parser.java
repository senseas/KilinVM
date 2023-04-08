package com.kilin.ast;

import com.kilin.ast.declaration.ClassOrInterfaceDeclaration;
import com.kilin.ast.declaration.EnumDeclaration;
import com.kilin.ast.declaration.InstanceOfDeclaration;
import com.kilin.ast.declaration.VariableDeclaration;
import com.kilin.ast.expression.*;
import com.kilin.ast.lexer.TokenType;
import com.kilin.ast.literal.Literal;
import com.kilin.ast.statement.*;
import com.kilin.ast.type.ArrayType;
import com.kilin.ast.util.FileUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class Parser {
    List<Node> list = new ArrayList<>();

    public void add(String a) {
        if (a.isEmpty()) return;

        TokenType type = TokenType.getType(a);
        if (Objects.nonNull(type)) {
            list.add(type.getToken());
        } else if (Literal.isLiteral(a)) {
            list.add(Literal.getLiteral(a));
        } else {
            list.add(new Name(a));
        }
    }

    public void parser(String strFile) {
        Stream<String> stream = FileUtil.readFile(strFile);
        stream.reduce((a, b) -> {
            String c = a.concat(b);
            if (a.equals(Literal.string)) {
                return c;
            } else if (a.startsWith(Literal.string)) {
                if (c.endsWith(Literal.strings)) return c;
                if (!c.endsWith(Literal.string)) return c;
                add(c);
                return "";
            } else if (TokenType.isWhitespace(b)) {
                add(a);
                return "";
            } else if (TokenType.isNumber(c)) {
                return c;
            } else if (TokenType.startsWith(c)) {
                return c;
            } else if (TokenType.isIdentifier(c)) {
                return c;
            } else if (TokenType.contains(a)) {
                add(a);
                return b;
            } else if (TokenType.contains(b)) {
                add(a);
                return b;
            } else {
                return c;
            }
        });

        CompilationUnit compilationUnit = new CompilationUnit();
        parserBlockStatement(compilationUnit);
        parserStatement(compilationUnit);
        reducePre(compilationUnit);
        reduce(compilationUnit);
    }

    private void parserBlockStatement(Node prarent) {
        Node node = new Node(prarent);
        prarent.getChildrens().add(node);
        for (Node o : list) {
            if (o.equals(TokenType.LBRACE)) {
                Node child = new BlockStatement(node, new NodeList<>());
                node.getChildrens().add(child);
                node = child;
            } else if (o.equals(TokenType.RBRACE)) {
                node = node.getPrarent();
            } else if (o.equals(TokenType.LPAREN)) {
                Node child = new ParametersExpression(node);
                node.getChildrens().add(child);
                node = child;
            } else if (o.equals(TokenType.RPAREN)) {
                node = node.getPrarent();
            } else if (o.equals(TokenType.LBRACK)) {
                Node child = new ArrayExpression(node);
                node.getChildrens().add(child);
                node = child;
            } else if (o.equals(TokenType.RBRACK)) {
                node = node.getPrarent();
            } else {
                node.getChildrens().add(o);
            }
        }
    }

    public void parserStatement(Node prarent) {
        NodeList<Node> list = new NodeList<>();
        Node node = new Statement(prarent);
        for (Node o : prarent.getChildrens()) {
            if (o instanceof BlockStatement) {
                node.getChildrens().add(o);
                list.add(node);
                node = new Statement(prarent);
                parserStatement(o);
            } else if (o.equals(TokenType.SEMI)) {
                list.add(node);
                node = new Statement(prarent);
            } else if (prarent.isLast(o)) {
                parserStatement(o);
                node.getChildrens().add(o);
                list.add(node);
            } else {
                parserStatement(o);
                node.getChildrens().add(o);
            }
        }
        prarent.setChildrens(list);
    }

    public static void reducePre(Node node) {
        for (Node n : node.getChildrens()) {
            if (!n.isParsed() && !n.getChildrens().isEmpty()) {
                reducePre(n);
            }
        }
        Name.parser(node);
        TypeParametersExpression.parser(node);
        ArrayAccessExpression.parser(node);
        ArrayType.parser(node);
        ArrayCreationExpression.parser(node);
        UnaryExpression.parser(node);
        BinaryExpression.parser(node);
        ConditionalExpression.parser(node);
        ObjectCreationExpression.parser(node);
        AssignExpression.parser(node);
        InstanceOfDeclaration.parser(node);
        AssertExpression.parser(node);
        VariableDeclaration.parser(node);
    }

    public void reduce(Node node) {
        for (Node n : node.getChildrens()) {
            if (!n.isParsed() &&!n.getChildrens().isEmpty()) {
                reduce(n);
            }
        }
        ClassOrInterfaceDeclaration.parser(node);
        ReturnStatement.parser(node);
        BreakStatement.parser(node);
        ContinueStatement.parser(node);
        MethodCallExpression.parser(node);
        ForEachStatement.parser(node);
        ForStatement.parser(node);
        WhileStatement.parser(node);
        IfStatement.parser(node);
        EnumDeclaration.parser(node);
        LambdaExpression.parser(node);
        SynchronizedStatement.parser(node);
        ThrowStatement.parser(node);
        SwitchStatement.parser(node);
        TryStatement.parser(node);
    }
}