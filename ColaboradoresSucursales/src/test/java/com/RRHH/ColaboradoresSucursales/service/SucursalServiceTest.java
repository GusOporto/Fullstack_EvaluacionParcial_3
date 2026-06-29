package com.RRHH.ColaboradoresSucursales.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.RRHH.ColaboradoresSucursales.DTO.ColaboradorDTO;
import com.RRHH.ColaboradoresSucursales.DTO.SucursalDTO;
import com.RRHH.ColaboradoresSucursales.model.Colaborador;
import com.RRHH.ColaboradoresSucursales.model.Comuna;
import com.RRHH.ColaboradoresSucursales.model.Region;
import com.RRHH.ColaboradoresSucursales.model.Sucursal;
import com.RRHH.ColaboradoresSucursales.repository.ColaboradorRepository;
import com.RRHH.ColaboradoresSucursales.repository.SucursalRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("SucursalService - Pruebas Unitarias")
class SucursalServiceTest {

    @Mock
    private SucursalRepository sucursalRepository;

    @Mock
    private ColaboradorRepository colaboradorRepository;

    @Mock
    private SucursalValidaciones sucursalValidaciones;

    @Mock
    private ColaboradorValidaciones colaboradorValidaciones;

    @InjectMocks
    private SucursalService sucursalService;

    private Sucursal sucursal;
    private SucursalDTO sucursalDTO;
    private Region region;
    private Comuna comuna;

    @BeforeEach
    void setUp() {
        region = new Region(1L, "Región Metropolitana");
        comuna = new Comuna(1L, "Santiago", region);

        sucursal = new Sucursal();
        sucursal.setId(1L);
        sucursal.setNombre("Sucursal Central");
        sucursal.setDireccion("Av. Principal 100");
        sucursal.setComuna(comuna);

        sucursalDTO = new SucursalDTO();
        sucursalDTO.setId(1L);
        sucursalDTO.setNombre("Sucursal Central");
        sucursalDTO.setDireccion("Av. Principal 100");
        sucursalDTO.setComuna("ID: 1 - Santiago");
        sucursalDTO.setRegion("ID: 1 - Región Metropolitana");
    }

    // ========== findAll ==========

    @Nested
    @DisplayName("findAll")
    class FindAllTests {

        @Test
        @DisplayName("Debe retornar lista de DTOs cuando existen sucursales")
        void findAll_conSucursales_retornaListaDTOs() {
            when(sucursalRepository.findAll()).thenReturn(List.of(sucursal));
            when(sucursalValidaciones.convertirADTO(sucursal)).thenReturn(sucursalDTO);

            List<SucursalDTO> resultado = sucursalService.findAll();

            assertNotNull(resultado);
            assertEquals(1, resultado.size());
            assertEquals("Sucursal Central", resultado.get(0).getNombre());
            verify(sucursalRepository).findAll();
        }

        @Test
        @DisplayName("Debe retornar lista vacía cuando no hay sucursales")
        void findAll_sinSucursales_retornaListaVacia() {
            when(sucursalRepository.findAll()).thenReturn(List.of());

            List<SucursalDTO> resultado = sucursalService.findAll();

            assertNotNull(resultado);
            assertTrue(resultado.isEmpty());
        }
    }

    // ========== findById ==========

    @Nested
    @DisplayName("findById")
    class FindByIdTests {

        @Test
        @DisplayName("Debe retornar DTO cuando la sucursal existe")
        void findById_existente_retornaDTO() {
            when(sucursalRepository.findById(1L)).thenReturn(Optional.of(sucursal));
            when(sucursalValidaciones.convertirADTO(sucursal)).thenReturn(sucursalDTO);

            SucursalDTO resultado = sucursalService.findById(1L);

            assertNotNull(resultado);
            assertEquals(1L, resultado.getId());
            assertEquals("Sucursal Central", resultado.getNombre());
        }

        @Test
        @DisplayName("Debe lanzar RuntimeException cuando la sucursal no existe")
        void findById_noExistente_lanzaExcepcion() {
            when(sucursalRepository.findById(99L)).thenReturn(Optional.empty());

            RuntimeException ex = assertThrows(RuntimeException.class,
                    () -> sucursalService.findById(99L));

            assertEquals("Sucursal no encontrada.", ex.getMessage());
        }
    }

    // ========== save ==========

    @Nested
    @DisplayName("save")
    class SaveTests {

        @Test
        @DisplayName("Debe guardar y retornar DTO correctamente")
        void save_datosValidos_retornaDTO() {
            when(sucursalRepository.save(sucursal)).thenReturn(sucursal);
            when(sucursalValidaciones.convertirADTO(sucursal)).thenReturn(sucursalDTO);

            SucursalDTO resultado = sucursalService.save(sucursal);

            assertNotNull(resultado);
            assertEquals("Sucursal Central", resultado.getNombre());
            verify(sucursalRepository).save(sucursal);
        }
    }

    // ========== delete ==========

    @Nested
    @DisplayName("delete")
    class DeleteTests {

        @Test
        @DisplayName("Debe retornar mensaje de éxito al eliminar sucursal existente")
        void delete_existente_retornaMensajeExito() {
            when(sucursalRepository.findById(1L)).thenReturn(Optional.of(sucursal));

            String resultado = sucursalService.delete(1L);

            assertEquals("La Sucursal ID: 1 ha sido eliminada exitosamente.", resultado);
            verify(sucursalRepository).delete(sucursal);
        }

        @Test
        @DisplayName("Debe retornar mensaje de error al eliminar sucursal inexistente")
        void delete_noExistente_retornaMensajeError() {
            when(sucursalRepository.findById(99L)).thenReturn(Optional.empty());

            String resultado = sucursalService.delete(99L);

            assertEquals("No se pudo borrar la Sucursal.", resultado);
            verify(sucursalRepository, never()).delete(any());
        }
    }

    // ========== updateSucursal ==========

    @Nested
    @DisplayName("updateSucursal")
    class UpdateTests {

        @Test
        @DisplayName("Debe actualizar todos los campos de la sucursal")
        void update_todosLosCampos_actualizaCorrectamente() {
            Sucursal datosNuevos = new Sucursal();
            datosNuevos.setNombre("Sucursal Nueva");
            datosNuevos.setDireccion("Calle Nueva 999");
            datosNuevos.setComuna(comuna);

            when(sucursalRepository.findById(1L)).thenReturn(Optional.of(sucursal));
            when(sucursalRepository.save(any(Sucursal.class))).thenReturn(sucursal);
            when(sucursalValidaciones.convertirADTO(any(Sucursal.class))).thenReturn(sucursalDTO);

            SucursalDTO resultado = sucursalService.updateSucursal(1L, datosNuevos);

            assertNotNull(resultado);
            verify(sucursalRepository).save(any(Sucursal.class));
        }

        @Test
        @DisplayName("Debe actualizar solo campos no nulos (parcial)")
        void update_camposParciales_actualizaSoloCamposNoNulos() {
            Sucursal datosNuevos = new Sucursal();
            datosNuevos.setNombre("Solo Nombre Nuevo");

            when(sucursalRepository.findById(1L)).thenReturn(Optional.of(sucursal));
            when(sucursalRepository.save(any(Sucursal.class))).thenReturn(sucursal);
            when(sucursalValidaciones.convertirADTO(any(Sucursal.class))).thenReturn(sucursalDTO);

            SucursalDTO resultado = sucursalService.updateSucursal(1L, datosNuevos);

            assertNotNull(resultado);
        }

        @Test
        @DisplayName("Debe lanzar excepción al actualizar sucursal inexistente")
        void update_noExistente_lanzaExcepcion() {
            when(sucursalRepository.findById(99L)).thenReturn(Optional.empty());

            assertThrows(RuntimeException.class,
                    () -> sucursalService.updateSucursal(99L, new Sucursal()));
        }
    }

    // ========== findColaboradoresBySucursalId ==========

    @Nested
    @DisplayName("findColaboradoresBySucursalId")
    class FindColaboradoresTests {

        @Test
        @DisplayName("Debe retornar lista de colaboradores cuando la sucursal existe")
        void findColaboradores_sucursalExiste_retornaLista() {
            Colaborador col = new Colaborador();
            col.setId(1L);
            col.setNombres("Juan");

            ColaboradorDTO colDTO = new ColaboradorDTO();
            colDTO.setId(1L);
            colDTO.setNombres("Juan");

            when(sucursalRepository.existsById(1L)).thenReturn(true);
            when(colaboradorRepository.findDistinctBySucursalesId(1L)).thenReturn(List.of(col));
            when(colaboradorValidaciones.convertirADTO(col)).thenReturn(colDTO);

            List<ColaboradorDTO> resultado = sucursalService.findColaboradoresBySucursalId(1L);

            assertNotNull(resultado);
            assertEquals(1, resultado.size());
            assertEquals("Juan", resultado.get(0).getNombres());
        }

        @Test
        @DisplayName("Debe lanzar excepción cuando la sucursal no existe")
        void findColaboradores_sucursalNoExiste_lanzaExcepcion() {
            when(sucursalRepository.existsById(99L)).thenReturn(false);

            RuntimeException ex = assertThrows(RuntimeException.class,
                    () -> sucursalService.findColaboradoresBySucursalId(99L));

            assertEquals("Sucursal no encontrada.", ex.getMessage());
        }

        @Test
        @DisplayName("Debe retornar lista vacía cuando no hay colaboradores en la sucursal")
        void findColaboradores_sinColaboradores_retornaListaVacia() {
            when(sucursalRepository.existsById(1L)).thenReturn(true);
            when(colaboradorRepository.findDistinctBySucursalesId(1L)).thenReturn(List.of());

            List<ColaboradorDTO> resultado = sucursalService.findColaboradoresBySucursalId(1L);

            assertNotNull(resultado);
            assertTrue(resultado.isEmpty());
        }
    }
}
