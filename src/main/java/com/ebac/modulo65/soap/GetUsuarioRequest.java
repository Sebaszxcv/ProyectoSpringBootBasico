package com.ebac.modulo65.soap;

import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "getUsuarioRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetUsuarioRequest {

    @XmlElement(required = true)
    private int idUsuario;

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}