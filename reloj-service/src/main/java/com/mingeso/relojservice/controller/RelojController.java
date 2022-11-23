package com.mingeso.relojservice.controller;

import com.mingeso.relojservice.entity.RelojEntity;
import com.mingeso.relojservice.service.RelojService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/clock")
public class RelojController {
    @Autowired
    RelojService relojService;

    @GetMapping
    public ResponseEntity<List<RelojEntity>> getAll(){
        List<RelojEntity> marcas= relojService.obtenerTodas();
        if(marcas.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(marcas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RelojEntity> getById(@PathVariable("id") int id) {
        RelojEntity marca = relojService.obtenerPorId(id);
        if(marca == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(marca);
    }



    // DUDAS AL RESPECTO
    @GetMapping("/empleado/{id}")
    public ResponseEntity<List<RelojEntity>> getByIdEmpleado(@PathVariable("id") int id) {
        ArrayList<RelojEntity> marcas= relojService.obtenerMarcasPorIdEmpleado(id);

        if(marcas.isEmpty()||marcas==null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(marcas);
    }

    @PostMapping()
    public ResponseEntity<RelojEntity> save(@RequestBody RelojEntity marca) {
        RelojEntity marcaNueva= relojService.guardarMarca(marca);
        return ResponseEntity.ok(marcaNueva);
    }

    /* @GetMapping("/bystudent/{studentId}")
    public ResponseEntity<List<Book>> getByStudentId(@PathVariable("studentId") int studentId) {
        List<Book> books = bookService.byStudentId(studentId);
        return ResponseEntity.ok(books);
    }*/

    @GetMapping("/dias")
    public ResponseEntity<ArrayList<LocalDate>> workDays(){
        return ResponseEntity.ok(relojService.obtenerDiasTrabajables());
    }
}
