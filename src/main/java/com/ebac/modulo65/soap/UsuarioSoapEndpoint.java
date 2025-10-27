package com.ebac.modulo65.soap;

import com.ebac.modulo65.dto.Usuario;
import com.ebac.modulo65.service.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
@Component
public class UsuarioSoapEndpoint {

    private static final Logger log = LoggerFactory.getLogger(UsuarioSoapEndpoint.class);
    private static final String NAMESPACE_URI = "http://ebac.com/modulo65/soap";

    private final UsuarioService usuarioService;

    public UsuarioSoapEndpoint(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUsuarioRequest")
    @ResponsePayload
    public GetUsuarioResponse getUsuario(@RequestPayload GetUsuarioRequest request) {
        log.info("🔍 Recibida solicitud SOAP para obtener usuario con id {}", request.getIdUsuario());

        Usuario usuario = usuarioService.obtenerUsuario(request.getIdUsuario());

        GetUsuarioResponse response = new GetUsuarioResponse();
        if (usuario != null) {
            response.setUsuario(usuario);
            log.info("✅ Usuario encontrado vía SOAP: {}", usuario.getNombre());
        } else {
            response.setUsuario(null);
            log.warn("⚠️ Usuario con id {} no encontrado vía SOAP", request.getIdUsuario());
        }

        return response;
    }
}