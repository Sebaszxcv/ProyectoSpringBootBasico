package com.ebac.modulo65.controller;

import com.ebac.modulo65.dto.Usuario;
import com.ebac.modulo65.service.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private static final Logger log = LoggerFactory.getLogger(UsuarioController.class);

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseWrapper<List<Usuario>> obtenerUsuarios() {
        log.info("📋 SOLICITUD: Obtener todos los usuarios");
        try {
            List<Usuario> usuarios = usuarioService.obtenerUsuarios();
            log.info("✅ RESPUESTA: {} usuarios obtenidos exitosamente", usuarios.size());
            return new ResponseWrapper<>(true, "Usuarios obtenidos", ResponseEntity.ok(usuarios));
        } catch (Exception e) {
            log.error("❌ ERROR al obtener usuarios: {}", e.getMessage());
            return new ResponseWrapper<>(false, "Error al obtener usuarios",
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
        }
    }

    @GetMapping("/{id}")
    public ResponseWrapper<Usuario> obtenerUsuario(@PathVariable int id) {
        log.info("🔍 SOLICITUD: Obtener usuario con ID: {}", id);
        try {
            Usuario usuario = usuarioService.obtenerUsuario(id);
            if (usuario == null) {
                log.warn("⚠️ Usuario con ID {} no encontrado", id);
                return new ResponseWrapper<>(false, "Usuario no encontrado", ResponseEntity.status(HttpStatus.NOT_FOUND).build());
            }
            log.info("✅ RESPUESTA: Usuario encontrado - ID: {}, Nombre: {}", id, usuario.getNombre());
            return new ResponseWrapper<>(true, "Usuario obtenido", ResponseEntity.ok(usuario));
        } catch (Exception e) {
            log.error("❌ ERROR al obtener usuario ID {}: {}", id, e.getMessage());
            return new ResponseWrapper<>(false, "Error al obtener usuario",
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
        }
    }

    @PostMapping
    public ResponseWrapper<Usuario> guardarUsuario(@RequestBody Usuario usuario) {
        log.info("💾 SOLICITUD: Guardar usuario: {}", usuario.getNombre());
        try {
            Usuario guardado = usuarioService.guardarUsuario(usuario);
            log.info("✅ RESPUESTA: Usuario guardado - ID: {}, Nombre: {}", guardado.getIdUsuario(), guardado.getNombre());
            return new ResponseWrapper<>(true, "Usuario guardado", ResponseEntity.ok(guardado));
        } catch (IllegalArgumentException e) {
            log.warn("⚠️ Validación fallida al guardar usuario: {}", e.getMessage());
            return new ResponseWrapper<>(false, "Error: " + e.getMessage(),
                    ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
        } catch (Exception e) {
            log.error("❌ ERROR inesperado al guardar usuario: {}", e.getMessage());
            return new ResponseWrapper<>(false, "Error interno del servidor",
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseWrapper<Void> eliminarUsuario(@PathVariable int id) {
        log.info("🗑️ SOLICITUD: Eliminar usuario ID: {}", id);
        try {
            usuarioService.eliminarUsuario(id);
            log.info("✅ RESPUESTA: Usuario ID {} eliminado exitosamente", id);
            return new ResponseWrapper<>(true, "Usuario eliminado exitosamente", ResponseEntity.ok().build());
        } catch (IllegalArgumentException e) {
            log.warn("⚠️ No se puede eliminar usuario: {}", e.getMessage());
            return new ResponseWrapper<>(false, e.getMessage(),
                    ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (Exception e) {
            log.error("❌ ERROR al eliminar usuario ID {}: {}", id, e.getMessage());
            return new ResponseWrapper<>(false, "Error al eliminar usuario",
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
        }
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseWrapper<Object> handleValidationExceptions(IllegalArgumentException ex) {
        log.warn("⚠️ Validación fallida: {}", ex.getMessage());
        return new ResponseWrapper<>(false, "Error: " + ex.getMessage(),
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }
}