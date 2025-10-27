package com.ebac.modulo65.controller;

import com.ebac.modulo65.dto.Usuario;
import com.ebac.modulo65.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    private Usuario usuario1;
    private Usuario usuario2;

    @BeforeEach
    void setUp() {
        usuario1 = new Usuario("Juan Perez", 25);
        usuario1.setIdUsuario(1);

        usuario2 = new Usuario("Maria Garcia", 30);
        usuario2.setIdUsuario(2);
    }

    @Test
    void obtenerUsuarios_CuandoExistenUsuarios_DeberiaRetornarLista() {
        // Arrange
        List<Usuario> usuariosEsperados = Arrays.asList(usuario1, usuario2);
        when(usuarioService.obtenerUsuarios()).thenReturn(usuariosEsperados);

        // Act
        ResponseWrapper<List<Usuario>> wrapper = usuarioController.obtenerUsuarios();

        // Assert
        assertTrue(wrapper.isSuccess());
        assertEquals("Usuarios obtenidos", wrapper.getMessage());
        assertEquals(HttpStatus.OK, wrapper.getResponseEntity().getStatusCode());
        assertEquals(2, wrapper.getResponseEntity().getBody().size());
        verify(usuarioService, times(1)).obtenerUsuarios();
    }

    @Test
    void obtenerUsuarios_CuandoNoExistenUsuarios_DeberiaRetornarListaVacia() {
        // Arrange
        when(usuarioService.obtenerUsuarios()).thenReturn(List.of());

        // Act
        ResponseWrapper<List<Usuario>> wrapper = usuarioController.obtenerUsuarios();

        // Assert
        assertTrue(wrapper.isSuccess());
        assertEquals("Usuarios obtenidos", wrapper.getMessage());
        assertEquals(HttpStatus.OK, wrapper.getResponseEntity().getStatusCode());
        assertTrue(wrapper.getResponseEntity().getBody().isEmpty());
        verify(usuarioService, times(1)).obtenerUsuarios();
    }

    @Test
    void obtenerUsuarioPorId_CuandoUsuarioExiste_DeberiaRetornarUsuario() {
        // Arrange
        int id = 1;
        when(usuarioService.obtenerUsuario(id)).thenReturn(usuario1);

        // Act
        ResponseWrapper<Usuario> wrapper = usuarioController.obtenerUsuario(id);

        // Assert
        assertTrue(wrapper.isSuccess());
        assertEquals("Usuario obtenido", wrapper.getMessage());
        assertEquals(HttpStatus.OK, wrapper.getResponseEntity().getStatusCode());
        assertNotNull(wrapper.getResponseEntity().getBody());
        assertEquals("Juan Perez", wrapper.getResponseEntity().getBody().getNombre());
        verify(usuarioService, times(1)).obtenerUsuario(id);
    }

    @Test
    void obtenerUsuarioPorId_CuandoUsuarioNoExiste_DeberiaRetornarNotFound() {
        // Arrange
        int id = 99;
        when(usuarioService.obtenerUsuario(id)).thenReturn(null);

        // Act
        ResponseWrapper<Usuario> wrapper = usuarioController.obtenerUsuario(id);

        // Assert
        assertFalse(wrapper.isSuccess());
        assertEquals("Usuario no encontrado", wrapper.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, wrapper.getResponseEntity().getStatusCode());
        assertNull(wrapper.getResponseEntity().getBody());
        verify(usuarioService, times(1)).obtenerUsuario(id);
    }

    @Test
    void guardarUsuario_CuandoUsuarioValido_DeberiaGuardarYRetornarUsuario() {
        // Arrange
        Usuario nuevoUsuario = new Usuario("Carlos Lopez", 28);
        when(usuarioService.guardarUsuario(any(Usuario.class))).thenReturn(usuario1);

        // Act
        ResponseWrapper<Usuario> wrapper = usuarioController.guardarUsuario(nuevoUsuario);

        // Assert
        assertTrue(wrapper.isSuccess());
        assertEquals("Usuario guardado", wrapper.getMessage());
        assertEquals(HttpStatus.OK, wrapper.getResponseEntity().getStatusCode());
        assertNotNull(wrapper.getResponseEntity().getBody());
        assertEquals(1, wrapper.getResponseEntity().getBody().getIdUsuario());
        verify(usuarioService, times(1)).guardarUsuario(nuevoUsuario);
    }

    @Test
    void guardarUsuario_CuandoNombreEsNulo_DeberiaManejarExcepcion() {
        // Arrange
        Usuario usuarioInvalido = new Usuario(null, 25);
        when(usuarioService.guardarUsuario(any(Usuario.class)))
                .thenThrow(new IllegalArgumentException("El nombre del usuario no puede estar vacío"));

        // Act
        ResponseWrapper<Usuario> wrapper = usuarioController.guardarUsuario(usuarioInvalido);

        // Assert
        assertFalse(wrapper.isSuccess());
        assertEquals("Error: El nombre del usuario no puede estar vacío", wrapper.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, wrapper.getResponseEntity().getStatusCode());
    }

    @Test
    void guardarUsuario_CuandoEdadEsInvalida_DeberiaManejarExcepcion() {
        // Arrange
        Usuario usuarioInvalido = new Usuario("Test", -5);
        when(usuarioService.guardarUsuario(any(Usuario.class)))
                .thenThrow(new IllegalArgumentException("La edad debe estar entre 0 y 150 años"));

        // Act
        ResponseWrapper<Usuario> wrapper = usuarioController.guardarUsuario(usuarioInvalido);

        // Assert
        assertFalse(wrapper.isSuccess());
        assertTrue(wrapper.getMessage().contains("La edad debe estar entre 0 y 150 años"));
        assertEquals(HttpStatus.BAD_REQUEST, wrapper.getResponseEntity().getStatusCode());
    }

    @Test
    void eliminarUsuario_CuandoUsuarioExiste_DeberiaEliminarCorrectamente() {
        // Arrange
        int id = 1;
        doNothing().when(usuarioService).eliminarUsuario(id);

        // Act
        ResponseWrapper<Void> wrapper = usuarioController.eliminarUsuario(id);

        // Assert
        assertTrue(wrapper.isSuccess());
        assertEquals("Usuario eliminado exitosamente", wrapper.getMessage());
        assertEquals(HttpStatus.OK, wrapper.getResponseEntity().getStatusCode());
        verify(usuarioService, times(1)).eliminarUsuario(id);
    }

    @Test
    void eliminarUsuario_CuandoUsuarioNoExiste_DeberiaRetornarNotFound() {
        // Arrange
        int id = 99;
        doThrow(new IllegalArgumentException("Usuario no encontrado para eliminar"))
                .when(usuarioService).eliminarUsuario(id);

        // Act
        ResponseWrapper<Void> wrapper = usuarioController.eliminarUsuario(id);

        // Assert
        assertFalse(wrapper.isSuccess());
        assertEquals("Usuario no encontrado para eliminar", wrapper.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, wrapper.getResponseEntity().getStatusCode());
        verify(usuarioService, times(1)).eliminarUsuario(id);
    }
}