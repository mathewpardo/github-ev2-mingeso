package com.mingeso.empleadoservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.mingeso.empleadoservice.entity.EmpleadoEntity;

@Repository
public interface EmpleadoRepository extends JpaRepository<EmpleadoEntity, String> {
    @Query(value="select rut from empleados as e where e.id_empleado = :id_empleado", nativeQuery = true)
    String findRutById(@Param("id_empleado") int id_empleado);
}
