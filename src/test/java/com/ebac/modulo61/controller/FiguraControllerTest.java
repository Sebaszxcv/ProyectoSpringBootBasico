package com.ebac.modulo61.controller;

import com.ebac.modulo61.service.FiguraService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class FiguraControllerTest {

    @Mock
    private FiguraService figuraService;

    @InjectMocks
    private FiguraController figuraController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtenerArea() {
        when(figuraService.mostrarArea()).thenReturn(15.0);
        String resultado = figuraController.obtenerArea();
        assertEquals("Área calculada: 15.0", resultado);
        verify(figuraService, times(1)).mostrarArea();
    }
}
