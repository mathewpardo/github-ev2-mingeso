package com.mingeso.planillaservice.repository;

import com.mingeso.planillaservice.entity.PlanillaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanillaRepository extends JpaRepository<PlanillaEntity,String> {
}
