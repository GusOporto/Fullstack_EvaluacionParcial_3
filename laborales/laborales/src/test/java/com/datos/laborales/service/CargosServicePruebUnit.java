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

import com.datos.laborales.model.Cargos;
import com.datos.laborales.repository.CargosRepository;

@ExtendWith(MockitoExtension.class)
class CargosServicePruebUnit {

    @Mock
    private CargosRepository cargosRepository;

    @InjectMocks
    private CargosService cargosService;

    private Cargos cargo;

    @BeforeEach
    void setUp() {
        cargo = new Cargos();
        cargo.setId(1L);
        cargo.setNombre("Cajero");
        cargo.setDescripcion("Atiende la caja registradora");
        cargo.setSueldo(500000);
    }

    @Test
    void testFindAll() {
        when(cargosRepository.findAll()).thenReturn(List.of(cargo));
        var resultado = cargosService.findAll();
        assertEquals(1, resultado.size());
        assertEquals("Cajero", resultado.get(0).getNombre());
    }

    @Test
    void testFindByIdExiste() {
        when(cargosRepository.findById(1L)).thenReturn(Optional.of(cargo));
        var resultado = cargosService.findById(1L);
        assertNotNull(resultado);
        assertEquals("Cajero", resultado.getNombre());
    }

    @Test
    void testFindByIdNoExiste() {
        when(cargosRepository.findById(99L)).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class, () -> cargosService.findById(99L));
        assertEquals("Cargo NO encontrado.", ex.getMessage());
    }

    @Test
    void testSave() {
        when(cargosRepository.save(cargo)).thenReturn(cargo);
        var resultado = cargosService.save(cargo);
        assertEquals("Cajero", resultado.getNombre());
        verify(cargosRepository).save(cargo);
    }

    @Test
    void testDeleteExiste() {
        when(cargosRepository.findById(1L)).thenReturn(Optional.of(cargo));
        String msg = cargosService.delete(1L);
        assertTrue(msg.contains("Exito"));
        verify(cargosRepository).delete(cargo);
    }

    @Test
    void testDeleteNoExiste() {
        when(cargosRepository.findById(99L)).thenReturn(Optional.empty());
        String msg = cargosService.delete(99L);
        assertTrue(msg.contains("99"));
        verify(cargosRepository, never()).delete(any(Cargos.class));
    }

    @Test
    void testUpdate() {
        Cargos cambios = new Cargos();
        cambios.setNombre("Bodeguero");
        cambios.setSueldo(600000);

        when(cargosRepository.findById(1L)).thenReturn(Optional.of(cargo));
        when(cargosRepository.save(any(Cargos.class))).thenAnswer(inv -> inv.getArgument(0));

        var resultado = cargosService.updateCargos(1L, cambios);
        assertEquals("Bodeguero", resultado.getNombre());
        assertEquals(600000, resultado.getSueldo());
    }

    @Test
    void testUpdateNoExiste() {
        when(cargosRepository.findById(99L)).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class, () -> cargosService.updateCargos(99L, cargo));
        assertEquals("El Cargo no existe.", ex.getMessage());
    }
}
