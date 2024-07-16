package com.juice.top.Objects;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MovimientoDTO {

    private LocalDateTime fecha;
    private BigDecimal saldoAntes;
    private String tipoMovimiento; // VENTA o COMPRA
    private BigDecimal valor;
    private String descripcion;
    private BigDecimal saldoDespues;

    // Constructores, getters y setters

    public MovimientoDTO() {
    }

    public MovimientoDTO(LocalDateTime fecha, BigDecimal saldoAntes, String tipoMovimiento, BigDecimal valor, String descripcion, BigDecimal saldoDespues) {
        this.fecha = fecha;
        this.saldoAntes = saldoAntes;
        this.tipoMovimiento = tipoMovimiento;
        this.valor = valor;
        this.descripcion = descripcion;
        this.saldoDespues = saldoDespues;
    }

    // Getters y Setters

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

