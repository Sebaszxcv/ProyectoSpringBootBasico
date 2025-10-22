package com.ebac.modulo61.service;

import com.ebac.modulo61.model.DataBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioAutowired {

    @Autowired
    private DataBase dataBase;

    public String ejecutarConsulta() {
        return "Servicio (@Autowired): " + dataBase.obtenerDato();
    }
}
