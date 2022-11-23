package com.mingeso.planillaservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mingeso.planillaservice.entity.PlanillaEntity;
import com.mingeso.planillaservice.model.Authorization;
import com.mingeso.planillaservice.model.Empleado;
import com.mingeso.planillaservice.model.Justificativo;
import com.mingeso.planillaservice.model.Reloj;
import com.mingeso.planillaservice.repository.PlanillaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlanillaService {

    @Autowired
    PlanillaRepository planillaRepository;

    @Autowired
    RestTemplate restTemplate;

    public List<PlanillaEntity> obtenerSueldos() {return planillaRepository.findAll();}

    public PlanillaEntity obtenerEmpleadoPorRut(String rut) {return planillaRepository.findById(rut).orElse(null);}

    public PlanillaEntity guardarSueldo(PlanillaEntity sueldo){return planillaRepository.save(sueldo);}

    public int obtenerHorasExtra(ArrayList<Reloj> marcasEmpleado, ArrayList<Authorization> horasPermitidasEmpleado){
        int i,j;
        int horasExtra;
        int horasExtraTotales=0;
        LocalDate fecha;
        Reloj marcaSalida=null;
        LocalTime horarioSalida= LocalTime.of(18,0,0);
        Duration duracion;
        if(marcasEmpleado!=null&&horasPermitidasEmpleado!=null) {
            for (i = 0; i < horasPermitidasEmpleado.size(); i++) {
                fecha = horasPermitidasEmpleado.get(i).getFechaHorasExtra();
                for (j = 0; j < marcasEmpleado.size() - 1; j++) {
                    if (marcasEmpleado.get(j).getFecha().isEqual(fecha)) {
                        if (marcasEmpleado.get(j + 1).getFecha().isEqual(fecha)) {
                            marcaSalida = marcasEmpleado.get(j + 1);
                            j = marcasEmpleado.size();
                        }
                    }
                }
                if (marcaSalida != null) {
                    duracion = Duration.between(horarioSalida, marcaSalida.getHora());
                    horasExtra = (int) (duracion.toHours());
                    horasExtraTotales = horasExtraTotales + horasExtra;
                }
            }
        }
        return horasExtraTotales;
    }

    public int sueldoHorasExtra(Empleado empleado){
        int valorHora=0;
        if (empleado.getCategoria().equals("A")){
            valorHora=25000;
        } else if (empleado.getCategoria().equals("B")) {
            valorHora=20000;
        }else if (empleado.getCategoria().equals("C")){
            valorHora=10000;
        }
        return valorHora;
    }

    public int obtenerSueldoFijo(Empleado empleado){
        int sueldo = 0;
        if (empleado.getCategoria().equals("A")){
            sueldo=1700000;
        } else if (empleado.getCategoria().equals("B")) {
            sueldo=1200000;
        }else if (empleado.getCategoria().equals("C")){
            sueldo=800000;
        }
        return sueldo;
    }

    public int obtenerAniosAntiguedad(Empleado empleado){
        LocalDate fechaActual= LocalDate.now();
        LocalDate fecha= LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(empleado.getFecha_in()));
        //LocalDate fechaIngreso= empleado.getFecha_in().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Period periodo= Period.between(fecha,fechaActual);
        return periodo.getYears();
    }

    public double calcularBonificaciones(Empleado empleado){
        int anios=obtenerAniosAntiguedad(empleado);
        double bonificacion=0;
        if(anios>=25){
            bonificacion=obtenerSueldoFijo(empleado)*0.17;
        }else if(anios>=20){
            bonificacion=obtenerSueldoFijo(empleado)*0.14;
        }else if(anios>=15){
            bonificacion=obtenerSueldoFijo(empleado)*0.11;
        }else if(anios>=10){
            bonificacion=obtenerSueldoFijo(empleado)*0.08;
        }else if(anios>=5){
            bonificacion=obtenerSueldoFijo(empleado)*0.05;
        }
        return bonificacion;
    }

    public double bonoHorasExtra(Empleado empleado){
        //int horasExtra=obtenerHorasExtra(relojService.obtenerMarcasEmpleado(empleado.getRut()),solicitudService.obtenerPorRut(empleado.getRut()));
        int idEmpleado=Long.valueOf(empleado.getId_empleado()).intValue();
        ArrayList<Reloj> marcasEmpleado= marcasFindById(idEmpleado);
        ArrayList<Authorization> autorizacionesEmpleado= autorizacionesFindById(idEmpleado);
        int horasExtra=obtenerHorasExtra(marcasEmpleado,autorizacionesEmpleado);
        return sueldoHorasExtra(empleado)*horasExtra;
    }

    public double calcularDescuentosAtraso(Reloj marca){
        LocalTime horaEntrada= LocalTime.of(8,0,0);
        long minutos=horaEntrada.until(marca.getHora(), ChronoUnit.MINUTES);
        double descuento=0;
        if (minutos>70){
            if(restTemplate.getForObject("http://localhost:8080/justificativos/marca/" + marca.getId_reloj(), Boolean.class) == false) {
                descuento = 0.15;
            }
        }else if (minutos>45){
            descuento=0.06;
        }else if (minutos>25){
            descuento=0.03;
        }else if (minutos>10){
            descuento=0.01;
        }
        return descuento;
    }

    public ArrayList<Reloj> obtenerMarcasEntradaAtrasada(ArrayList<Reloj> marcasEmpleado){
        int j;
        LocalDate fecha;
        Reloj marcaEntrada;
        LocalTime horarioEntrada= LocalTime.of(8,0,0);
        ArrayList<Reloj> atrasos= new ArrayList<>();
        if(marcasEmpleado!=null) {
            for (j = 0; j < marcasEmpleado.size() - 1; j++) {
                fecha = marcasEmpleado.get(j).getFecha();
                if (marcasEmpleado.get(j + 1).getFecha().isEqual(fecha)) {
                    marcaEntrada = marcasEmpleado.get(j);
                    if (marcaEntrada.getHora().isAfter(horarioEntrada)) {
                        atrasos.add(marcaEntrada);
                    }
                }
            }
        }
        return atrasos;
    }

    public double calcularDescuentoAtrasosTotal(Empleado empleado){
        int i;
        double descuentoTotal=0;
        int idEmpleado = Long.valueOf(empleado.getId_empleado()).intValue();
        ArrayList<Reloj> marcasEmpleado= marcasFindById(idEmpleado);
        ArrayList<Reloj> marcasConAtrasos=obtenerMarcasEntradaAtrasada(marcasEmpleado);
        for (i=0;i<marcasConAtrasos.size();i++){
            descuentoTotal=descuentoTotal+calcularDescuentosAtraso(marcasConAtrasos.get(i));
        }
        return descuentoTotal;
    }

    public double calcularDescuentoFaltas(Empleado empleado){
        int idEmpleado= Long.valueOf(empleado.getId_empleado()).intValue();
        ArrayList<Reloj> marcasEmpleado= marcasFindById(idEmpleado);
        ArrayList<LocalDate> diasDeTrabajo= diasTrabajables();
        ArrayList<Justificativo> justificativosEmpleado= justificativoFindById(idEmpleado);
        int esta=0;
        int index=0;
        boolean flag=false;
        LocalDate fecha;
        int diasFaltados=0;
        if(marcasEmpleado!=null&&justificativosEmpleado!=null) {
            for (int i = 0; i < diasDeTrabajo.size(); i++) {
                for (int j = 0; j < marcasEmpleado.size(); j++) {
                    if (marcasEmpleado.get(j).getFecha().isEqual(diasDeTrabajo.get(i))) {
                        esta = 1;
                        j = marcasEmpleado.size();
                    }
                }
                if (esta == 0) {
                    fecha = diasDeTrabajo.get(i);
                    //restTemplate.getForObject("http://justificativo-service/justificativos/inasistencia/" + empleado.getId_empleado(), Boolean.class);
                    int k;
                    for (k = 0; k < justificativosEmpleado.size(); k++) {
                        if (fecha.isEqual(justificativosEmpleado.get(k).getFecha())) {
                            flag = true;
                        }
                    }
                    if (!flag) {
                        diasFaltados++;
                    }
                    flag = false;
                }
                esta = 0;
            }
        }
        return diasFaltados*0.15;
    }

    public double calcularDescuentosTotal(Empleado empleado, int sueldoFijoEmpleado){
        double descuentos = (calcularDescuentoAtrasosTotal(empleado) + calcularDescuentoFaltas(empleado)) * sueldoFijoEmpleado;
        return descuentos;
    }

    public double calcularSueldoBruto(Empleado empleado){
        int sueldoFijo= obtenerSueldoFijo(empleado);
        double bonificaciones=calcularBonificaciones(empleado);
        double bonoHoras=bonoHorasExtra(empleado);
        double descuentos=calcularDescuentosTotal(empleado,sueldoFijo);
        return sueldoFijo+bonificaciones+bonoHoras-descuentos;
    }


    public double cotizacionPrevisional(double sueldoBruto){
        return sueldoBruto*0.1;
    }

    public double cotizacionSalud(double sueldoBruto){
        return sueldoBruto*0.08;
    }

    public double calcularSueldoFinal(Empleado empleado){
        double sueldoBruto= calcularSueldoBruto(empleado);
        return sueldoBruto - (cotizacionPrevisional(sueldoBruto)) - (cotizacionSalud(sueldoBruto));
    }

    public void calcularPlanillaEmpleado(Empleado empleado){
        PlanillaEntity nuevoSueldo;
        String rut;
        String nombres;
        String apellidos;
        String categoria;
        int sueldoFijo, sueldoBruto,sueldoFinal, bonoHorasExtra,bonoAnios,descuentos,cotPrevisional,cotSalud;
        int aniosDeAntiguedad;
        rut= empleado.getRut();
        nombres= empleado.getNombres();
        apellidos= empleado.getApellidos();
        categoria=empleado.getCategoria();
        aniosDeAntiguedad=obtenerAniosAntiguedad(empleado);
        sueldoFijo=obtenerSueldoFijo(empleado);
        bonoAnios=(int)calcularBonificaciones(empleado);
        bonoHorasExtra=(int)bonoHorasExtra(empleado);
        descuentos=(int)calcularDescuentosTotal(empleado,sueldoFijo);
        sueldoBruto=(int)calcularSueldoBruto(empleado);
        cotPrevisional=(int)cotizacionPrevisional(sueldoBruto);
        cotSalud=(int)cotizacionSalud(sueldoBruto);
        sueldoFinal=(int)calcularSueldoFinal(empleado);
        nuevoSueldo=planillaRepository.save(new PlanillaEntity(rut,apellidos,nombres,categoria,aniosDeAntiguedad,sueldoFijo,bonoAnios,bonoHorasExtra,descuentos,sueldoBruto,cotPrevisional,cotSalud,sueldoFinal));
        guardarSueldo(nuevoSueldo);
    }

    public void calcularPlanillaEmpleados(){
        List<Empleado> empleados= obtenerEmpleados();
        for(int i=0;i<empleados.size();i++){
            calcularPlanillaEmpleado(empleados.get(i));
        }
    }

    public List<Empleado> obtenerEmpleados(){
        ResponseEntity<Object[]> response = restTemplate.getForEntity("http://localhost:8080/empleados/", Object[].class);
        Object[] records = null;
        if(response.getBody()!=null) {
            records=response.getBody();
        }
        if(records == null){
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return  Arrays.stream(records)
                .map(empleado -> mapper.convertValue(empleado, Empleado.class))
                .collect(Collectors.toList());
    }

    public ArrayList<Justificativo> justificativoFindById(int id){
        ResponseEntity<Object[]> response  = restTemplate.getForEntity("http://localhost:8080/justificativos/empleado/" + id,  Object[].class);
        Object[] records = null;
        if(response.getBody()!=null) {
            records=response.getBody();
        }
        if(records == null){
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return (ArrayList<Justificativo>) Arrays.stream(records)
                .map(empleado -> mapper.convertValue(empleado, Justificativo.class))
                .collect(Collectors.toList());
    }
    public ArrayList<Authorization> autorizacionesFindById(int id){
        ResponseEntity<Object[]> response  = restTemplate.getForEntity("http://localhost:8080/autorizaciones/empleado/" + id, Object[].class);
        Object[] records = null;
        if(response.getBody()!=null) {
            records=response.getBody();
        }
        if(records == null){
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return (ArrayList<Authorization>) Arrays.stream(records)
                .map(empleado -> mapper.convertValue(empleado, Authorization.class))
                .collect(Collectors.toList());
    }
    public ArrayList<Reloj> marcasFindById(int id) {
        ResponseEntity<Object[]> response = restTemplate.getForEntity("http://localhost:8080/clock/empleado/" + id, Object[].class);
        Object[] records = null;
        if(response.getBody()!=null) {
            records=response.getBody();
        }
        if (records == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return (ArrayList<Reloj>) Arrays.stream(records)
                .map(empleado -> mapper.convertValue(empleado, Reloj.class))
                .collect(Collectors.toList());
    }

    public ArrayList<LocalDate> diasTrabajables() {
        ResponseEntity<Object[]> response = restTemplate.getForEntity("http://localhost:8080/clock/dias", Object[].class);
        Object[] records=null;
        if(response.getBody()!=null) {
            records = response.getBody();
        }
        if (records == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return (ArrayList<LocalDate>) Arrays.stream(records)
                .map(empleado -> mapper.convertValue(empleado, LocalDate.class))
                .collect(Collectors.toList());
    }

}
