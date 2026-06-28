package com.datos.laborales.service;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.datos.laborales.model.Curriculum;
import com.datos.laborales.repository.CurriculumRepository;

@ExtendWith(MockitoExtension.class)
class CurriculumServicePruebUnit {

    @Mock
    private CurriculumRepository curriculumRepository;

    @InjectMocks
    private CurriculumService curriculumService;

    private Curriculum curriculum;

    @BeforeEach
    void setUp() {
        curriculum = new Curriculum();
        curriculum.setId(1L);
        curriculum.setNombre("Juan Pérez");
        curriculum.setEdad(28);
        curriculum.setExperienciaLaboral("3 años en retail");
        curriculum.setCertificaciones("Excel avanzado");
        curriculum.setHabilidades("Trabajo en equipo");
        curriculum.setFortalezas("Puntualidad");
        curriculum.setIdiomas("Español");
    }

    @Test
    void testFindAll() {
        when(curriculumRepository.findAll()).thenReturn(List.of(curriculum));
        var resultado = curriculumService.findAll();
        assertEquals(1, resultado.size());
        assertEquals("Juan Pérez", resultado.get(0).getNombre());
    }

    @Test
    void testFindByIdExiste() {
        when(curriculumRepository.findById(1L)).thenReturn(Optional.of(curriculum));
        var resultado = curriculumService.findById(1L);
        assertNotNull(resultado);
        assertEquals(28, resultado.getEdad());
    }

    @Test
    void testFindByIdNoExiste() {
        when(curriculumRepository.findById(99L)).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class, () -> curriculumService.findById(99L));
        assertEquals("Curriculum NO encontrado.", ex.getMessage());
    }

    @Test
    void testSave() {
        when(curriculumRepository.save(curriculum)).thenReturn(curriculum);
        var resultado = curriculumService.save(curriculum);
        assertEquals("Juan Pérez", resultado.getNombre());
        verify(curriculumRepository).save(curriculum);
    }

    @Test
    void testDeleteExiste() {
        when(curriculumRepository.findById(1L)).thenReturn(Optional.of(curriculum));
        String msg = curriculumService.delete(1L);
        assertTrue(msg.contains("Exito"));
        verify(curriculumRepository).delete(curriculum);
    }

    @Test
    void testDeleteNoExiste() {
        when(curriculumRepository.findById(99L)).thenReturn(Optional.empty());
        String msg = curriculumService.delete(99L);
        assertTrue(msg.contains("99"));
        verify(curriculumRepository, never()).delete(any(Curriculum.class));
    }

    @Test
    void testUpdateSoloCamposNoNulos() {
        Curriculum cambios = new Curriculum();
        cambios.setExperienciaLaboral("5 años en logística");

        when(curriculumRepository.findById(1L)).thenReturn(Optional.of(curriculum));
        when(curriculumRepository.save(any(Curriculum.class))).thenAnswer(inv -> inv.getArgument(0));

        var resultado = curriculumService.updateCurriculum(1L, cambios);
        assertEquals("Juan Pérez", resultado.getNombre()); // no cambió
        assertEquals("5 años en logística", resultado.getExperienciaLaboral());
    }

    @Test
    void testUpdateNoExiste() {
        when(curriculumRepository.findById(99L)).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class, () -> curriculumService.updateCurriculum(99L, curriculum));
        assertEquals("el Curriculum no existe.", ex.getMessage());
    }
}
