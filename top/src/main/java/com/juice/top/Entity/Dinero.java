package com.juice.top.Entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Dinero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal saldo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public Dinero(Long id, BigDecimal saldo) {
        this.id = id;
        this.saldo = saldo;
    }

    public Dinero() {
    }
}
