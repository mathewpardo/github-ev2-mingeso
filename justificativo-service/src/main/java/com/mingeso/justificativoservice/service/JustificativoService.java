package com.mingeso.justificativoservice.service;


import com.mingeso.justificativoservice.model.Reloj;
import com.mingeso.justificativoservice.repository.JustificativoRepository;
import com.mingeso.justificativoservice.entity.JustificativoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class JustificativoService {
    @Autowired
    JustificativoRepository justificativoRepository;

    @Autowired
    RestTemplate restTemplate;

    public List<JustificativoEntity> obtenerTodas(){return justificativoRepository.findAll();}

    public JustificativoEntity guardarJustificativo(JustificativoEntity justificativo){return justificativoRepository.save(justificativo);}

    public ArrayList<JustificativoEntity> obtenerJustificativosEmpleado(String rut){return justificativoRepository.findAllByRut(rut);}

    public boolean verificarJustificativo(Reloj marca){
        ArrayList<JustificativoEntity> justificativosEmpleado=obtenerJustificativosEmpleado(marca.getRut());
        LocalDate fecha=marca.getFecha();
        int i;
        for (i=0;i<justificativosEmpleado.size();i++){
            if(fecha.isEqual(justificativosEmpleado.get(i).getFecha())){
                return true;
            }
        }
        return false;
    }

    public boolean verificarJustificativo(LocalDate fecha,String rutEmpleado){
        ArrayList<JustificativoEntity> justificativosEmpleado=obtenerJustificativosEmpleado(rutEmpleado);
        int i;
        for (i=0;i<justificativosEmpleado.size();i++){
            if(fecha.isEqual(justificativosEmpleado.get(i).getFecha())){
                return true;
            }
        }
        return false;
    }

    public ArrayList<JustificativoEntity> obtenerJustificativosPorIdEmpleado(int idEmpleado){
        String rutEmpleado= restTemplate.getForObject("http://localhost:8080/empleados/" + idEmpleado, String.class);
        ArrayList<JustificativoEntity> justificativosEmpleado= obtenerJustificativosEmpleado(rutEmpleado);

        return justificativosEmpleado;
    }

    public boolean verificarJustificativoIdMarca(int id){
        Reloj marca= restTemplate.getForObject("http://localhost:8080/clock/" + id, Reloj.class);
        return verificarJustificativo(marca);
    }

}
