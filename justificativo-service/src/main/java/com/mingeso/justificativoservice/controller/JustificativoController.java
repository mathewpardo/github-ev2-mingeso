package com.mingeso.justificativoservice.controller;

import com.mingeso.justificativoservice.entity.JustificativoEntity;
import com.mingeso.justificativoservice.model.Reloj;
import com.mingeso.justificativoservice.service.JustificativoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/justificativos")
@CrossOrigin("*")
public class JustificativoController {

    @Autowired
    JustificativoService justificativoService;

    @GetMapping
    public ResponseEntity<List<JustificativoEntity>> getAll(){
        List<JustificativoEntity> justificativos = justificativoService.obtenerTodas();
        return ResponseEntity.ok(justificativos);
    }

    // DUDAS AL RESPECTO
    @GetMapping("/empleado/{id}")
    public ResponseEntity<List<JustificativoEntity>> getById(@PathVariable("id") int id) {
        // obtenerRut por id empleado
        List<JustificativoEntity> justificativosEmpleado= justificativoService.obtenerJustificativosPorIdEmpleado(id);

        return ResponseEntity.ok(justificativosEmpleado);
    }

    @PostMapping
    public ResponseEntity<JustificativoEntity> save(@RequestBody JustificativoEntity justificativo) {
        JustificativoEntity justificativoNuevo = justificativoService.guardarJustificativo(justificativo);
        return ResponseEntity.ok(justificativoNuevo);
    }

    @GetMapping("/marca/{id}")
    public ResponseEntity<Boolean> verifyById(@PathVariable("id") int id){
        boolean flag = justificativoService.verificarJustificativoIdMarca(id);
        return ResponseEntity.ok(flag);
    }

    /* @GetMapping("/bystudent/{studentId}")
    public ResponseEntity<List<Book>> getByStudentId(@PathVariable("studentId") int studentId) {
        List<Book> books = bookService.byStudentId(studentId);
        return ResponseEntity.ok(books);
    }*/
}
