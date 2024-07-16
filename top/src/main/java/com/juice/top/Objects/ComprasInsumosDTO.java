package com.juice.top.Objects;

import com.juice.top.Entity.Insumos;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ComprasInsumosDTO {
    private Long id;

    private LocalDate fecha;

    private InsumosDTO insumoId;

    private BigDecimal cantidad;

    private BigDecimal precioUnitario;

    private BigDecimal total;

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

    public InsumosDTO getInsumoId() {
        return insumoId;
    }

    public void setInsumoId(InsumosDTO insumoId) {
        this.insumoId = insumoId;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public ComprasInsumosDTO(Long id, LocalDate fecha, InsumosDTO insumoId, BigDecimal cantidad, BigDecimal precioUnitario, BigDecimal total) {
        this.id = id;
        this.fecha = fecha;
        this.insumoId = insumoId;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.total = total;
    }

    public ComprasInsumosDTO() {
    }
}
