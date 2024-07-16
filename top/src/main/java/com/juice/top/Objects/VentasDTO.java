package com.juice.top.Objects;

import com.juice.top.Entity.TipoJugos;


import java.math.BigDecimal;
import java.time.LocalDate;

public class VentasDTO {

    private Long id;

    private LocalDate fecha;

    private TipoJugosDTO tipoJugos;

    private Integer cantidad;

    private BigDecimal precio;

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

    public TipoJugosDTO getTipoJugos() {
        return tipoJugos;
    }

    public void setTipoJugos(TipoJugosDTO tipoJugos) {
        this.tipoJugos = tipoJugos;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public VentasDTO(Long id, LocalDate fecha, TipoJugosDTO tipoJugos, Integer cantidad, BigDecimal precio) {
        this.id = id;
        this.fecha = fecha;
        this.tipoJugos = tipoJugos;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public VentasDTO() {
    }
}
