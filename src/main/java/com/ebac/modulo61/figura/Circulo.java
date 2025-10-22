package com.ebac.modulo61.figura;

import org.springframework.stereotype.Component;

@Component("circulo")
public class Circulo implements Figura {

    private double radio = 5.0;

    @Override
    public double calcularArea() {
        return Math.PI * radio * radio;
    }
}
