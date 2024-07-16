package com.juice.top.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "insumos")
public class Insumos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre")
    private String name;

    @Column(name = "descripcion")
    private String description;

    @Column(name = "unidad_medida")
    private String unidadMedida;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public Insumos(Long id, String name, String description, String unidadMedida) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.unidadMedida = unidadMedida;
    }

    public Insumos() {
    }
}
