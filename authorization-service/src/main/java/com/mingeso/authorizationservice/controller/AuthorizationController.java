package com.mingeso.authorizationservice.controller;

import com.mingeso.authorizationservice.entity.AuthorizationEntity;
import com.mingeso.authorizationservice.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/autorizaciones")
public class AuthorizationController {

    @Autowired
    AuthorizationService authorizationService;

    @GetMapping
    public ResponseEntity<List<AuthorizationEntity>> getAll(){
        List<AuthorizationEntity> autorizaciones = authorizationService.obtenerTodas();
        return ResponseEntity.ok(autorizaciones);
    }

    // DUDAS AL RESPECTO
    @GetMapping("/empleado/{id}")
    public ResponseEntity<List<AuthorizationEntity>> getById(@PathVariable("id") int id) {
        // obtenerRut por id empleado
        ArrayList<AuthorizationEntity> autorizacionesEmpleado= authorizationService.obtenerAutorizacionesPorIdEmpleado(id);

        return ResponseEntity.ok(autorizacionesEmpleado);
    }

    @PostMapping()
    public ResponseEntity<AuthorizationEntity> save(@RequestBody AuthorizationEntity autorizacion) {
        AuthorizationEntity autorizacionNueva = authorizationService.guardarAutorizacion(autorizacion);
        return ResponseEntity.ok(autorizacionNueva);
    }
}
