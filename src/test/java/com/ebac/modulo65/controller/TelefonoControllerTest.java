package com.ebac.modulo65.controller;

import com.ebac.modulo65.dto.Telefono;
import com.ebac.modulo65.dto.Usuario;
import com.ebac.modulo65.service.TelefonoService;
import com.ebac.modulo65.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TelefonoControllerTest {

    @Mock
    private TelefonoService telefonoService;

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private TelefonoController telefonoController;

    @Test
    void guardarTelefonoCorrectamente() {
        // Arrange
        Usuario usuario = new Usuario("Juan", 25);
        usuario.setIdUsuario(1);

        Telefono telefono = new Telefono();
        telefono.setNumero("123456789");
        telefono.setLada("55");
        telefono.setTipoTelefono("Celular");

        when(usuarioService.obtenerUsuario(1)).thenReturn(usuario);
        when(telefonoService.guardarTelefono(telefono, usuario)).thenReturn(telefono);

        // Act
        ResponseWrapper<Telefono> wrapper = telefonoController.guardarTelefono(1, telefono);

        // Assert
        assertTrue(wrapper.isSuccess());
        assertEquals("Teléfono guardado exitosamente", wrapper.getMessage());
        assertEquals(telefono, wrapper.getResponseEntity().getBody());
        verify(usuarioService, times(1)).obtenerUsuario(1);
        verify(telefonoService, times(1)).guardarTelefono(telefono, usuario);
    }

    @Test
    void guardarTelefonoUsuarioNoExiste() {
        // Arrange
        Telefono telefono = new Telefono();
        telefono.setNumero("123456789");

        when(usuarioService.obtenerUsuario(99)).thenReturn(null);

        // Act
        ResponseWrapper<Telefono> wrapper = telefonoController.guardarTelefono(99, telefono);

        // Assert
        assertFalse(wrapper.isSuccess());
        assertEquals("Usuario no encontrado", wrapper.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, wrapper.getResponseEntity().getStatusCode());
        assertNull(wrapper.getResponseEntity().getBody());
        verify(usuarioService, times(1)).obtenerUsuario(99);
        verify(telefonoService, never()).guardarTelefono(any(), any());
    }

    @Test
    void guardarTelefonoConValidacionFallida() {
        // Arrange
        Usuario usuario = new Usuario("Juan", 25);
        usuario.setIdUsuario(1);

        Telefono telefono = new Telefono(); // Sin número, debería fallar

        when(usuarioService.obtenerUsuario(1)).thenReturn(usuario);
        when(telefonoService.guardarTelefono(telefono, usuario))
                .thenThrow(new IllegalArgumentException("El número de teléfono no puede estar vacío"));

        // Act
        ResponseWrapper<Telefono> wrapper = telefonoController.guardarTelefono(1, telefono);

        // Assert
        assertFalse(wrapper.isSuccess());
        assertEquals("El número de teléfono no puede estar vacío", wrapper.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, wrapper.getResponseEntity().getStatusCode());
    }
}