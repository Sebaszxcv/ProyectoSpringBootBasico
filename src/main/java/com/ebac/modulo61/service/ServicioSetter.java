package com.ebac.modulo61.service;

import com.ebac.modulo61.model.DataBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioSetter {

    private DataBase dataBase;

    @Autowired
    public void setDataBase(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public String ejecutarConsulta() {
        return "Servicio (Setter): " + dataBase.obtenerDato();
    }
}
