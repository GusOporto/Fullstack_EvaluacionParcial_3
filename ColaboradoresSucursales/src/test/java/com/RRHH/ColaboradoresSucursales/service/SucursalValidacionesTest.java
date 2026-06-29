package com.RRHH.ColaboradoresSucursales.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

import com.RRHH.ColaboradoresSucursales.DTO.SucursalDTO;
import com.RRHH.ColaboradoresSucursales.model.Colaborador;
import com.RRHH.ColaboradoresSucursales.model.Comuna;
import com.RRHH.ColaboradoresSucursales.model.Region;
import com.RRHH.ColaboradoresSucursales.model.Sucursal;
import com.RRHH.ColaboradoresSucursales.repository.ColaboradorRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("SucursalValidaciones - Pruebas Unitarias")
class SucursalValidacionesTest {

    @Mock
    private ColaboradorRepository colaboradorRepository;

    @InjectMocks
    private SucursalValidaciones sucursalValidaciones;

    private Sucursal sucursalCompleta;
    private Comuna comunaBase;
    private Region regionBase;

    @BeforeEach
    void setUp() {
        regionBase = new Region();
        regionBase.setId(1L);
        regionBase.setNombre("Metropolitana");

        comunaBase = new Comuna();
        comunaBase.setId(10L);
        comunaBase.setNombre("Santiago Centro");
        comunaBase.setRegion(regionBase);

        sucursalCompleta = new Sucursal();
        sucursalCompleta.setId(100L);
        sucursalCompleta.setNombre("Sucursal Central");
        sucursalCompleta.setDireccion("Av. Libertador 1234");
        sucursalCompleta.setComuna(comunaBase);
    }

    // ========== validarNullVacio ==========

    @Nested
    @DisplayName("validarNullVacio")
    class ValidarNullVacioTests {

        @Test
        @DisplayName("Debe retornar true cuando todos los campos obligatorios están completos")
        void validar_todosLosCamposCompletos_retornaTrue() {
            assertTrue(sucursalValidaciones.validarNullVacio(sucursalCompleta));
        }

        @Test
        @DisplayName("Debe retornar false cuando el nombre es null o vacío")
        void validar_nombreInvalido_retornaFalse() {
            sucursalCompleta.setNombre(null);
            assertFalse(sucursalValidaciones.validarNullVacio(sucursalCompleta));

            sucursalCompleta.setNombre("");
            assertFalse(sucursalValidaciones.validarNullVacio(sucursalCompleta));
        }

        @Test
        @DisplayName("Debe retornar false cuando la dirección es null o vacía")
        void validar_direccionInvalida_retornaFalse() {
            sucursalCompleta.setDireccion(null);
            assertFalse(sucursalValidaciones.validarNullVacio(sucursalCompleta));

            sucursalCompleta.setDireccion("");
            assertFalse(sucursalValidaciones.validarNullVacio(sucursalCompleta));
        }

        @Test
        @DisplayName("Debe retornar false cuando la comuna asociada es null")
        void validar_comunaNull_retornaFalse() {
            sucursalCompleta.setComuna(null);
            assertFalse(sucursalValidaciones.validarNullVacio(sucursalCompleta));
        }
    }

    // ========== convertirADTO ==========

    @Nested
    @DisplayName("convertirADTO")
    class ConvertirADTOTests {

        @Test
        @DisplayName("Debe mapear los datos básicos y sus relaciones jerárquicas con formato de texto")
        void convertir_sucursalCompleta_retornaDTOMapeado() {
            // Inicializamos datos simulados de colaboradores vinculados a la sucursal
            Colaborador col = new Colaborador();
            col.setId(5L);
            col.setNombres("Carlos");
            col.setApellidos("Andrade");

            when(colaboradorRepository.findDistinctBySucursalesId(100L)).thenReturn(List.of(col));

            SucursalDTO dto = sucursalValidaciones.convertirADTO(sucursalCompleta);

            // Validaciones primarias de la sucursal
            assertNotNull(dto);
            assertEquals(100L, dto.getId());
            assertEquals("Sucursal Central", dto.getNombre());
            assertEquals("Av. Libertador 1234", dto.getDireccion());

            // Validaciones de jerarquía geográfica anidada
            assertEquals("ID: 10 - Santiago Centro", dto.getComuna());
            assertEquals("ID: 1 - Metropolitana", dto.getRegion());

            // Validación de los colaboradores formateados en string
            assertNotNull(dto.getColaboradores());
            assertEquals(1, dto.getColaboradores().size());
            assertEquals("ID: 5 - Carlos - Andrade", dto.getColaboradores().get(0));

            verify(colaboradorRepository).findDistinctBySucursalesId(100L);
        }

        @Test
        @DisplayName("Debe omitir los campos comuna y región en el DTO si la comuna original es null")
        void convertir_sinComunaAsociada_comunaYRegionNullEnDTO() {
            sucursalCompleta.setComuna(null);
            when(colaboradorRepository.findDistinctBySucursalesId(100L)).thenReturn(List.of());

            SucursalDTO dto = sucursalValidaciones.convertirADTO(sucursalCompleta);

            assertNotNull(dto);
            assertNull(dto.getComuna());
            assertNull(dto.getRegion());
        }

        @Test
        @DisplayName("Debe omitir el campo región en el DTO si la comuna no tiene una región asignada")
        void convertir_comunaSinRegion_regionNullEnDTO() {
            comunaBase.setRegion(null); // Desvinculamos la región
            when(colaboradorRepository.findDistinctBySucursalesId(100L)).thenReturn(List.of());

            SucursalDTO dto = sucursalValidaciones.convertirADTO(sucursalCompleta);

            assertNotNull(dto);
            assertEquals("ID: 10 - Santiago Centro", dto.getComuna());
            assertNull(dto.getRegion());
        }

        @Test
        @DisplayName("Debe retornar una lista vacía de colaboradores si el repositorio no encuentra coincidencias")
        void convertir_sinColaboradoresAsociados_listaVaciaEnDTO() {
            when(colaboradorRepository.findDistinctBySucursalesId(100L)).thenReturn(List.of());

            SucursalDTO dto = sucursalValidaciones.convertirADTO(sucursalCompleta);

            assertNotNull(dto);
            assertNotNull(dto.getColaboradores());
            assertTrue(dto.getColaboradores().isEmpty());
        }
    }
}