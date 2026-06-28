package com.datos.laborales.service;

import java.time.LocalDate;
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

import com.datos.laborales.model.Evaluaciones;
import com.datos.laborales.repository.EvaluacionesRepository;

@ExtendWith(MockitoExtension.class)
class EvaluacionesServicePruebUnit {

    @Mock
    private EvaluacionesRepository evaluacionesRepository;

    @InjectMocks
    private EvaluacionesService evaluacionesService;

    private Evaluaciones evaluacion;

    @BeforeEach
    void setUp() {
        evaluacion = new Evaluaciones();
        evaluacion.setId(1L);
        evaluacion.setFechaEvaluacion(LocalDate.of(2025, 6, 1));
        evaluacion.setPeriodo("Semestre 1 2025");
        evaluacion.setObservaciones("Buen desempeño general");
        evaluacion.setFortalezas("Puntualidad");
        evaluacion.setDebilidades("Comunicación");
        evaluacion.setPorMejorar("Presentaciones orales");
    }

    @Test
    void testFindAll() {
        when(evaluacionesRepository.findAll()).thenReturn(List.of(evaluacion));
        var resultado = evaluacionesService.findAll();
        assertEquals(1, resultado.size());
        assertEquals("Semestre 1 2025", resultado.get(0).getPeriodo());
    }

    @Test
    void testFindByIdExiste() {
        when(evaluacionesRepository.findById(1L)).thenReturn(Optional.of(evaluacion));
        var resultado = evaluacionesService.findById(1L);
        assertNotNull(resultado);
        assertEquals("Buen desempeño general", resultado.getObservaciones());
    }

    @Test
    void testFindByIdNoExiste() {
        when(evaluacionesRepository.findById(99L)).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class, () -> evaluacionesService.findById(99L));
        assertEquals("Evaluacion NO encontrada.", ex.getMessage());
    }

    @Test
    void testSave() {
        when(evaluacionesRepository.save(evaluacion)).thenReturn(evaluacion);
        var resultado = evaluacionesService.save(evaluacion);
        assertEquals("Semestre 1 2025", resultado.getPeriodo());
        verify(evaluacionesRepository).save(evaluacion);
    }

    @Test
    void testDeleteExiste() {
        when(evaluacionesRepository.findById(1L)).thenReturn(Optional.of(evaluacion));
        String msg = evaluacionesService.delete(1L);
        assertTrue(msg.contains("Exito"));
        verify(evaluacionesRepository).delete(evaluacion);
    }

    @Test
    void testDeleteNoExiste() {
        when(evaluacionesRepository.findById(99L)).thenReturn(Optional.empty());
        String msg = evaluacionesService.delete(99L);
        assertTrue(msg.contains("99"));
        verify(evaluacionesRepository, never()).delete(any(Evaluaciones.class));
    }

    @Test
    void testUpdateSoloCamposNoNulos() {
        Evaluaciones cambios = new Evaluaciones();
        cambios.setPeriodo("Semestre 2 2025");

        when(evaluacionesRepository.findById(1L)).thenReturn(Optional.of(evaluacion));
        when(evaluacionesRepository.save(any(Evaluaciones.class))).thenAnswer(inv -> inv.getArgument(0));

        var resultado = evaluacionesService.updateEvaluaciones(1L, cambios);
        assertEquals("Semestre 2 2025", resultado.getPeriodo());
        assertEquals("Buen desempeño general", resultado.getObservaciones()); // no cambió
    }

    @Test
    void testUpdateNoExiste() {
        when(evaluacionesRepository.findById(99L)).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class, () -> evaluacionesService.updateEvaluaciones(99L, evaluacion));
        assertEquals("La evaluacion no existe.", ex.getMessage());
    }
}
