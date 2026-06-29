package com.RRHH.ColaboradoresSucursales.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
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

import com.RRHH.ColaboradoresSucursales.DTO.ColaboradorDTO;
import com.RRHH.ColaboradoresSucursales.model.Colaborador;
import com.RRHH.ColaboradoresSucursales.model.Comuna;
import com.RRHH.ColaboradoresSucursales.model.Region;
import com.RRHH.ColaboradoresSucursales.model.Sucursal;

@ExtendWith(MockitoExtension.class)
@DisplayName("ColaboradorValidaciones - Pruebas Unitarias")
class ColaboradorValidacionesTest {

    @Mock
    private WebClient.Builder webClientBuilder;

    @InjectMocks
    private ColaboradorValidaciones colaboradorValidaciones;

    private Colaborador colaboradorCompleto;

    @BeforeEach
    void setUp() {
        Region region = new Region(1L, "Región Metropolitana");
        Comuna comuna = new Comuna(1L, "Santiago", region);
        Sucursal sucursal = new Sucursal(1L, "Sucursal Central", "Av. Principal 100", comuna);

        colaboradorCompleto = new Colaborador();
        colaboradorCompleto.setId(1L);
        colaboradorCompleto.setRun("123456789");
        colaboradorCompleto.setNombres("Juan");
        colaboradorCompleto.setApellidos("Pérez");
        colaboradorCompleto.setFechaNacimiento(LocalDate.of(1990, 5, 15));
        colaboradorCompleto.setTelefono("912345678");
        colaboradorCompleto.setCorreo("juan@test.com");
        colaboradorCompleto.setDireccion("Calle Falsa 123");
        colaboradorCompleto.setSucursales(List.of(sucursal));
    }

    // ========== validarNullVacio ==========

    @Nested
    @DisplayName("validarNullVacio")
    class ValidarNullVacioTests {

        @Test
        @DisplayName("Debe retornar true cuando todos los campos están completos")
        void validar_todosLosCamposCompletos_retornaTrue() {
            assertTrue(colaboradorValidaciones.validarNullVacio(colaboradorCompleto));
        }

        @Test
        @DisplayName("Debe retornar false cuando el RUN es null")
        void validar_runNull_retornaFalse() {
            colaboradorCompleto.setRun(null);
            assertFalse(colaboradorValidaciones.validarNullVacio(colaboradorCompleto));
        }

        @Test
        @DisplayName("Debe retornar false cuando el RUN está vacío")
        void validar_runVacio_retornaFalse() {
            colaboradorCompleto.setRun("");
            assertFalse(colaboradorValidaciones.validarNullVacio(colaboradorCompleto));
        }

        @Test
        @DisplayName("Debe retornar false cuando el nombre es null")
        void validar_nombreNull_retornaFalse() {
            colaboradorCompleto.setNombres(null);
            assertFalse(colaboradorValidaciones.validarNullVacio(colaboradorCompleto));
        }

        @Test
        @DisplayName("Debe retornar false cuando el nombre está vacío")
        void validar_nombreVacio_retornaFalse() {
            colaboradorCompleto.setNombres("");
            assertFalse(colaboradorValidaciones.validarNullVacio(colaboradorCompleto));
        }

        @Test
        @DisplayName("Debe retornar false cuando los apellidos son null")
        void validar_apellidosNull_retornaFalse() {
            colaboradorCompleto.setApellidos(null);
            assertFalse(colaboradorValidaciones.validarNullVacio(colaboradorCompleto));
        }

        @Test
        @DisplayName("Debe retornar false cuando los apellidos están vacíos")
        void validar_apellidosVacios_retornaFalse() {
            colaboradorCompleto.setApellidos("");
            assertFalse(colaboradorValidaciones.validarNullVacio(colaboradorCompleto));
        }

        @Test
        @DisplayName("Debe retornar false cuando la fecha de nacimiento es null")
        void validar_fechaNacimientoNull_retornaFalse() {
            colaboradorCompleto.setFechaNacimiento(null);
            assertFalse(colaboradorValidaciones.validarNullVacio(colaboradorCompleto));
        }

        @Test
        @DisplayName("Debe retornar false cuando el teléfono es null")
        void validar_telefonoNull_retornaFalse() {
            colaboradorCompleto.setTelefono(null);
            assertFalse(colaboradorValidaciones.validarNullVacio(colaboradorCompleto));
        }

        @Test
        @DisplayName("Debe retornar false cuando el teléfono está vacío")
        void validar_telefonoVacio_retornaFalse() {
            colaboradorCompleto.setTelefono("");
            assertFalse(colaboradorValidaciones.validarNullVacio(colaboradorCompleto));
        }

        @Test
        @DisplayName("Debe retornar false cuando el correo es null")
        void validar_correoNull_retornaFalse() {
            colaboradorCompleto.setCorreo(null);
            assertFalse(colaboradorValidaciones.validarNullVacio(colaboradorCompleto));
        }

        @Test
        @DisplayName("Debe retornar false cuando el correo está vacío")
        void validar_correoVacio_retornaFalse() {
            colaboradorCompleto.setCorreo("");
            assertFalse(colaboradorValidaciones.validarNullVacio(colaboradorCompleto));
        }

        @Test
        @DisplayName("Debe retornar false cuando la dirección es null")
        void validar_direccionNull_retornaFalse() {
            colaboradorCompleto.setDireccion(null);
            assertFalse(colaboradorValidaciones.validarNullVacio(colaboradorCompleto));
        }

        @Test
        @DisplayName("Debe retornar false cuando la dirección está vacía")
        void validar_direccionVacia_retornaFalse() {
            colaboradorCompleto.setDireccion("");
            assertFalse(colaboradorValidaciones.validarNullVacio(colaboradorCompleto));
        }

        @Test
        @DisplayName("Debe retornar false cuando las sucursales son null")
        void validar_sucursalesNull_retornaFalse() {
            colaboradorCompleto.setSucursales(null);
            assertFalse(colaboradorValidaciones.validarNullVacio(colaboradorCompleto));
        }

        @Test
        @DisplayName("Debe retornar false cuando las sucursales están vacías")
        void validar_sucursalesVacias_retornaFalse() {
            colaboradorCompleto.setSucursales(new ArrayList<>());
            assertFalse(colaboradorValidaciones.validarNullVacio(colaboradorCompleto));
        }
    }

    // ========== convertirADTO ==========

    @Nested
    @DisplayName("convertirADTO")
    class ConvertirADTOTests {

        @Test
        @DisplayName("Debe retornar null cuando el colaborador es null")
        void convertir_colaboradorNull_retornaNull() {
            ColaboradorDTO resultado = colaboradorValidaciones.convertirADTO(null);
            assertNull(resultado);
        }

        @Test
        @DisplayName("Debe convertir correctamente un colaborador completo a DTO")
        void convertir_colaboradorCompleto_retornaDTOCorrecto() {
            ColaboradorDTO dto = colaboradorValidaciones.convertirADTO(colaboradorCompleto);

            assertNotNull(dto);
            assertEquals(1L, dto.getId());
            assertEquals("123456789", dto.getRun());
            assertEquals("Juan", dto.getNombres());
            assertEquals("Pérez", dto.getApellidos());
            assertEquals(LocalDate.of(1990, 5, 15), dto.getFechaNacimiento());
            assertEquals("912345678", dto.getTelefono());
            assertEquals("juan@test.com", dto.getCorreo());
            assertEquals("Calle Falsa 123", dto.getDireccion());
        }

        @Test
        @DisplayName("Debe incluir las sucursales en formato texto en el DTO")
        void convertir_conSucursales_incluyeSucursalesEnDTO() {
            ColaboradorDTO dto = colaboradorValidaciones.convertirADTO(colaboradorCompleto);

            assertNotNull(dto.getSucursales());
            assertEquals(1, dto.getSucursales().size());
            assertTrue(dto.getSucursales().get(0).contains("Sucursal Central"));
            assertTrue(dto.getSucursales().get(0).contains("ID: 1"));
        }

        @Test
        @DisplayName("Debe retornar lista vacía de sucursales cuando son null")
        void convertir_sucursalesNull_listaVacia() {
            colaboradorCompleto.setSucursales(null);
            ColaboradorDTO dto = colaboradorValidaciones.convertirADTO(colaboradorCompleto);

            assertNotNull(dto.getSucursales());
            assertTrue(dto.getSucursales().isEmpty());
        }

        @Test
        @DisplayName("Debe retornar lista vacía de sucursales cuando no tiene ninguna")
        void convertir_sinSucursales_listaVacia() {
            colaboradorCompleto.setSucursales(new ArrayList<>());
            ColaboradorDTO dto = colaboradorValidaciones.convertirADTO(colaboradorCompleto);

            assertNotNull(dto.getSucursales());
            assertTrue(dto.getSucursales().isEmpty());
        }
    }
}
