package com.mingeso.relojservice.controller;

import com.mingeso.relojservice.service.FileUploadService;
import com.mingeso.relojservice.service.RelojService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/schedules/files")
public class FileUploadController {
    @Autowired
    FileUploadService fileUploadService;

    @Autowired
    RelojService relojService;

    @PostMapping("/clocks/upload")
    public ResponseEntity<String> uploadClockFile(@RequestParam("file") MultipartFile file){
        try{
            String filename= file.getOriginalFilename();
            System.out.println("Nombre de archivo: " + filename);
            fileUploadService.save(file);
            relojService.uploadClockFile(file.getOriginalFilename());
            return ResponseEntity.ok().body("Archivo se subio correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
