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

import com.datos.laborales.DTO.EvaluacionesDTO;
import com.datos.laborales.model.Evaluaciones;
import com.datos.laborales.service.EvaluacionesService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody; //?
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/v1/evaluaciones")// http://localhost:8080/api/v1/evaluaciones
@Slf4j
@Tag(name = "Evaluaciones", description = "Operaciones relacionadas con las evaluaciones")
public class EvaluacionesController {

    @Autowired//obligatorio para heredar evaluaciones service
    private EvaluacionesService evaluacionesService;

    @GetMapping
    @Operation(summary = "Obtener todas las Evaluaciones", description = "Obtiene una lista de todas las evaluaciones")
    public ResponseEntity<List<EvaluacionesDTO>> listar(){
        List<EvaluacionesDTO> evaluaciones = evaluacionesService.findAll();
        
        if(evaluaciones.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(evaluaciones, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Crear una Evaluacion", description = "Crea una nueva evaluacion")
    public ResponseEntity <Evaluaciones> guardar(@Valid @RequestBody Evaluaciones evaluaciones){
        try{
            Evaluaciones evaluacionNueva = evaluacionesService.save(evaluaciones);
            return new ResponseEntity<>(evaluacionNueva, HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una Evaluacion por ID", description = "Obtiene una evaluacion específica por su ID")
    public ResponseEntity<EvaluacionesDTO> buscarPorId(@PathVariable Long id){
        try{
            EvaluacionesDTO evaluaciones = evaluacionesService.findById(id);
            return new ResponseEntity<>(evaluaciones, HttpStatus.OK);
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una Evaluacion", description = "Actualiza los datos de una evaluacion existente por su ID")
    public ResponseEntity<Evaluaciones> actualizar (@Valid @PathVariable Long id, @RequestBody Evaluaciones evaluaciones){
        try{
            Evaluaciones  edicionEvaluaciones = evaluacionesService.updateEvaluaciones(id, evaluaciones);
            return new ResponseEntity<>(edicionEvaluaciones, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una Evaluacion", description = "Elimina una evaluacion existente por su ID")
    public ResponseEntity<?> eliminar (@PathVariable Long id){
        String resultado = evaluacionesService.delete(id);
        if(resultado.contains("Exito")){
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PatchMapping("/{id}")
    @Operation(summary = "Editar una Evaluacion", description = "Editar los datos de una evaluacion existente por su ID")
    public ResponseEntity<Evaluaciones> editarEvaluaciones(@Valid @PathVariable Long id,@RequestBody Evaluaciones evaluaciones){
        try{
            Evaluaciones editado = evaluacionesService.updateEvaluaciones(id, evaluaciones);
            return new ResponseEntity<>(editado, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
