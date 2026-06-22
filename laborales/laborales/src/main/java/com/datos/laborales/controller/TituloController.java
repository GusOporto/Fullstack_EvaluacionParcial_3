package com.datos.laborales.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datos.laborales.DTO.TituloDTO;
import com.datos.laborales.model.Titulo;
import com.datos.laborales.service.TituloService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody; //?
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/v1/titulo")// http://localhost:8080/api/v1/titulo
@Slf4j
@Tag(name = "Titulos", description = "Operaciones relacionadas con los titulos")
public class TituloController {

    @Autowired//obligatorio para heredar titulo service
    private TituloService tituloService;

    @GetMapping
    @Operation(summary = "Obtener todas los titulos", description = "Obtiene una lista de todas los titulos")
    public ResponseEntity<List<TituloDTO>> listar(){
        List<TituloDTO> titulos = tituloService.findAll();
        
        if(titulos.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(titulos, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Crear un Titulo", description = "Crea una nuevo titulo")
    public ResponseEntity <Titulo> guardar(@Valid @RequestBody Titulo titulo){
        try{
            Titulo TituloNuevo = tituloService.save(titulo);
            return new ResponseEntity<>(TituloNuevo, HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")

    public ResponseEntity<TituloDTO> buscarPorId(@PathVariable Long id){
        try{
            TituloDTO titulo = tituloService.findById(id);
            return new ResponseEntity<>(titulo, HttpStatus.OK);
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un Titulo", description = "Actualiza un titulo existente por su ID")
    public ResponseEntity<Titulo> actualiazr (@Valid @PathVariable Long id, @RequestBody Titulo titulo){
        try{
            Titulo  edicionTitulo = tituloService.updateTitulo(id, titulo);
            return new ResponseEntity<>(edicionTitulo, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un Titulo", description = "Elimina un titulo existente por su ID")
    public ResponseEntity<?> eliminar (@PathVariable Long id){
        String resultado = tituloService.delete(id);
        if(resultado.contains("Exito")){
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PatchMapping("/{id}")
    @Operation(summary = "Editar un Titulo", description = "Editar los datos de un titulo existente por su ID")
    public ResponseEntity<Titulo> editarTitulo(@Valid @PathVariable Long id,@RequestBody Titulo titulo){
        try{
            Titulo editado = tituloService.updateTitulo(id, titulo);
            return new ResponseEntity<>(editado, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
