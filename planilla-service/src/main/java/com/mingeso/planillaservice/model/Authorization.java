package com.mingeso.planillaservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Authorization{
    private Long id_solicitud;
    private String rut;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate fechaHorasExtra;
    private String firma;
}
