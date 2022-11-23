package com.mingeso.planillaservice.controller;

import com.mingeso.planillaservice.entity.PlanillaEntity;
import com.mingeso.planillaservice.service.PlanillaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/planilla")
public class PlanillaController {

    @Autowired
    PlanillaService planillaService;

    @GetMapping("/ver")
    public ResponseEntity<List<PlanillaEntity>> mostrarDatosURL(){
        planillaService.calcularPlanillaEmpleados();
        List<PlanillaEntity> planilla = planillaService.obtenerSueldos();
        return ResponseEntity.ok(planilla);
    }
}
