package com.RRHH.ColaboradoresSucursales.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import org.springframework.web.reactive.function.client.WebClient;

import com.RRHH.ColaboradoresSucursales.DTO.RegionDTO;
import com.RRHH.ColaboradoresSucursales.model.Comuna;
import com.RRHH.ColaboradoresSucursales.model.Region;
import com.RRHH.ColaboradoresSucursales.model.Sucursal;
import com.RRHH.ColaboradoresSucursales.repository.ComunaRepository;
import com.RRHH.ColaboradoresSucursales.repository.SucursalRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("RegionValidaciones - Pruebas Unitarias")
class RegionValidacionesTest {

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private ComunaRepository comunaRepository;

    @Mock
    private SucursalRepository sucursalRepository;

    @InjectMocks
    private RegionValidaciones regionValidaciones;

    private Region regionCompleta;

    @BeforeEach
    void setUp() {
        regionCompleta = new Region();
        regionCompleta.setId(1L);
        regionCompleta.setNombre("Región Metropolitana");
    }

    // ========== validarNullVacio ==========

    @Nested
    @DisplayName("validarNullVacio")
    class ValidarNullVacioTests {

        @Test
        @DisplayName("Debe retornar true cuando el nombre está completo")
        void validar_nombreValido_retornaTrue() {
            assertTrue(regionValidaciones.validarNullVacio(regionCompleta));
        }

        @Test
        @DisplayName("Debe retornar false cuando el nombre es null")
        void validar_nombreNull_retornaFalse() {
            regionCompleta.setNombre(null);
            assertFalse(regionValidaciones.validarNullVacio(regionCompleta));
        }

        @Test
        @DisplayName("Debe retornar false cuando el nombre está vacío o contiene solo espacios")
        void validar_nombreVacioOConEspacios_retornaFalse() {
            regionCompleta.setNombre("");
            assertFalse(regionValidaciones.validarNullVacio(regionCompleta));

            regionCompleta.setNombre("   ");
            assertFalse(regionValidaciones.validarNullVacio(regionCompleta));
        }
    }

    // ========== convertirADTO ==========

    @Nested
    @DisplayName("convertirADTO")
    class ConvertirADTOTests {

        @Test
        @DisplayName("Debe mapear los datos básicos y sus colecciones relacionadas formateadas como texto")
        void convertir_regionCompleta_retornaDTOMapeado() {
            // Datos simulados para las relaciones de la región
            Comuna comuna = new Comuna();
            comuna.setId(10L);
            comuna.setNombre("Santiago");

            Sucursal sucursal = new Sucursal();
            sucursal.setId(100L);
            sucursal.setNombre("Sucursal Alameda");

            when(comunaRepository.findByRegionId(1L)).thenReturn(List.of(comuna));
            when(sucursalRepository.findByComunaRegionId(1L)).thenReturn(List.of(sucursal));

            RegionDTO dto = regionValidaciones.convertirADTO(regionCompleta);

            // Validaciones de datos principales
            assertNotNull(dto);
            assertEquals(1L, dto.getId());
            assertEquals("Región Metropolitana", dto.getNombre());

            // Validaciones de formato de listas de texto
            assertNotNull(dto.getComunas());
            assertEquals(1, dto.getComunas().size());
            assertEquals("ID: 10 - Santiago", dto.getComunas().get(0));

            assertNotNull(dto.getSucursales());
            assertEquals(1, dto.getSucursales().size());
            assertEquals("ID: 100 - Sucursal Alameda", dto.getSucursales().get(0));

            // Verificar invocaciones a repositorios
            verify(comunaRepository).findByRegionId(1L);
            verify(sucursalRepository).findByComunaRegionId(1L);
        }

        @Test
        @DisplayName("Debe retornar listas vacías en el DTO cuando no se encuentran comunas ni sucursales")
        void convertir_sinRelacionesExistentes_listasVaciasEnDTO() {
            when(comunaRepository.findByRegionId(1L)).thenReturn(List.of());
            when(sucursalRepository.findByComunaRegionId(1L)).thenReturn(List.of());

            RegionDTO dto = regionValidaciones.convertirADTO(regionCompleta);

            assertNotNull(dto);
            assertNotNull(dto.getComunas());
            assertTrue(dto.getComunas().isEmpty());

            assertNotNull(dto.getSucursales());
            assertTrue(dto.getSucursales().isEmpty());
        }
    }
}