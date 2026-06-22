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

import com.datos.laborales.DTO.CargosDTO;
import com.datos.laborales.model.Cargos;
import com.datos.laborales.service.CargosService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody; //?
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/v1/cargos")// http://localhost:8080/api/v1/cargos
@Slf4j
@Tag(name = "Cargos", description = "Operaciones relacionadas con los cargos")
public class CargosController {

    
    @Autowired//obligatorio para heredar cargos service
    private CargosService cargosService;

    @GetMapping
    @Operation(summary = "Obtener todas los cargos", description = "Obtiene una lista de todas los cargos")
    public ResponseEntity<List<CargosDTO>> listar(){
        List<CargosDTO> cargos = cargosService.findAll();
        
        if(cargos.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(cargos, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Crear un Cargo", description = "Crea una nuevo cargo")
    public ResponseEntity <Cargos> guardar(@Valid @RequestBody Cargos cargos){
        try{
            Cargos cargoNuevo = cargosService.save(cargos);
            return new ResponseEntity<>(cargoNuevo, HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CargosDTO> buscarPorId(@PathVariable Long id){
        try{
            CargosDTO cargos = cargosService.findById(id);
            return new ResponseEntity<>(cargos, HttpStatus.OK);
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un Cargo", description = "Actualiza los datos de un cargo existente por su ID")
    public ResponseEntity<Cargos> actualizar (@Valid @PathVariable Long id, @RequestBody Cargos cargo){
        try{
            Cargos edicionCargo = cargosService.updateCargos(id, cargo);
            return new ResponseEntity<>(edicionCargo, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un Cargo", description = "Elimina un cargo existente por su ID")
    public ResponseEntity<?> eliminar (@PathVariable Long id){
        String resultado = cargosService.delete(id);
        if(resultado.contains("Exito")){ //tiene que ser tal cual el delete de service
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PatchMapping("/{id}")
    @Operation(summary = "Editar un Cargo", description = "Edita los datos de un cargo existente por su ID")
    public ResponseEntity<Cargos> editarCargo(@Valid @PathVariable Long id,@RequestBody Cargos cargo){
        try{
            Cargos editado = cargosService.updateCargos(id,cargo);
            return new ResponseEntity<>(editado, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
