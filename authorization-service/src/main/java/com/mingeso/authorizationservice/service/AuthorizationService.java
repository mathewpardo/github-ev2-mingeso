package com.mingeso.authorizationservice.service;

import com.mingeso.authorizationservice.entity.AuthorizationEntity;
import com.mingeso.authorizationservice.repository.AuthorizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service
public class AuthorizationService {

    @Autowired
    AuthorizationRepository authorizationRepository;

    @Autowired
    RestTemplate restTemplate;

    public ArrayList<AuthorizationEntity> obtenerTodas(){return (ArrayList<AuthorizationEntity>) authorizationRepository.findAll();}

    public AuthorizationEntity guardarAutorizacion(AuthorizationEntity autorizacion) {return authorizationRepository.save(autorizacion);}

    public ArrayList<AuthorizationEntity> obtenerPorRut(String rut){return authorizationRepository.findAllByRut(rut);}

    public ArrayList<AuthorizationEntity> obtenerAutorizacionesPorIdEmpleado(int idEmpleado){
        String rutEmpleado= restTemplate.getForObject("http://localhost:8080/empleados/" + idEmpleado, String.class);
        ArrayList<AuthorizationEntity> autorizacionesEmpleado= obtenerPorRut(rutEmpleado);

        return autorizacionesEmpleado;
    }
}