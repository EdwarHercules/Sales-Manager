package com.juice.top.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "produccióndiaria")
public class Produccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "jugo_id")
    private TipoJugos jugoId;

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

    public TipoJugos getJugoId() {
        return jugoId;
    }

    public void setJugoId(TipoJugos jugoId) {
        this.jugoId = jugoId;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Produccion(Long id, LocalDate fecha, TipoJugos jugoId, Integer cantidad) {
        this.id = id;
        this.fecha = fecha;
        this.jugoId = jugoId;
        this.cantidad = cantidad;
    }

    public Produccion() {
    }
}
