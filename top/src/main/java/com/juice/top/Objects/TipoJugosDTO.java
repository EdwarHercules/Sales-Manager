package com.juice.top.Objects;

public class TipoJugosDTO {
    private Long id;
    private String name;
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

    public TipoJugosDTO(Long id, String name, String juice, Integer stock) {
        this.id = id;
        this.name = name;
        this.juice = juice;
        this.stock = stock;
    }

    public TipoJugosDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public TipoJugosDTO() {
    }
}
