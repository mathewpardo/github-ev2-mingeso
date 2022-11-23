package com.mingeso.authorizationservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "solicitudes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorizationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_solicitud", nullable = false)
    private Long id_solicitud;
    private String rut;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate fechaHorasExtra;
    private String firma;
}
