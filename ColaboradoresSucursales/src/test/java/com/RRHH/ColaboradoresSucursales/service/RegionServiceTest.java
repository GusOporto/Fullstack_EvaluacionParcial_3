package com.RRHH.ColaboradoresSucursales.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.RRHH.ColaboradoresSucursales.DTO.RegionDTO;
import com.RRHH.ColaboradoresSucursales.DTO.SucursalDTO;
import com.RRHH.ColaboradoresSucursales.model.Comuna;
import com.RRHH.ColaboradoresSucursales.model.Region;
import com.RRHH.ColaboradoresSucursales.model.Sucursal;
import com.RRHH.ColaboradoresSucursales.repository.RegionRepository;
import com.RRHH.ColaboradoresSucursales.repository.SucursalRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("RegionService - Pruebas Unitarias")
class RegionServiceTest {

    @Mock
    private RegionRepository regionRepository;

    @Mock
    private SucursalRepository sucursalRepository;

    @Mock
    private RegionValidaciones regionValidaciones;

    @InjectMocks
    private RegionService regionService;

    private Region region;
    private RegionDTO regionDTO;

    @BeforeEach
    void setUp() {
        region = new Region();
        region.setId(1L);
        region.setNombre("Metropolitana");

        regionDTO = new RegionDTO();
        regionDTO.setId(1L);
        regionDTO.setNombre("Metropolitana");
    }

    // ========== findAll ==========

    @Nested
    @DisplayName("findAll")
    class FindAllTests {

        @Test
        @DisplayName("Debe retornar lista de DTOs cuando existen regiones")
        void findAll_conRegiones_retornaListaDTOs() {
            Region reg2 = new Region();
            reg2.setId(2L);
            reg2.setNombre("Valparaíso");

            RegionDTO dto2 = new RegionDTO();
            dto2.setId(2L);
            dto2.setNombre("Valparaíso");

            when(regionRepository.findAll()).thenReturn(List.of(region, reg2));
            when(regionValidaciones.convertirADTO(region)).thenReturn(regionDTO);
            when(regionValidaciones.convertirADTO(reg2)).thenReturn(dto2);

            List<RegionDTO> resultado = regionService.findAll();

            assertNotNull(resultado);
            assertEquals(2, resultado.size());
            assertEquals("Metropolitana", resultado.get(0).getNombre());
            verify(regionRepository).findAll();
        }

        @Test
        @DisplayName("Debe retornar lista vacía cuando no hay regiones")
        void findAll_sinRegiones_retornaListaVacia() {
            when(regionRepository.findAll()).thenReturn(List.of());

            List<RegionDTO> resultado = regionService.findAll();

            assertNotNull(resultado);
            assertTrue(resultado.isEmpty());
            verify(regionRepository).findAll();
        }
    }

    // ========== findById ==========

    @Nested
    @DisplayName("findById")
    class FindByIdTests {

        @Test
        @DisplayName("Debe retornar DTO cuando la región existe")
        void findById_existente_retornaDTO() {
            when(regionRepository.findById(1L)).thenReturn(Optional.of(region));
            when(regionValidaciones.convertirADTO(region)).thenReturn(regionDTO);

            RegionDTO resultado = regionService.findById(1L);

            assertNotNull(resultado);
            assertEquals(1L, resultado.getId());
            assertEquals("Metropolitana", resultado.getNombre());
            verify(regionRepository).findById(1L);
        }

        @Test
        @DisplayName("Debe lanzar RuntimeException cuando la región no existe")
        void findById_noExistente_lanzaExcepcion() {
            when(regionRepository.findById(99L)).thenReturn(Optional.empty());

            RuntimeException ex = assertThrows(RuntimeException.class,
                    () -> regionService.findById(99L));

            assertEquals("Region no encontrada.", ex.getMessage());
        }
    }

    // ========== findSucursalesByRegionId ==========

    @Nested
    @DisplayName("findSucursalesByRegionId")
    class FindSucursalesByRegionIdTests {

        @Test
        @DisplayName("Debe lanzar excepción si la región no existe")
        void findSucursales_regionNoExiste_lanzaExcepcion() {
            when(regionRepository.existsById(99L)).thenReturn(false);

            RuntimeException ex = assertThrows(RuntimeException.class,
                    () -> regionService.findSucursalesByRegionId(99L));

            assertEquals("Region no encontrada.", ex.getMessage());
            verify(sucursalRepository, never()).findByComunaRegionId(any());
        }

        @Test
        @DisplayName("Debe retornar lista de SucursalDTO mapeada correctamente cuando la región existe")
        void findSucursales_regionExisteConSucursales_retornaListaDTOs() {
            // Construcción del grafo de objetos para evitar el NullPointerException en el
            // mapeo manual del servicio
            Comuna comuna = new Comuna();
            comuna.setId(10L);
            comuna.setNombre("Santiago Centro");
            comuna.setRegion(region);

            Sucursal sucursal = new Sucursal();
            sucursal.setId(100L);
            sucursal.setNombre("Sucursal Central");
            sucursal.setDireccion("Av. Libertador 123");
            sucursal.setComuna(comuna);

            when(regionRepository.existsById(1L)).thenReturn(true);
            when(sucursalRepository.findByComunaRegionId(1L)).thenReturn(List.of(sucursal));

            List<SucursalDTO> resultado = regionService.findSucursalesByRegionId(1L);

            assertNotNull(resultado);
            assertEquals(1, resultado.size());

            SucursalDTO dtoResultado = resultado.get(0);
            assertEquals(100L, dtoResultado.getId());
            assertEquals("Sucursal Central", dtoResultado.getNombre());
            assertEquals("ID: 1 - Metropolitana", dtoResultado.getRegion());
            assertEquals("ID: 10 - Santiago Centro", dtoResultado.getComuna());
        }
    }

    // ========== save ==========

    @Nested
    @DisplayName("save")
    class SaveTests {

        @Test
        @DisplayName("Debe guardar y retornar el DTO de la región")
        void save_datosValidos_retornaDTO() {
            when(regionRepository.save(region)).thenReturn(region);
            when(regionValidaciones.convertirADTO(region)).thenReturn(regionDTO);

            RegionDTO resultado = regionService.save(region);

            assertNotNull(resultado);
            assertEquals("Metropolitana", resultado.getNombre());
            verify(regionRepository).save(region);
        }
    }

    // ========== delete ==========

    @Nested
    @DisplayName("delete")
    class DeleteTests {

        @Test
        @DisplayName("Debe retornar mensaje de éxito al eliminar una región existente")
        void delete_existente_retornaMensajeExito() {
            when(regionRepository.findById(1L)).thenReturn(Optional.of(region));

            String resultado = regionService.delete(1L);

            assertEquals("La Region ID: 1 ha sido eliminada exitosamente.", resultado);
            verify(regionRepository).delete(region);
        }

        @Test
        @DisplayName("Debe retornar mensaje de error controlado al intentar eliminar una región inexistente")
        void delete_noExistente_retornaMensajeError() {
            when(regionRepository.findById(99L)).thenReturn(Optional.empty());

            String resultado = regionService.delete(99L);

            assertEquals("No se pudo borrar la Region ID: 99, no existe.", resultado);
            verify(regionRepository, never()).delete(any());
        }
    }

    // ========== updateRegion ==========

    @Nested
    @DisplayName("updateRegion")
    class UpdateRegionTests {

        @Test
        @DisplayName("Debe actualizar el nombre de la región correctamente")
        void update_conCamposNuevos_actualizaCorrectamente() {
            Region datosNuevos = new Region();
            datosNuevos.setNombre("Nueva Region Metropolitana");

            when(regionRepository.findById(1L)).thenReturn(Optional.of(region));
            when(regionRepository.save(any(Region.class))).thenReturn(region);
            when(regionValidaciones.convertirADTO(any(Region.class))).thenReturn(regionDTO);

            regionService.updateRegion(1L, datosNuevos);

            // Capturamos el estado final para asegurar que el cambio se ejecutó
            ArgumentCaptor<Region> captor = ArgumentCaptor.forClass(Region.class);
            verify(regionRepository).save(captor.capture());

            assertEquals("Nueva Region Metropolitana", captor.getValue().getNombre());
        }

        @Test
        @DisplayName("Debe mantener el nombre original si los datos de actualización vienen nulos")
        void update_conCamposNulos_mantieneDatosOriginales() {
            Region datosNuevos = new Region(); // nombre = null

            when(regionRepository.findById(1L)).thenReturn(Optional.of(region));
            when(regionRepository.save(any(Region.class))).thenReturn(region);
            when(regionValidaciones.convertirADTO(any(Region.class))).thenReturn(regionDTO);

            regionService.updateRegion(1L, datosNuevos);

            ArgumentCaptor<Region> captor = ArgumentCaptor.forClass(Region.class);
            verify(regionRepository).save(captor.capture());

            assertEquals("Metropolitana", captor.getValue().getNombre()); // No cambió
        }

        @Test
        @DisplayName("Debe lanzar excepción al intentar actualizar una región inexistente")
        void update_noExistente_lanzaExcepcion() {
            Region datosNuevos = new Region();
            when(regionRepository.findById(99L)).thenReturn(Optional.empty());

            RuntimeException ex = assertThrows(RuntimeException.class,
                    () -> regionService.updateRegion(99L, datosNuevos));

            assertEquals("La Region no existe.", ex.getMessage());
        }
    }
}