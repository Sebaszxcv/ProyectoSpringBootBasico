package com.ebac.modulo61.figura;

import org.springframework.stereotype.Component;

@Component("cuadrado")
public class Cuadrado implements Figura {

    private double lado = 4.0;

    @Override
    public double calcularArea() {
        return lado * lado;
    }
}
