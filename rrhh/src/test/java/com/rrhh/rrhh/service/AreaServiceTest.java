package com.rrhh.rrhh.service;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.rrhh.rrhh.DTO.AreaDTO;
import com.rrhh.rrhh.model.Area;
import com.rrhh.rrhh.repository.AreaRepository;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AreaServiceTest {

    @Mock
    private AreaRepository areaRepository;

    @InjectMocks
    private AreaService areaService;

    private Area area;

    @BeforeEach
    public void setUp() {
        area = new Area();
        area.setId(1L);
        area.setNombre("Recursos Humanos");
        area.setDescripcion("Área encargada de la gestión del personal");
    }

    @Test
    @DisplayName("Debe obtener todas las áreas como DTOs")
    public void ObteenrAreas_debeRetornarListaDeAreaDTOs() {
        // Given
        when(areaRepository.findAll()).thenReturn(List.of(area));
        // When
        List<AreaDTO> resultado = areaService.obtenerAreas();
        // Then
        assertEquals(1, resultado.size());
        assertEquals(1L, resultado.get(0).getId());
        assertEquals("Recursos Humanos", resultado.get(0).getNombre());
        assertEquals("Área encargada de la gestión del personal", resultado.get(0).getDescripcion());

        verify(areaRepository).findAll();
    }

    @Test
    @DisplayName("Debe obtener un área por su ID")
    public void buscarPorId_deberiaRetornarAreaDTO() {

        // Given
        when(areaRepository.findById(1L)).thenReturn(java.util.Optional.of(area));
        // When
        AreaDTO resultado = areaService.buscarPorId(1L);
        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Recursos Humanos", resultado.getNombre());
        assertEquals("Área encargada de la gestión del personal", resultado.getDescripcion());

        verify(areaRepository).findById(1L);
    }

    @Test
    @DisplayName("Debe guardar un área y retornar su DTO")
    public void guardarArea_deberiaRetornarAreaDTO() {
        // Given
        when(areaRepository.save(any(Area.class))).thenReturn(area);
        // When
        AreaDTO resultado = areaService.guardarArea(area);
        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Recursos Humanos", resultado.getNombre());
        assertEquals("Área encargada de la gestión del personal", resultado.getDescripcion());

        verify(areaRepository).save(any(Area.class));
    }

    @Test
    @DisplayName("Debe actualizar un área existente ")
    public void actualizarArea_deberiaRetornarAreaDTOActualizada() {

        // Given
        Area areaActualizada = new Area();
        areaActualizada.setId(1L);
        areaActualizada.setNombre("Finanzas");
        areaActualizada.setDescripcion("Área encargada de la gestión financiera");

        when(areaRepository.findById(1L)).thenReturn(java.util.Optional.of(area));
        when(areaRepository.save(any(Area.class))).thenReturn(areaActualizada);
        // When
        AreaDTO resultado = areaService.actualizarArea(1L, areaActualizada);

        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Finanzas", resultado.getNombre());
        assertEquals("Área encargada de la gestión financiera", resultado.getDescripcion());

        verify(areaRepository).findById(1L);
        verify(areaRepository).save(any(Area.class));
    }

    @Test
    @DisplayName("Debe eliminar un área existente ")
    public void eliminar_deberiaRetornarMensajeExitoso() {
        // Given
        when(areaRepository.findById(1L)).thenReturn(java.util.Optional.of(area));

        // When
        String resultado = areaService.eliminar(1L);
        // Then
        assertEquals("El área 'Recursos Humanos' ha sido eliminada exitosamente.", resultado);

        verify(areaRepository).findById(1L);
        verify(areaRepository).delete(area);
    }

    @Test
    @DisplayName("Debe retornar mensaje de error al intentar eliminar un área inexistente")
    public void eliminar_deberiaRetornarMensajeError() {
        // Given
        when(areaRepository.findById(99L)).thenReturn(java.util.Optional.empty());
        // When
        String resultado = areaService.eliminar(99L);
        // Then
        assertEquals("¡Imposible eliminar! El área con ID 99 no existe.", resultado);

        verify(areaRepository).findById(99L);
        verify(areaRepository, never()).delete(any(Area.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando el área no existe al actualizar")
    public void actualizarArea_cuandoNoExiste_deberiaLanzarExcepcion() {
        // Given
        Area areaActualizada = new Area();
        areaActualizada.setNombre("Finanzas");

        when(areaRepository.findById(99L)).thenReturn(java.util.Optional.empty());

        // When / Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            areaService.actualizarArea(99L, areaActualizada);
        });

        assertEquals("¡El área no existe en los registros!", exception.getMessage());

        verify(areaRepository).findById(99L);
        verify(areaRepository, never()).save(any(Area.class));
    }

}
