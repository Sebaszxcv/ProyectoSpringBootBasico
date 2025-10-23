package com.ebac.modulo61.controller;

import com.ebac.modulo61.dto.Usuario;
import com.ebac.modulo61.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usuario = new Usuario();
        usuario.setIdUsuario(1L);
        usuario.setNombre("Sebastián");
        usuario.setEdad(25);
    }

    @Test
    void testObtenerUsuarios() {
        when(usuarioService.obtenerUsuarios()).thenReturn(Arrays.asList(usuario));
        List<Usuario> resultado = usuarioController.obtenerUsuarios();
        assertEquals(1, resultado.size());
        assertEquals("Sebastián", resultado.get(0).getNombre());
        verify(usuarioService, times(1)).obtenerUsuarios();
    }

    @Test
    void testObtenerUsuarioPorId() {
        when(usuarioService.obtenerUsuarioPorId(1L)).thenReturn(Optional.of(usuario));
        ResponseEntity<Usuario> respuesta = usuarioController.obtenerUsuarioPorId(1L);
        assertTrue(respuesta.getStatusCode().is2xxSuccessful());
        assertEquals("Sebastián", respuesta.getBody().getNombre());
        verify(usuarioService, times(1)).obtenerUsuarioPorId(1L);
    }

    @Test
    void testCrearUsuario() {
        when(usuarioService.crearUsuario(any(Usuario.class))).thenReturn(usuario);
        ResponseEntity<Usuario> respuesta = usuarioController.crearUsuario(usuario);
        assertEquals(201, respuesta.getStatusCode().value());
        assertEquals("Sebastián", respuesta.getBody().getNombre());
        verify(usuarioService, times(1)).crearUsuario(any(Usuario.class));
    }

    @Test
    void testActualizarUsuario() {
        when(usuarioService.obtenerUsuarioPorId(1L)).thenReturn(Optional.of(usuario));
        doNothing().when(usuarioService).actualizarUsuario(any(Usuario.class));
        ResponseEntity<Usuario> respuesta = usuarioController.actualizarUsuario(1L, usuario);
        assertEquals(200, respuesta.getStatusCode().value());
        verify(usuarioService, times(1)).actualizarUsuario(any(Usuario.class));
    }

    @Test
    void testEliminarUsuario() {
        doNothing().when(usuarioService).eliminarUsuario(1L);
        ResponseEntity<Void> respuesta = usuarioController.eliminarUsuario(1L);
        assertEquals(204, respuesta.getStatusCode().value());
        verify(usuarioService, times(1)).eliminarUsuario(1L);
    }
}
