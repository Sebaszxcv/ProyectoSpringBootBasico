package com.ebac.modulo61.controller;

import com.ebac.modulo61.service.FiguraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/figura")
public class FiguraController {

    @Autowired
    private FiguraService figuraService;

    @GetMapping("/area")
    @ResponseBody
    public String obtenerArea() {
        return "Área calculada: " + figuraService.mostrarArea();
    }
}
