package com.rrhh.rrhh.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rrhh.rrhh.DTO.TipoContratoDTO;
import com.rrhh.rrhh.model.TipoContrato;
import com.rrhh.rrhh.repository.TipoContratoRepository;

@ExtendWith(MockitoExtension.class)
public class TipoContratoServiceTest {

    @Mock
    private TipoContratoRepository tipoContratoRepository;

    @InjectMocks
    private TipoContratoService tipoContratoService;

    private TipoContrato tipoContrato;

    @BeforeEach
    void setUp() {
        tipoContrato = new TipoContrato();
        tipoContrato.setId(1L);
        tipoContrato.setNombre("Contrato indefinido");
        tipoContrato.setDescripcion("Contrato laboral sin fecha de término definida");
        tipoContrato.setModalidad("Presencial");
        tipoContrato.setJornada("Completa");
        tipoContrato.setFechaCreacion(LocalDate.of(2026, 6, 4));
    }

    @Test
    @DisplayName("Debe obtener todos los tipos de contrato como DTO")
    public void obtenerTipoContrato_deberiaRetornarListaDeDTOs() {
        // Given
        when(tipoContratoRepository.findAll()).thenReturn(List.of(tipoContrato));

        // When
        List<TipoContratoDTO> resultado = tipoContratoService.obtenerTipoContrato();

        // Then
        assertEquals(1, resultado.size());
        assertEquals(1L, resultado.get(0).getId());
        assertEquals("Contrato indefinido", resultado.get(0).getNombre());
        assertEquals("Contrato laboral sin fecha de término definida", resultado.get(0).getDescripcion());
        assertEquals("Presencial", resultado.get(0).getModalidad());
        assertEquals("Completa", resultado.get(0).getJornada());
        assertEquals(LocalDate.of(2026, 6, 4), resultado.get(0).getFechaCreacion());

        verify(tipoContratoRepository).findAll();
    }

    @Test
    @DisplayName("Debe obtener un tipo de contrato por ID")
    public void buscarPorId_deberiaRetornarTipoContratoDTO() {
        // Given
        when(tipoContratoRepository.findById(1L)).thenReturn(Optional.of(tipoContrato));

        // When
        TipoContratoDTO resultado = tipoContratoService.buscarPorId(1L);

        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Contrato indefinido", resultado.getNombre());
        assertEquals("Contrato laboral sin fecha de término definida", resultado.getDescripcion());
        assertEquals("Presencial", resultado.getModalidad());
        assertEquals("Completa", resultado.getJornada());
        assertEquals(LocalDate.of(2026, 6, 4), resultado.getFechaCreacion());

        verify(tipoContratoRepository).findById(1L);
    }

    @Test
    @DisplayName("Debe guardar un tipo de contrato y retornar su DTO")
    public void guardarTipoContrato_deberiaRetornarTipoContratoDTO() {
        // Given
        when(tipoContratoRepository.save(any(TipoContrato.class))).thenReturn(tipoContrato);

        // When
        TipoContratoDTO resultado = tipoContratoService.guardarTipoContrato(tipoContrato);

        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Contrato indefinido", resultado.getNombre());
        assertEquals("Contrato laboral sin fecha de término definida", resultado.getDescripcion());
        assertEquals("Presencial", resultado.getModalidad());
        assertEquals("Completa", resultado.getJornada());
        assertEquals(LocalDate.of(2026, 6, 4), resultado.getFechaCreacion());

        verify(tipoContratoRepository).save(any(TipoContrato.class));
    }

    @Test
    @DisplayName("Debe actualizar un tipo de contrato existente")
    public void actualizarTipoContrato_deberiaRetornarTipoContratoDTOActualizado() {
        // Given
        TipoContrato tipoActualizado = new TipoContrato();
        tipoActualizado.setId(1L);
        tipoActualizado.setNombre("Contrato plazo fijo");
        tipoActualizado.setDescripcion("Contrato con fecha de término definida");
        tipoActualizado.setModalidad("Remoto");
        tipoActualizado.setJornada("Parcial");
        tipoActualizado.setFechaCreacion(LocalDate.of(2026, 7, 1));

        when(tipoContratoRepository.findById(1L)).thenReturn(Optional.of(tipoContrato));
        when(tipoContratoRepository.save(any(TipoContrato.class))).thenReturn(tipoActualizado);

        // When
        TipoContratoDTO resultado = tipoContratoService.actualizarTipoContrato(1L, tipoActualizado);

        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Contrato plazo fijo", resultado.getNombre());
        assertEquals("Contrato con fecha de término definida", resultado.getDescripcion());
        assertEquals("Remoto", resultado.getModalidad());
        assertEquals("Parcial", resultado.getJornada());
        assertEquals(LocalDate.of(2026, 7, 1), resultado.getFechaCreacion());

        verify(tipoContratoRepository).findById(1L);
        verify(tipoContratoRepository).save(any(TipoContrato.class));
    }

    @Test
    @DisplayName("Debe eliminar un tipo de contrato correctamente")
    public void eliminarTipoContrato_deberiaRetornarMensajeExitoso() {
        // Given
        when(tipoContratoRepository.findById(1L)).thenReturn(Optional.of(tipoContrato));

        // When
        String resultado = tipoContratoService.eliminar(1L);

        // Then
        assertEquals("El tipo de contrato 'Contrato indefinido' ha sido eliminado exitosamente.", resultado);

        verify(tipoContratoRepository).findById(1L);
        verify(tipoContratoRepository).delete(tipoContrato);
    }

    @Test
    @DisplayName("Debe retornar mensaje de error cuando el tipo de contrato no existe")
    public void eliminarTipoContrato_cuandoNoExiste_deberiaRetornarMensajeError() {
        // Given
        when(tipoContratoRepository.findById(99L)).thenReturn(Optional.empty());

        // When
        String resultado = tipoContratoService.eliminar(99L);

        // Then
        assertEquals("¡Imposible eliminar! El tipo de contrato con ID99no existe.", resultado);

        verify(tipoContratoRepository).findById(99L);
        verify(tipoContratoRepository, never()).delete(any(TipoContrato.class));

    }

    @Test
    @DisplayName("Debe lanzar excepción cuando el tipo de contrato no existe al buscar por ID")
    public void buscarPorId_cuandoNoExiste_deberiaLanzarExcepcion() {
        // Given
        when(tipoContratoRepository.findById(99L)).thenReturn(Optional.empty());

        // When / Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            tipoContratoService.buscarPorId(99L);
        });

        assertEquals("Tipo de contrato no encontrado!", exception.getMessage());

        verify(tipoContratoRepository).findById(99L);
    }
}