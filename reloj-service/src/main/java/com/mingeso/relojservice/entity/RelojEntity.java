package com.mingeso.relojservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.*;

@Entity
@Table(name = "registros")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelojEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id_reloj", nullable = false)
    private Long id_reloj;
    private String rut;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate fecha;
    @JsonFormat(pattern="HH:mm")
    private LocalTime hora;
}
