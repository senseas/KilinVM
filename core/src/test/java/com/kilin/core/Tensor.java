package com.kilin.core;

import java.io.Serializable;

public class Tensor<N> implements Serializable {

    public Tensor(String name, Tensor... input) { }

    public void backward() {}

    private String name = "Tensor::";
    private Tensor[] input;
    protected N output;

    public String getName() {
        return name;
    }

    public Tensor[] getInput() {
        return input;
    }

    public void setInput(Tensor[] input) {
        this.input = input;
    }

    public N getOutput() {
        return output;
    }
}