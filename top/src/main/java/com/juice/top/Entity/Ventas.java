package com.juice.top.Entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "ventas")
public class Ventas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "fecha")
    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "jugo_id")
    private TipoJugos jugo;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "valor_unitario")
    private BigDecimal precio;

    // Getters y Setters

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

    public TipoJugos getJugo() {
        return jugo;
    }

    public void setJugo(TipoJugos jugo) {
        this.jugo = jugo;
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

    public Ventas(Long id, LocalDate fecha, TipoJugos jugo, Integer cantidad, BigDecimal precio) {
        this.id = id;
        this.fecha = fecha;
        this.jugo = jugo;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public Ventas() {
    }
}
