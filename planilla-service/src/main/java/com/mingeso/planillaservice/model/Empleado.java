package com.mingeso.planillaservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Empleado {
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
