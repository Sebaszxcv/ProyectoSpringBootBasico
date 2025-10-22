package com.ebac.modulo61.service;

import com.ebac.modulo61.figura.Figura;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class FiguraService {

    @Autowired
    @Qualifier("triangulo")
    private Figura figura;

    public double mostrarArea() {
        return figura.calcularArea();
    }
}
