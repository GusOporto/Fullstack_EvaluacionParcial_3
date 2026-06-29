package com.RRHH.ColaboradoresSucursales.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
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
import com.RRHH.ColaboradoresSucursales.model.Colaborador;
import com.RRHH.ColaboradoresSucursales.repository.ColaboradorRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("ColaboradorService - Pruebas Unitarias")
class ColaboradorServiceTest {

    @Mock
    private ColaboradorRepository colaboradorRepository;

    @Mock
    private ColaboradorValidaciones colaboradorValidaciones;

    @InjectMocks
    private ColaboradorService colaboradorService;

    private Colaborador colaborador;
    private ColaboradorDTO colaboradorDTO;

    @BeforeEach
    void setUp() {
        colaborador = new Colaborador();
        colaborador.setId(1L);
        colaborador.setRun("123456789");
        colaborador.setNombres("Juan");
        colaborador.setApellidos("Pérez");
        colaborador.setFechaNacimiento(LocalDate.of(1990, 5, 15));
        colaborador.setTelefono("912345678");
        colaborador.setCorreo("juan@test.com");
        colaborador.setDireccion("Calle Falsa 123");
        colaborador.setSucursales(new ArrayList<>());

        colaboradorDTO = new ColaboradorDTO();
        colaboradorDTO.setId(1L);
        colaboradorDTO.setRun("123456789");
        colaboradorDTO.setNombres("Juan");
        colaboradorDTO.setApellidos("Pérez");
        colaboradorDTO.setFechaNacimiento(LocalDate.of(1990, 5, 15));
        colaboradorDTO.setTelefono("912345678");
        colaboradorDTO.setCorreo("juan@test.com");
        colaboradorDTO.setDireccion("Calle Falsa 123");
        colaboradorDTO.setSucursales(List.of());
    }

    // ========== findAll ==========

    @Nested
    @DisplayName("findAll")
    class FindAllTests {

        @Test
        @DisplayName("Debe retornar lista de DTOs cuando existen colaboradores")
        void findAll_conColaboradores_retornaListaDTOs() {
            Colaborador col2 = new Colaborador();
            col2.setId(2L);
            col2.setRun("987654321");
            col2.setNombres("María");
            col2.setApellidos("López");
            col2.setSucursales(new ArrayList<>());

            ColaboradorDTO dto2 = new ColaboradorDTO();
            dto2.setId(2L);
            dto2.setRun("987654321");

            when(colaboradorRepository.findAll()).thenReturn(List.of(colaborador, col2));
            when(colaboradorValidaciones.convertirADTO(colaborador)).thenReturn(colaboradorDTO);
            when(colaboradorValidaciones.convertirADTO(col2)).thenReturn(dto2);

            List<ColaboradorDTO> resultado = colaboradorService.findAll();

            assertNotNull(resultado);
            assertEquals(2, resultado.size());
            assertEquals("Juan", resultado.get(0).getNombres());
            verify(colaboradorRepository).findAll();
            verify(colaboradorValidaciones, times(2)).convertirADTO(any(Colaborador.class));
        }

        @Test
        @DisplayName("Debe retornar lista vacía cuando no hay colaboradores")
        void findAll_sinColaboradores_retornaListaVacia() {
            when(colaboradorRepository.findAll()).thenReturn(List.of());

            List<ColaboradorDTO> resultado = colaboradorService.findAll();

            assertNotNull(resultado);
            assertTrue(resultado.isEmpty());
            verify(colaboradorRepository).findAll();
        }
    }

    // ========== findById ==========

    @Nested
    @DisplayName("findById")
    class FindByIdTests {

        @Test
        @DisplayName("Debe retornar DTO cuando el colaborador existe")
        void findById_existente_retornaDTO() {
            when(colaboradorRepository.findById(1L)).thenReturn(Optional.of(colaborador));
            when(colaboradorValidaciones.convertirADTO(colaborador)).thenReturn(colaboradorDTO);

            ColaboradorDTO resultado = colaboradorService.findById(1L);

            assertNotNull(resultado);
            assertEquals(1L, resultado.getId());
            assertEquals("Juan", resultado.getNombres());
            verify(colaboradorRepository).findById(1L);
        }

        @Test
        @DisplayName("Debe lanzar RuntimeException cuando el colaborador no existe")
        void findById_noExistente_lanzaExcepcion() {
            when(colaboradorRepository.findById(99L)).thenReturn(Optional.empty());

            RuntimeException ex = assertThrows(RuntimeException.class,
                    () -> colaboradorService.findById(99L));

            assertEquals("Colaborador no encontrado.", ex.getMessage());
        }
    }

    // ========== save ==========

    @Nested
    @DisplayName("save")
    class SaveTests {

        @Test
        @DisplayName("Debe guardar y retornar DTO cuando los datos son válidos")
        void save_datosValidos_retornaDTO() {
            when(colaboradorValidaciones.validarNullVacio(colaborador)).thenReturn(true);
            when(colaboradorRepository.save(colaborador)).thenReturn(colaborador);
            when(colaboradorValidaciones.convertirADTO(colaborador)).thenReturn(colaboradorDTO);

            ColaboradorDTO resultado = colaboradorService.save(colaborador);

            assertNotNull(resultado);
            assertEquals("Juan", resultado.getNombres());
            verify(colaboradorRepository).save(colaborador);
        }

        @Test
        @DisplayName("Debe retornar null cuando la validación falla")
        void save_validacionFalla_retornaNull() {
            when(colaboradorValidaciones.validarNullVacio(colaborador)).thenReturn(false);

            ColaboradorDTO resultado = colaboradorService.save(colaborador);

            assertNull(resultado);
            verify(colaboradorRepository, never()).save(any());
        }
    }

    // ========== delete ==========

    @Nested
    @DisplayName("delete")
    class DeleteTests {

        @Test
        @DisplayName("Debe retornar mensaje de éxito al eliminar colaborador existente")
        void delete_existente_retornaMensajeExito() {
            when(colaboradorRepository.findById(1L)).thenReturn(Optional.of(colaborador));

            String resultado = colaboradorService.delete(1L);

            assertEquals("El Colaborador ID: 1 ha sido eliminado exitosamente.", resultado);
            verify(colaboradorRepository).delete(colaborador);
        }

        @Test
        @DisplayName("Debe retornar mensaje de error al intentar eliminar colaborador inexistente")
        void delete_noExistente_retornaMensajeError() {
            when(colaboradorRepository.findById(99L)).thenReturn(Optional.empty());

            String resultado = colaboradorService.delete(99L);

            assertEquals("No se pudo borrar el Colaborador.", resultado);
            verify(colaboradorRepository, never()).delete(any());
        }
    }

    // ========== updateColaborador ==========

    @Nested
    @DisplayName("updateColaborador")
    class UpdateTests {

        @Test
        @DisplayName("Debe actualizar todos los campos del colaborador")
        void update_todosLosCampos_actualizaCorrectamente() {
            Colaborador datosNuevos = new Colaborador();
            datosNuevos.setRun("111111111");
            datosNuevos.setNombres("Carlos");
            datosNuevos.setApellidos("García");
            datosNuevos.setFechaNacimiento(LocalDate.of(1985, 3, 20));
            datosNuevos.setTelefono("999999999");
            datosNuevos.setCorreo("carlos@test.com");
            datosNuevos.setDireccion("Av. Nueva 456");
            datosNuevos.setSucursales(new ArrayList<>());

            when(colaboradorRepository.findById(1L)).thenReturn(Optional.of(colaborador));
            when(colaboradorRepository.save(any(Colaborador.class))).thenReturn(colaborador);
            when(colaboradorValidaciones.convertirADTO(any(Colaborador.class))).thenReturn(colaboradorDTO);

            ColaboradorDTO resultado = colaboradorService.updateColaborador(1L, datosNuevos);

            assertNotNull(resultado);
            verify(colaboradorRepository).findById(1L);
            verify(colaboradorRepository).save(any(Colaborador.class));
        }

        @Test
        @DisplayName("Debe actualizar solo campos no nulos (parcial)")
        void update_camposParciales_actualizaSoloCamposNoNulos() {
            Colaborador datosNuevos = new Colaborador();
            datosNuevos.setNombres("NuevoNombre");
            // Los demás campos son null

            when(colaboradorRepository.findById(1L)).thenReturn(Optional.of(colaborador));
            when(colaboradorRepository.save(any(Colaborador.class))).thenReturn(colaborador);
            when(colaboradorValidaciones.convertirADTO(any(Colaborador.class))).thenReturn(colaboradorDTO);

            ColaboradorDTO resultado = colaboradorService.updateColaborador(1L, datosNuevos);

            assertNotNull(resultado);
            verify(colaboradorRepository).save(any(Colaborador.class));
        }

        @Test
        @DisplayName("Debe lanzar excepción al actualizar colaborador inexistente")
        void update_noExistente_lanzaExcepcion() {
            Colaborador datosNuevos = new Colaborador();
            when(colaboradorRepository.findById(99L)).thenReturn(Optional.empty());

            assertThrows(RuntimeException.class,
                    () -> colaboradorService.updateColaborador(99L, datosNuevos));
        }
    }

    // ========== findByRun ==========

    @Nested
    @DisplayName("findByRun")
    class FindByRunTests {

        @Test
        @DisplayName("Debe retornar DTO cuando el RUN existe")
        void findByRun_existente_retornaDTO() {
            when(colaboradorRepository.findByRun("123456789")).thenReturn(colaborador);
            when(colaboradorValidaciones.convertirADTO(colaborador)).thenReturn(colaboradorDTO);

            ColaboradorDTO resultado = colaboradorService.findByRun("123456789");

            assertNotNull(resultado);
            assertEquals("123456789", resultado.getRun());
        }

        @Test
        @DisplayName("Debe lanzar excepción cuando el RUN no existe")
        void findByRun_noExistente_lanzaExcepcion() {
            when(colaboradorRepository.findByRun("000000000")).thenReturn(null);

            RuntimeException ex = assertThrows(RuntimeException.class,
                    () -> colaboradorService.findByRun("000000000"));

            assertEquals("Colaborador no encontrado", ex.getMessage());
        }
    }

    // ========== findBySucursales ==========

    @Nested
    @DisplayName("findBySucursales")
    class FindBySucursalesTests {

        @Test
        @DisplayName("Debe retornar lista de DTOs cuando hay colaboradores en la sucursal")
        void findBySucursales_conResultados_retornaListaDTOs() {
            when(colaboradorRepository.findDistinctBySucursalesId(1L))
                    .thenReturn(List.of(colaborador));
            when(colaboradorValidaciones.convertirADTO(colaborador)).thenReturn(colaboradorDTO);

            List<ColaboradorDTO> resultado = colaboradorService.findBySucursales(1L);

            assertNotNull(resultado);
            assertEquals(1, resultado.size());
        }

        @Test
        @DisplayName("Debe retornar lista vacía cuando no hay colaboradores en la sucursal")
        void findBySucursales_sinResultados_retornaListaVacia() {
            when(colaboradorRepository.findDistinctBySucursalesId(99L))
                    .thenReturn(List.of());

            List<ColaboradorDTO> resultado = colaboradorService.findBySucursales(99L);

            assertTrue(resultado.isEmpty());
        }
    }

    // ========== findByComuna ==========

    @Test
    @DisplayName("findByComuna - Debe retornar colaboradores asociados a una comuna")
    void findByComuna_retornaColaboradores() {
        when(colaboradorRepository.findDistinctBySucursalesComunaId(1L))
                .thenReturn(List.of(colaborador));
        when(colaboradorValidaciones.convertirADTO(colaborador)).thenReturn(colaboradorDTO);

        List<ColaboradorDTO> resultado = colaboradorService.findByComuna(1L);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    // ========== findByRegion ==========

    @Test
    @DisplayName("findByRegion - Debe retornar colaboradores asociados a una región")
    void findByRegion_retornaColaboradores() {
        when(colaboradorRepository.findDistinctBySucursalesComunaRegionId(1L))
                .thenReturn(List.of(colaborador));
        when(colaboradorValidaciones.convertirADTO(colaborador)).thenReturn(colaboradorDTO);

        List<ColaboradorDTO> resultado = colaboradorService.findByRegion(1L);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }
}
