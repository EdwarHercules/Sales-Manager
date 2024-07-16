package com.juice.top.Entity;


import jakarta.persistence.*;

@Entity
@Table(name = "jugos")
public class TipoJugos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "nombre")
    private String name;
    @Column(name = "sabor")
    private String juice;

    private Integer stock;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJuice() {
        return juice;
    }

    public void setJuice(String juice) {
        this.juice = juice;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public TipoJugos(Long id, String name, String juice, Integer stock) {
        this.id = id;
        this.name = name;
        this.juice = juice;
        this.stock = stock;
    }

    public TipoJugos() {
    }
}
