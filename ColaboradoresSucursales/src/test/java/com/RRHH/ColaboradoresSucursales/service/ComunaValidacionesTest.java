package com.RRHH.ColaboradoresSucursales.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.RRHH.ColaboradoresSucursales.DTO.ComunaDTO;
import com.RRHH.ColaboradoresSucursales.model.Colaborador;
import com.RRHH.ColaboradoresSucursales.model.Comuna;
import com.RRHH.ColaboradoresSucursales.model.Region;
import com.RRHH.ColaboradoresSucursales.model.Sucursal;
import com.RRHH.ColaboradoresSucursales.repository.ColaboradorRepository;
import com.RRHH.ColaboradoresSucursales.repository.SucursalRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("ComunaValidaciones - Pruebas Unitarias")
class ComunaValidacionesTest {

    @Mock
    private SucursalRepository sucursalRepository;

    @Mock
    private ColaboradorRepository colaboradorRepository;

    @InjectMocks
    private ComunaValidaciones comunaValidaciones;

    private Comuna comunaCompleta;
    private Region regionBase;

    @BeforeEach
    void setUp() {
        regionBase = new Region();
        regionBase.setId(1L);
        regionBase.setNombre("Metropolitana");

        comunaCompleta = new Comuna();
        comunaCompleta.setId(10L);
        comunaCompleta.setNombre("Providencia");
        comunaCompleta.setRegion(regionBase);
    }

    // ========== convertirADTO ==========

    @Nested
    @DisplayName("convertirADTO")
    class ConvertirADTOTests {

        @Test
        @DisplayName("Debe retornar null cuando la comuna es null")
        void convertir_comunaNull_retornaNull() {
            ComunaDTO resultado = comunaValidaciones.convertirADTO(null);
            assertNull(resultado);
        }

        @Test
        @DisplayName("Debe mapear los datos básicos y relaciones formateadas correctamente")
        void convertir_comunaCompleta_retornaDTOMapeado() {
            // Estructuramos datos simulados para los repositorios internos
            Sucursal sucursal = new Sucursal();
            sucursal.setId(100L);
            sucursal.setNombre("Sucursal Tobalaba");

            Colaborador colaborador = new Colaborador();
            colaborador.setRun("12345678-9");
            colaborador.setNombres("María");
            colaborador.setApellidos("López");

            when(sucursalRepository.findByComunaId(10L)).thenReturn(List.of(sucursal));
            when(colaboradorRepository.findDistinctBySucursalesComunaId(10L)).thenReturn(List.of(colaborador));

            ComunaDTO dto = comunaValidaciones.convertirADTO(comunaCompleta);

            // Verificaciones básicas
            assertNotNull(dto);
            assertEquals(10L, dto.getId());
            assertEquals("Providencia", dto.getNombre());
            assertEquals("ID: 1 - Metropolitana", dto.getRegion());

            // Verificación del formateo de sucursales
            assertNotNull(dto.getSucursales());
            assertEquals(1, dto.getSucursales().size());
            assertEquals("ID: 100 - Sucursal Tobalaba", dto.getSucursales().get(0));

            // Verificación del formateo de colaboradores
            assertNotNull(dto.getColaboradores());
            assertEquals(1, dto.getColaboradores().size());
            assertEquals("RUN: 12345678-9 - María López", dto.getColaboradores().get(0));

            // Aseguramos que se invocaron los repositorios correspondientes
            verify(sucursalRepository).findByComunaId(10L);
            verify(colaboradorRepository).findDistinctBySucursalesComunaId(10L);
        }

        @Test
        @DisplayName("Debe dejar el campo región nulo si la comuna no tiene región asignada")
        void convertir_sinRegion_regionNullEnDTO() {
            comunaCompleta.setRegion(null);

            when(sucursalRepository.findByComunaId(10L)).thenReturn(List.of());
            when(colaboradorRepository.findDistinctBySucursalesComunaId(10L)).thenReturn(List.of());

            ComunaDTO dto = comunaValidaciones.convertirADTO(comunaCompleta);

            assertNotNull(dto);
            assertNull(dto.getRegion());
        }

        @Test
        @DisplayName("Debe retornar listas vacías en el DTO cuando no existen sucursales ni colaboradores")
        void convertir_sinDatosRelacionados_listasVaciasEnDTO() {
            when(sucursalRepository.findByComunaId(10L)).thenReturn(List.of());
            when(colaboradorRepository.findDistinctBySucursalesComunaId(10L)).thenReturn(List.of());

            ComunaDTO dto = comunaValidaciones.convertirADTO(comunaCompleta);

            assertNotNull(dto);
            assertNotNull(dto.getSucursales());
            assertTrue(dto.getSucursales().isEmpty());
            assertNotNull(dto.getColaboradores());
            assertTrue(dto.getColaboradores().isEmpty());
        }
    }
}