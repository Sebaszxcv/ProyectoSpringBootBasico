package com.ebac.modulo65.dto;

import jakarta.persistence.*;

@Entity
@Table(name = "telefonos")
public class Telefono {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTelefono;

    private String numero;
    private String lada;
    private String tipoTelefono;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public Telefono() {}

    public Telefono(String numero, String lada, String tipoTelefono, Usuario usuario) {
        this.numero = numero;
        this.lada = lada;
        this.tipoTelefono = tipoTelefono;
        this.usuario = usuario;
    }

    public int getIdTelefono() { return idTelefono; }
    public void setIdTelefono(int idTelefono) { this.idTelefono = idTelefono; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public String getLada() { return lada; }
    public void setLada(String lada) { this.lada = lada; }

    public String getTipoTelefono() { return tipoTelefono; }
    public void setTipoTelefono(String tipoTelefono) { this.tipoTelefono = tipoTelefono; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
}