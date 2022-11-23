package com.mingeso.relojservice.service;

import com.mingeso.relojservice.entity.RelojEntity;
//import com.example.demo.entities.SolicitudEntity;
import com.mingeso.relojservice.repository.RelojRepository;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class RelojService {
    @Autowired
    RelojRepository relojRepository;

    @Autowired
    FileUploadService fileUploadService;

    @Autowired
    RestTemplate restTemplate;

    public List<RelojEntity> obtenerTodas(){return relojRepository.findAll();}

    public RelojEntity guardarMarca(RelojEntity reloj) {
        return relojRepository.save(reloj);
    }

    public RelojEntity obtenerPorId(int id){
        Long idL= Long.valueOf(id);
        return relojRepository.findById(idL).orElse(null);
    }

    public ArrayList<RelojEntity> obtenerMarcasEmpleado(String rut) {
        return relojRepository.findAllByRut(rut);
    }

    public ArrayList<LocalDate> obtenerDiasTrabajables(){
            ArrayList<Date> fechasTrabajables=relojRepository.findAllDates();
            ArrayList<LocalDate> fechasTrabajablesLocalDate=new ArrayList<>();
            LocalDate fechaActual;
            for(int i=0;i<fechasTrabajables.size();i++){
                fechaActual=fechasTrabajables.get(i).toLocalDate();
                fechasTrabajablesLocalDate.add(fechaActual);
            }
            return fechasTrabajablesLocalDate;
    }


    /*public int read(MultipartFile file) {
        String linea, rut;
        LocalTime hora;
        LocalDate fecha;
        RelojEntity marcaActual;
        int id = 1;
        try {
            String carpeta = "uploads//";
            Path path = Paths.get(carpeta + file.getOriginalFilename());
            BufferedReader bufferedReader = Files.newBufferedReader(path);
            linea = bufferedReader.readLine();
            while (linea != null) {
                String[] lista = linea.split(";");
                fecha = LocalDate.parse(lista[0], DateTimeFormatter.ofPattern("yyyy/MM/dd", Locale.US));
                hora = LocalTime.parse(lista[1], DateTimeFormatter.ofPattern("HH:mm", Locale.US));
                rut = lista[2];
                marcaActual = relojRepository.save(new RelojEntity((long) id, rut, fecha, hora));
                guardarMarca(marcaActual);
                linea = bufferedReader.readLine();
                id = id + 1;
            }
            return 1;
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return 0;
        }
    }*/

    public void uploadClockFile(String filename){
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm");

            Resource data = fileUploadService.load(filename);

            BufferedReader reader = new BufferedReader(new InputStreamReader(data.getInputStream()));

            String linea = reader.readLine();

            while (linea!=null){
                String[] values = linea.split(";");
                RelojEntity marca = new RelojEntity();
                marca.setFecha(LocalDate.parse(values[0],formatter));
                marca.setHora(LocalTime.parse(values[1],formatter2));
                marca.setRut(values[2]);

                relojRepository.save(marca);
                guardarMarca(marca);

                linea = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public ArrayList<RelojEntity> obtenerMarcasPorIdEmpleado(int idEmpleado){
        String rutEmpleado= restTemplate.getForObject("http://localhost:8080/empleados/" + idEmpleado, String.class);
        ArrayList<RelojEntity> marcasEmpleado= obtenerMarcasEmpleado(rutEmpleado);

        return marcasEmpleado;
    }

}
