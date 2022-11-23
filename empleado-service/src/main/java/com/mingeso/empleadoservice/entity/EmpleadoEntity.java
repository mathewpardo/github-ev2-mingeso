package com.mingeso.empleadoservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "empleados")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id_empleado", nullable = false)
    private Long id_empleado;
    private String rut;
    private String nombres;
    private String apellidos;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date fecha_nac;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date fecha_in;
    private String categoria;
}
