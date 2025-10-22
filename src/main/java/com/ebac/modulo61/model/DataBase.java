package com.ebac.modulo61.model;

public class DataBase {

    public String getConexion() {
        return "✅ Conexión simulada a base de datos establecida correctamente";
    }

    public String obtenerDato() {
        return "Datos obtenidos correctamente desde la base de datos simulada.";
    }

    public void imprimirConexion() {
        System.out.println(getConexion());
    }
}
