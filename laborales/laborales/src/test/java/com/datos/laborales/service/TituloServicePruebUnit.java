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

import com.datos.laborales.model.Titulo;
import com.datos.laborales.repository.TituloRepository;

@ExtendWith(MockitoExtension.class)
class TituloServicePruebUnit {

    @Mock
    private TituloRepository tituloRepository;

    @InjectMocks
    private TituloService tituloService;

    private Titulo titulo;

    @BeforeEach
    void setUp() {
        titulo = new Titulo();
        titulo.setId(1L);
        titulo.setNombre("Técnico en Administración");
        titulo.setInstitucion("DuocUC");
        titulo.setFechaObtencion(LocalDate.of(2024, 12, 15));
    }

    @Test
    void testFindAll() {
        when(tituloRepository.findAll()).thenReturn(List.of(titulo));
        var resultado = tituloService.findAll();
        assertEquals(1, resultado.size());
        assertEquals("DuocUC", resultado.get(0).getInstitucion());
    }

    @Test
    void testFindByIdExiste() {
        when(tituloRepository.findById(1L)).thenReturn(Optional.of(titulo));
        var resultado = tituloService.findById(1L);
        assertNotNull(resultado);
        assertEquals("Técnico en Administración", resultado.getNombre());
    }

    @Test
    void testFindByIdNoExiste() {
        when(tituloRepository.findById(99L)).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class, () -> tituloService.findById(99L));
        assertEquals("Titulo NO encontrado.", ex.getMessage());
    }

    @Test
    void testSave() {
        when(tituloRepository.save(titulo)).thenReturn(titulo);
        var resultado = tituloService.save(titulo);
        assertEquals("DuocUC", resultado.getInstitucion());
        verify(tituloRepository).save(titulo);
    }

    @Test
    void testDeleteExiste() {
        when(tituloRepository.findById(1L)).thenReturn(Optional.of(titulo));
        String msg = tituloService.delete(1L);
        assertTrue(msg.contains("Exito"));
        verify(tituloRepository).delete(titulo);
    }

    @Test
    void testDeleteNoExiste() {
        when(tituloRepository.findById(99L)).thenReturn(Optional.empty());
        String msg = tituloService.delete(99L);
        assertTrue(msg.contains("99"));
        verify(tituloRepository, never()).delete(any(Titulo.class));
    }

    @Test
    void testUpdateSoloCamposNoNulos() {
        Titulo cambios = new Titulo();
        cambios.setInstitucion("INACAP");

        when(tituloRepository.findById(1L)).thenReturn(Optional.of(titulo));
        when(tituloRepository.save(any(Titulo.class))).thenAnswer(inv -> inv.getArgument(0));

        var resultado = tituloService.updateTitulo(1L, cambios);
        assertEquals("Técnico en Administración", resultado.getNombre()); // no cambió
        assertEquals("INACAP", resultado.getInstitucion());
    }

    @Test
    void testUpdateNoExiste() {
        when(tituloRepository.findById(99L)).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class, () -> tituloService.updateTitulo(99L, titulo));
        assertEquals("El Titulo no existe.", ex.getMessage());
    }
}
