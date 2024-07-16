package com.juice.top.Entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimientos")
public class Movimientos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    @Column(name = "saldo_antes")
    private BigDecimal saldoAntes;

    @Column(name = "tipo_movimiento")
    private String tipoMovimiento; // VENTA o COMPRA

    @Column(name = "valor")
    private BigDecimal valor;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "saldo_despues")
    private BigDecimal saldoDespues;
// Constructores, getters y setters

    public Movimientos() {
    }

    public Movimientos(LocalDateTime fecha, BigDecimal saldoAntes, String tipoMovimiento, BigDecimal valor, String descripcion, BigDecimal saldoDespues) {
        this.fecha = fecha;
        this.saldoAntes = saldoAntes;
        this.tipoMovimiento = tipoMovimiento;
        this.valor = valor;
        this.descripcion = descripcion;
        this.saldoDespues = saldoDespues;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getSaldoAntes() {
        return saldoAntes;
    }

    public void setSaldoAntes(BigDecimal saldoAntes) {
        this.saldoAntes = saldoAntes;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getSaldoDespues() {
        return saldoDespues;
    }

    public void setSaldoDespues(BigDecimal saldoDespues) {
        this.saldoDespues = saldoDespues;
    }

}
