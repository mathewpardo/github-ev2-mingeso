package com.mingeso.empleadoservice.controller;

import com.mingeso.empleadoservice.entity.EmpleadoEntity;
import com.mingeso.empleadoservice.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/empleados")
public class EmpleadoController {
    @Autowired
    EmpleadoService empleadoService;

    @GetMapping
    public ResponseEntity<List<EmpleadoEntity>> getAll(){
        List<EmpleadoEntity> empleados= empleadoService.obtenerEmpleados();
        if(empleados.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(empleados);
    }

    // DUDAS AL RESPECTO
    @GetMapping("/{id}")
    public ResponseEntity<String> getRutById(@PathVariable("id") int id) {
        String rut_empleado= empleadoService.obtenerRutPorId(id);

        if(rut_empleado.equals(null)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(rut_empleado);
    }

    @PostMapping()
    public ResponseEntity<EmpleadoEntity> save(@RequestBody EmpleadoEntity empleado) {
        EmpleadoEntity empleadoNuevo= empleadoService.guardarEmpleado(empleado);
        return ResponseEntity.ok(empleadoNuevo);
    }
}
