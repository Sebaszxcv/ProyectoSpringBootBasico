package com.ebac.modulo65.service;

import com.ebac.modulo65.dto.Usuario;
import com.ebac.modulo65.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private static final Logger log = LoggerFactory.getLogger(UsuarioService.class);

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> obtenerUsuarios() {
        log.info("📋 Obteniendo todos los usuarios desde la base de datos");
        List<Usuario> usuarios = usuarioRepository.findAll();
        log.debug("✅ Se encontraron {} usuarios", usuarios.size());
        return usuarios;
    }

    public Usuario obtenerUsuario(int id) {
        log.info("🔍 Buscando usuario con ID: {}", id);
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);

        if (usuarioOpt.isPresent()) {
            log.debug("✅ Usuario encontrado: {}", usuarioOpt.get().getNombre());
            return usuarioOpt.get();
        } else {
            log.warn("⚠️ Usuario con ID {} no encontrado", id);
            return null;
        }
    }

    public Usuario guardarUsuario(Usuario usuario) {
        log.info("💾 Intentando guardar usuario: {}", usuario.getNombre());

        // Validación básica
        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
            log.error("❌ No se puede guardar usuario: nombre vacío o nulo");
            throw new IllegalArgumentException("El nombre del usuario no puede estar vacío");
        }

        if (usuario.getEdad() < 0 || usuario.getEdad() > 150) {
            log.error("❌ No se puede guardar usuario: edad inválida: {}", usuario.getEdad());
            throw new IllegalArgumentException("La edad debe estar entre 0 y 150 años");
        }

        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        log.info("✅ Usuario guardado exitosamente - ID: {}, Nombre: {}",
                usuarioGuardado.getIdUsuario(), usuarioGuardado.getNombre());
        return usuarioGuardado;
    }

    public boolean existeUsuario(int id) {
        log.debug("🔎 Verificando existencia de usuario ID: {}", id);
        return usuarioRepository.existsById(id);
    }

    public void eliminarUsuario(int id) {
        log.info("🗑️ Intentando eliminar usuario ID: {}", id);
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            log.info("✅ Usuario ID: {} eliminado exitosamente", id);
        } else {
            log.warn("⚠️ No se puede eliminar: usuario ID {} no existe", id);
            throw new IllegalArgumentException("Usuario no encontrado para eliminar");
        }
    }
}