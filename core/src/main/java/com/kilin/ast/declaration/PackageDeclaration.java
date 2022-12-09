package com.kilin.ast.declaration;

import com.kilin.ast.Stream;
import com.kilin.ast.expression.Name;
import com.kilin.ast.lexer.TokenType;
import com.kilin.ast.Node;

import static com.kilin.ast.lexer.TokenType.PACKAGE;

public class PackageDeclaration extends Declaration {
    private Name name;

    public PackageDeclaration(Node prarent, Name name) {
        super(prarent);
        this.name = name;
        this.name.setPrarent(this);
        getChildrens().add(name);
    }

    public Name getName() {
        return name;
    }

    public static void parser(Node node) {
        Stream.of(node.getChildrens()).reduce(((list, a, b) -> {
            if (a.equals(PACKAGE)) {
                PackageDeclaration declare = new PackageDeclaration(node.getPrarent(), (Name) b);
                node.getPrarent().replace(node, declare);
                node.getChildrens().remove(a);
                list.clear();
            }
        }));
    }

}