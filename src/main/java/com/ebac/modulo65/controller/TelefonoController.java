package com.ebac.modulo65.controller;

import com.ebac.modulo65.dto.Telefono;
import com.ebac.modulo65.dto.Usuario;
import com.ebac.modulo65.service.TelefonoService;
import com.ebac.modulo65.service.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/telefonos")
public class TelefonoController {

    private static final Logger log = LoggerFactory.getLogger(TelefonoController.class);

    private final TelefonoService telefonoService;
    private final UsuarioService usuarioService;

    public TelefonoController(TelefonoService telefonoService, UsuarioService usuarioService) {
        this.telefonoService = telefonoService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseWrapper<List<Telefono>> obtenerTelefonos() {
        log.info("📋 SOLICITUD: Obtener todos los teléfonos");
        try {
            List<Telefono> telefonos = telefonoService.obtenerTelefonos();
            log.info("✅ RESPUESTA: {} teléfonos obtenidos exitosamente", telefonos.size());
            return new ResponseWrapper<>(true, "Teléfonos obtenidos exitosamente", ResponseEntity.ok(telefonos));
        } catch (Exception e) {
            log.error("❌ ERROR al obtener teléfonos: {}", e.getMessage());
            return new ResponseWrapper<>(false, "Error al obtener teléfonos",
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
        }
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseWrapper<List<Telefono>> obtenerTelefonosPorUsuario(@PathVariable int usuarioId) {
        log.info("📋 SOLICITUD: Obtener teléfonos del usuario ID: {}", usuarioId);
        try {
            if (!usuarioService.existeUsuario(usuarioId)) {
                log.warn("⚠️ Usuario ID {} no encontrado", usuarioId);
                return new ResponseWrapper<>(false, "Usuario no encontrado",
                        ResponseEntity.status(HttpStatus.NOT_FOUND).build());
            }

            List<Telefono> telefonos = telefonoService.obtenerTelefonosPorUsuario(usuarioId);
            log.info("✅ RESPUESTA: {} teléfonos encontrados para usuario ID: {}", telefonos.size(), usuarioId);
            return new ResponseWrapper<>(true, "Teléfonos del usuario obtenidos exitosamente", ResponseEntity.ok(telefonos));
        } catch (Exception e) {
            log.error("❌ ERROR al obtener teléfonos del usuario {}: {}", usuarioId, e.getMessage());
            return new ResponseWrapper<>(false, "Error al obtener teléfonos del usuario",
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
        }
    }

    @PostMapping("/{usuarioId}")
    public ResponseWrapper<Telefono> guardarTelefono(@PathVariable int usuarioId, @RequestBody Telefono telefono) {
        log.info("📋 SOLICITUD: Guardar teléfono para usuario ID: {}", usuarioId);
        try {
            Usuario usuario = usuarioService.obtenerUsuario(usuarioId);
            if (usuario == null) {
                log.warn("❌ No se puede guardar teléfono: usuario ID {} no encontrado", usuarioId);
                return new ResponseWrapper<>(false, "Usuario no encontrado",
                        ResponseEntity.status(HttpStatus.NOT_FOUND).build());
            }

            Telefono guardado = telefonoService.guardarTelefono(telefono, usuario);
            log.info("✅ RESPUESTA: Teléfono guardado exitosamente - ID: {}, Usuario: {}",
                    guardado.getIdTelefono(), usuario.getNombre());
            return new ResponseWrapper<>(true, "Teléfono guardado exitosamente", ResponseEntity.ok(guardado));

        } catch (IllegalArgumentException e) {
            log.warn("⚠️ Validación fallida al guardar teléfono: {}", e.getMessage());
            return new ResponseWrapper<>(false, e.getMessage(),
                    ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
        } catch (Exception e) {
            log.error("❌ ERROR inesperado al guardar teléfono: {}", e.getMessage());
            return new ResponseWrapper<>(false, "Error interno del servidor",
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseWrapper<Void> eliminarTelefono(@PathVariable int id) {
        log.info("📋 SOLICITUD: Eliminar teléfono ID: {}", id);
        try {
            telefonoService.eliminarTelefono(id);
            log.info("✅ RESPUESTA: Teléfono ID {} eliminado exitosamente", id);
            return new ResponseWrapper<>(true, "Teléfono eliminado exitosamente", ResponseEntity.ok().build());
        } catch (IllegalArgumentException e) {
            log.warn("⚠️ No se puede eliminar teléfono: {}", e.getMessage());
            return new ResponseWrapper<>(false, e.getMessage(),
                    ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (Exception e) {
            log.error("❌ ERROR al eliminar teléfono ID {}: {}", id, e.getMessage());
            return new ResponseWrapper<>(false, "Error al eliminar teléfono",
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
        }
    }
}