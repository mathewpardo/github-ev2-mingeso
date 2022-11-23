package com.mingeso.authorizationservice.repository;

import com.mingeso.authorizationservice.entity.AuthorizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface AuthorizationRepository extends JpaRepository<AuthorizationEntity,Long> {
    @Query(value = "select * from solicitudes as s where s.rut = :rut_empleado", nativeQuery = true)
    ArrayList<AuthorizationEntity> findAllByRut(@Param("rut_empleado") String rut_empleado);
}
