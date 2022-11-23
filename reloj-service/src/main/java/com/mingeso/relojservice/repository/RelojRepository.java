package com.mingeso.relojservice.repository;

import com.mingeso.relojservice.entity.RelojEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

@Repository
public interface RelojRepository extends JpaRepository<RelojEntity, Long> {
    @Query(value = "select * from registros as r where r.rut = :rut_empleado", nativeQuery = true)
    ArrayList<RelojEntity> findAllByRut(@Param("rut_empleado") String rut_empleado);

    @Query(value = "select distinct fecha from registros", nativeQuery = true)
    ArrayList<Date> findAllDates();

    //Query(value = "select * from registros as r where r.rut = :rut_empleado and r.fecha= :fecha_marcas", nativeQuery = true)
    //ArrayList<RelojEntity> findMarcasDiaEmpleado(@Param("rut_empleado") String rut_empleado, @Param("fecha_marcas") LocalDate fecha_marcas);
}
