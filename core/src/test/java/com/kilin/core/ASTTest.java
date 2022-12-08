package com.kilin.core;

import com.kilin.ast.Parser;
import org.junit.Test;

public class ASTTest {

    @Test
    public void parserTest() {
        Parser parser = new Parser();
        parser.parser("D:\\github\\kilin\\core\\src\\test\\java\\com\\kilin\\core\\TensorOparetor.java");
    }

}
