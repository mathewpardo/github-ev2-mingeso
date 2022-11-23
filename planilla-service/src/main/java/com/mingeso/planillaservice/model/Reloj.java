package com.mingeso.planillaservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reloj {
    private Long id_reloj;
    private String rut;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate fecha;
    @JsonFormat(pattern="HH:mm")
    private LocalTime hora;
}
