package com.juice.top.Objects;

import com.juice.top.Entity.TipoJugos;


import java.time.LocalDate;

public class ProduccionDTO {
    private Long id;

    private LocalDate fecha;

    private TipoJugosDTO jugoId;

    private Integer cantidad;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public TipoJugosDTO getJugoId() {
        return jugoId;
    }

    public void setJugoId(TipoJugosDTO jugoId) {
        this.jugoId = jugoId;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public ProduccionDTO(Long id, LocalDate fecha, TipoJugosDTO jugoId, Integer cantidad) {
        this.id = id;
        this.fecha = fecha;
        this.jugoId = jugoId;
        this.cantidad = cantidad;
    }

    public ProduccionDTO() {
    }
}
