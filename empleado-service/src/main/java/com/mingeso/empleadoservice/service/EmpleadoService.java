package com.mingeso.empleadoservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mingeso.empleadoservice.repository.EmpleadoRepository;
import com.mingeso.empleadoservice.entity.EmpleadoEntity;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class EmpleadoService {
    @Autowired
    EmpleadoRepository empleadoRepository;

    public ArrayList<EmpleadoEntity> obtenerEmpleados(){return (ArrayList<EmpleadoEntity>) empleadoRepository.findAll();}

    public EmpleadoEntity guardarEmpleado(EmpleadoEntity empleado) {return empleadoRepository.save(empleado);}

    public String obtenerRutPorId(int id_empleado){return empleadoRepository.findRutById(id_empleado);}
}
