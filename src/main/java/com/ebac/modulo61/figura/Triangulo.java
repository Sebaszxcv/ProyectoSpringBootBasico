package com.ebac.modulo61.figura;

import org.springframework.stereotype.Component;

@Component("triangulo")
public class Triangulo implements Figura {

    private double base = 6.0;
    private double altura = 3.0;

    @Override
    public double calcularArea() {
        return (base * altura) / 2;
    }
}
