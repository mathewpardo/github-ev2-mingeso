package com.mingeso.planillaservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "planillas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanillaEntity {
    @Id
    @Column(unique = true, nullable = false)
    private String rut;
    private String apellidoEmpleado;
    private String nombreEmpleado;
    private String categoriaEmpleado;
    private int aniosEnEmpresa;
    private int sueldoFijo;
    private int montoBonificacion;
    private int montoHorasExtras;
    private int montoDescuentos;
    private int sueldoBruto;
    private int cotizacionPrevisional;
    private int cotizacionSalud;
    private int sueldoFinal;
}
