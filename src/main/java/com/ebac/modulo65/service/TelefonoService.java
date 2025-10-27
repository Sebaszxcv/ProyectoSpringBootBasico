package com.ebac.modulo65.service;

import com.ebac.modulo65.dto.Telefono;
import com.ebac.modulo65.dto.Usuario;
import com.ebac.modulo65.repository.TelefonoRepository;
import com.ebac.modulo65.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TelefonoService {

    private static final Logger log = LoggerFactory.getLogger(TelefonoService.class);

    private final TelefonoRepository telefonoRepository;
    private final UsuarioRepository usuarioRepository;

    public TelefonoService(TelefonoRepository telefonoRepository, UsuarioRepository usuarioRepository) {
        this.telefonoRepository = telefonoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Telefono> obtenerTelefonos() {
        log.info("📞 Obteniendo todos los teléfonos desde la base de datos");
        List<Telefono> telefonos = telefonoRepository.findAll();
        log.debug("✅ Se encontraron {} teléfonos", telefonos.size());
        return telefonos;
    }

    public Telefono guardarTelefono(Telefono telefono, Usuario usuario) {
        log.info("💾 Intentando guardar teléfono para usuario: {}", usuario.getNombre());

        // Validaciones
        if (telefono.getNumero() == null || telefono.getNumero().trim().isEmpty()) {
            log.error("❌ No se puede guardar teléfono: número vacío o nulo");
            throw new IllegalArgumentException("El número de teléfono no puede estar vacío");
        }

        if (telefono.getTipoTelefono() == null || telefono.getTipoTelefono().trim().isEmpty()) {
            log.error("❌ No se puede guardar teléfono: tipo de teléfono vacío o nulo");
            throw new IllegalArgumentException("El tipo de teléfono no puede estar vacío");
        }

        telefono.setUsuario(usuario);
        Telefono telefonoGuardado = telefonoRepository.save(telefono);

        log.info("✅ Teléfono guardado exitosamente - ID: {}, Número: {}, Usuario: {}",
                telefonoGuardado.getIdTelefono(), telefonoGuardado.getNumero(), usuario.getNombre());
        return telefonoGuardado;
    }

    public List<Telefono> obtenerTelefonosPorUsuario(int usuarioId) {
        log.info("📞 Buscando teléfonos del usuario ID: {}", usuarioId);
        return telefonoRepository.findAll().stream()
                .filter(t -> t.getUsuario().getIdUsuario() == usuarioId)
                .toList();
    }

    public void eliminarTelefono(int id) {
        log.info("🗑️ Intentando eliminar teléfono ID: {}", id);
        if (telefonoRepository.existsById(id)) {
            telefonoRepository.deleteById(id);
            log.info("✅ Teléfono ID: {} eliminado exitosamente", id);
        } else {
            log.warn("⚠️ No se puede eliminar: teléfono ID {} no existe", id);
            throw new IllegalArgumentException("Teléfono no encontrado para eliminar");
        }
    }
}