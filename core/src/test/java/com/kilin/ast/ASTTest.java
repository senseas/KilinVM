package com.kilin.ast;

import org.junit.Test;

public class ASTTest {

    @Test
    public void parserTest() {
        Parser parser = new Parser();
        parser.parser("/Users/chengdong/workspace/kilin/core/src/test/java/com/kilin/ast/TensorOparetor.java");
    }

}
