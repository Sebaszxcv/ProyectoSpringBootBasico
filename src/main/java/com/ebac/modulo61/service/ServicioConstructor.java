package com.ebac.modulo61.service;

import com.ebac.modulo61.model.DataBase;
import org.springframework.stereotype.Service;

@Service
public class ServicioConstructor {

    private final DataBase dataBase;

    public ServicioConstructor(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public String ejecutarConsulta() {
        return "Servicio (Constructor): " + dataBase.obtenerDato();
    }
}
