package com.mingeso.justificativoservice.repository;

import com.mingeso.justificativoservice.entity.JustificativoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface JustificativoRepository extends JpaRepository<JustificativoEntity,Long> {
    @Query(value = "select * from justificativos as j where j.rut = :rut_empleado", nativeQuery = true)
    ArrayList<JustificativoEntity> findAllByRut(@Param("rut_empleado") String rut_empleado);
}
