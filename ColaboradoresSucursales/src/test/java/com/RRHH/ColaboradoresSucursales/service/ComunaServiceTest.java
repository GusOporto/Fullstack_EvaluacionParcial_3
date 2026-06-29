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

import com.RRHH.ColaboradoresSucursales.DTO.ComunaDTO;
import com.RRHH.ColaboradoresSucursales.DTO.SucursalDTO;
import com.RRHH.ColaboradoresSucursales.model.Comuna;
import com.RRHH.ColaboradoresSucursales.model.Region;
import com.RRHH.ColaboradoresSucursales.model.Sucursal;
import com.RRHH.ColaboradoresSucursales.repository.ComunaRepository;
import com.RRHH.ColaboradoresSucursales.repository.SucursalRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("ComunaService - Pruebas Unitarias")
class ComunaServiceTest {

    @Mock
    private ComunaRepository comunaRepository;

    @Mock
    private SucursalRepository sucursalRepository;

    @Mock
    private ComunaValidaciones comunaValidaciones;

    @InjectMocks
    private ComunaService comunaService;

    private Comuna comuna;
    private ComunaDTO comunaDTO;
    private Region regionBase;

    @BeforeEach
    void setUp() {
        regionBase = new Region();
        regionBase.setId(1L);
        regionBase.setNombre("Metropolitana");

        comuna = new Comuna();
        comuna.setId(10L);
        comuna.setNombre("Providencia");
        comuna.setRegion(regionBase);

        comunaDTO = new ComunaDTO();
        comunaDTO.setId(10L);
        comunaDTO.setNombre("Providencia");
    }

    // ========== findAll ==========

    @Nested
    @DisplayName("findAll")
    class FindAllTests {

        @Test
        @DisplayName("Debe retornar lista de DTOs cuando existen comunas")
        void findAll_conComunas_retornaListaDTOs() {
            Comuna com2 = new Comuna();
            com2.setId(11L);
            com2.setNombre("Ñuñoa");

            ComunaDTO dto2 = new ComunaDTO();
            dto2.setId(11L);
            dto2.setNombre("Ñuñoa");

            when(comunaRepository.findAll()).thenReturn(List.of(comuna, com2));
            when(comunaValidaciones.convertirADTO(comuna)).thenReturn(comunaDTO);
            when(comunaValidaciones.convertirADTO(com2)).thenReturn(dto2);

            List<ComunaDTO> resultado = comunaService.findAll();

            assertNotNull(resultado);
            assertEquals(2, resultado.size());
            assertEquals("Providencia", resultado.get(0).getNombre());
            verify(comunaRepository).findAll();
        }

        @Test
        @DisplayName("Debe retornar lista vacía cuando no hay comunas")
        void findAll_sinComunas_retornaListaVacia() {
            when(comunaRepository.findAll()).thenReturn(List.of());

            List<ComunaDTO> resultado = comunaService.findAll();

            assertNotNull(resultado);
            assertTrue(resultado.isEmpty());
            verify(comunaRepository).findAll();
        }
    }

    // ========== findById ==========

    @Nested
    @DisplayName("findById")
    class FindByIdTests {

        @Test
        @DisplayName("Debe retornar DTO cuando la comuna existe")
        void findById_existente_retornaDTO() {
            when(comunaRepository.findById(10L)).thenReturn(Optional.of(comuna));
            when(comunaValidaciones.convertirADTO(comuna)).thenReturn(comunaDTO);

            ComunaDTO resultado = comunaService.findById(10L);

            assertNotNull(resultado);
            assertEquals(10L, resultado.getId());
            assertEquals("Providencia", resultado.getNombre());
            verify(comunaRepository).findById(10L);
        }

        @Test
        @DisplayName("Debe lanzar RuntimeException cuando la comuna no existe")
        void findById_noExistente_lanzaExcepcion() {
            when(comunaRepository.findById(99L)).thenReturn(Optional.empty());

            RuntimeException ex = assertThrows(RuntimeException.class,
                    () -> comunaService.findById(99L));

            assertEquals("Comuna no encontrada.", ex.getMessage());
        }
    }

    // ========== save ==========

    @Nested
    @DisplayName("save")
    class SaveTests {

        @Test
        @DisplayName("Debe guardar y retornar el DTO de la comuna")
        void save_datosValidos_retornaDTO() {
            when(comunaRepository.save(comuna)).thenReturn(comuna);
            when(comunaValidaciones.convertirADTO(comuna)).thenReturn(comunaDTO);

            ComunaDTO resultado = comunaService.save(comuna);

            assertNotNull(resultado);
            assertEquals("Providencia", resultado.getNombre());
            verify(comunaRepository).save(comuna);
        }
    }

    // ========== delete ==========

    @Nested
    @DisplayName("delete")
    class DeleteTests {

        @Test
        @DisplayName("Debe retornar mensaje de éxito al eliminar una comuna existente")
        void delete_existente_retornaMensajeExito() {
            when(comunaRepository.findById(10L)).thenReturn(Optional.of(comuna));

            String resultado = comunaService.delete(10L);

            assertEquals("La Comuna ID: 10 ha sido eliminada exitosamente.", resultado);
            verify(comunaRepository).delete(comuna);
        }

        @Test
        @DisplayName("Debe retornar mensaje de error controlado al intentar eliminar una comuna inexistente")
        void delete_noExistente_retornaMensajeError() {
            when(comunaRepository.findById(99L)).thenReturn(Optional.empty());

            String resultado = comunaService.delete(99L);

            assertEquals("No se pudo borrar la Comuna.", resultado);
            verify(comunaRepository, never()).delete(any());
        }
    }

    // ========== updateComuna ==========

    @Nested
    @DisplayName("updateComuna")
    class UpdateComunaTests {

        @Test
        @DisplayName("Debe actualizar todos los campos modificables correctamente")
        void update_conCamposNuevos_actualizaCorrectamente() {
            Region nuevaRegion = new Region();
            nuevaRegion.setId(2L);
            nuevaRegion.setNombre("Valparaíso");

            Comuna datosNuevos = new Comuna();
            datosNuevos.setNombre("Casablanca");
            datosNuevos.setRegion(nuevaRegion);

            when(comunaRepository.findById(10L)).thenReturn(Optional.of(comuna));
            when(comunaRepository.save(any(Comuna.class))).thenReturn(comuna);
            when(comunaValidaciones.convertirADTO(any(Comuna.class))).thenReturn(comunaDTO);

            comunaService.updateComuna(10L, datosNuevos);

            ArgumentCaptor<Comuna> captor = ArgumentCaptor.forClass(Comuna.class);
            verify(comunaRepository).save(captor.capture());
            Comuna comunaGuardada = captor.getValue();

            assertEquals("Casablanca", comunaGuardada.getNombre());
            assertEquals(2L, comunaGuardada.getRegion().getId());
        }

        @Test
        @DisplayName("Debe mantener valores originales si los nuevos campos son nulos")
        void update_conCamposNulos_mantieneDatosOriginales() {
            Comuna datosNuevos = new Comuna(); // nombre = null, region = null

            when(comunaRepository.findById(10L)).thenReturn(Optional.of(comuna));
            when(comunaRepository.save(any(Comuna.class))).thenReturn(comuna);
            when(comunaValidaciones.convertirADTO(any(Comuna.class))).thenReturn(comunaDTO);

            comunaService.updateComuna(10L, datosNuevos);

            ArgumentCaptor<Comuna> captor = ArgumentCaptor.forClass(Comuna.class);
            verify(comunaRepository).save(captor.capture());
            Comuna comunaGuardada = captor.getValue();

            assertEquals("Providencia", comunaGuardada.getNombre());
            assertEquals(1L, comunaGuardada.getRegion().getId());
        }

        @Test
        @DisplayName("Debe lanzar excepción al intentar actualizar una comuna inexistente")
        void update_noExistente_lanzaExcepcion() {
            Comuna datosNuevos = new Comuna();
            when(comunaRepository.findById(99L)).thenReturn(Optional.empty());

            RuntimeException ex = assertThrows(RuntimeException.class,
                    () -> comunaService.updateComuna(99L, datosNuevos));

            assertEquals("La Comuna no existe.", ex.getMessage());
        }
    }

    // ========== findSucursalesByComunaId ==========

    @Nested
    @DisplayName("findSucursalesByComunaId")
    class FindSucursalesByComunaIdTests {

        @Test
        @DisplayName("Debe lanzar excepción si la comuna no existe")
        void findSucursales_comunaNoExiste_lanzaExcepcion() {
            when(comunaRepository.existsById(99L)).thenReturn(false);

            RuntimeException ex = assertThrows(RuntimeException.class,
                    () -> comunaService.findSucursalesByComunaId(99L));

            assertEquals("Comuna no encontrada.", ex.getMessage());
            verify(sucursalRepository, never()).findByComunaId(any());
        }

        @Test
        @DisplayName("Debe retornar lista de SucursalDTO mapeada correctamente al encontrar la comuna")
        void findSucursales_comunaExisteConSucursales_retornaListaDTOs() {
            // Estructuramos la sucursal apuntando a nuestra comuna y región base para
            // blindar el bucle for
            Sucursal sucursal = new Sucursal();
            sucursal.setId(100L);
            sucursal.setNombre("Sucursal Costanera");
            sucursal.setDireccion("Av. Andrés Bello 2425");
            sucursal.setComuna(comuna);

            when(comunaRepository.existsById(10L)).thenReturn(true);
            when(sucursalRepository.findByComunaId(10L)).thenReturn(List.of(sucursal));

            List<SucursalDTO> resultado = comunaService.findSucursalesByComunaId(10L);

            assertNotNull(resultado);
            assertEquals(1, resultado.size());

            SucursalDTO dtoResultado = resultado.get(0);
            assertEquals(100L, dtoResultado.getId());
            assertEquals("Sucursal Costanera", dtoResultado.getNombre());
            assertEquals("ID: 1 - Metropolitana", dtoResultado.getRegion());
            assertEquals("ID: 10 - Providencia", dtoResultado.getComuna());
        }
    }
}