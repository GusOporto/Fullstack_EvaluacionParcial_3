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

import com.datos.laborales.DTO.CurriculumDTO;
import com.datos.laborales.model.Curriculum;
import com.datos.laborales.service.CurriculumService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody; //?
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/v1/curriculum")// http://localhost:8080/api/v1/curriculum
@Slf4j
@Tag(name = "Curriculums", description = "Operaciones relacionadas con los curriculums")
public class CurriculumController {

    @Autowired//obligatorio para heredar curriculum service
    private CurriculumService curriculumService;

    @GetMapping
    @Operation(summary = "Obtener todas los curriculums", description = "Obtiene una lista de todas los curriculums")
    public ResponseEntity<List<CurriculumDTO>> listar(){
        List<CurriculumDTO> curriculums = curriculumService.findAll();
        
        if(curriculums.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(curriculums, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Crear un Curriculum", description = "Crea una nuevo curriculum")
    public ResponseEntity <Curriculum> guardar(@Valid @RequestBody Curriculum curriculum){
        try{
            Curriculum curriculumNuevo = curriculumService.save(curriculum);
            return new ResponseEntity<>(curriculumNuevo, HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un Curriculum por ID", description = "Obtiene un curriculum específico por su ID")
    public ResponseEntity<CurriculumDTO> buscarPorId(@PathVariable Long id){
        try{
            CurriculumDTO curriculum = curriculumService.findById(id);
            return new ResponseEntity<>(curriculum, HttpStatus.OK);
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un Curriculum", description = "Actualiza los datos de un curriculum existente por su ID")
    public ResponseEntity<Curriculum> actualizar (@Valid @PathVariable Long id, @RequestBody Curriculum curriculum){
        try{
            Curriculum  edicionCurriculum = curriculumService.updateCurriculum(id, curriculum);
            return new ResponseEntity<>(edicionCurriculum, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un Curriculum", description = "Elimina un curriculum existente por su ID")
    public ResponseEntity<?> eliminar (@PathVariable Long id){
        String resultado = curriculumService.delete(id);
        if(resultado.contains("Exito")){
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PatchMapping("/{id}")
    @Operation(summary = "Editar un Curriculum", description = "Editar los datos un curriculum existente por su ID")
    public ResponseEntity<Curriculum> editarCurriculum(@Valid @PathVariable Long id,@RequestBody Curriculum curriculum){
        try{
            Curriculum editado = curriculumService.updateCurriculum(id, curriculum);
            return new ResponseEntity<>(editado, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
