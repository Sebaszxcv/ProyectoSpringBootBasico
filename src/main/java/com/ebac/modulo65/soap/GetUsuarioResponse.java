package com.ebac.modulo65.soap;

import com.ebac.modulo65.dto.Usuario;
import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "getUsuarioResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetUsuarioResponse {

    @XmlElement
    private Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}