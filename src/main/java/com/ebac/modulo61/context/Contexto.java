package com.ebac.modulo61.context;

import com.ebac.modulo61.service.ServicioAutowired;
import com.ebac.modulo61.service.ServicioConstructor;
import com.ebac.modulo61.service.ServicioSetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Contexto {

    @Autowired
    private ServicioSetter servicioSetter;

    @Autowired
    private ServicioConstructor servicioConstructor;

    @Autowired
    private ServicioAutowired servicioAutowired;

    public void ejecutarServicios() {
        System.out.println(servicioSetter.ejecutarConsulta());
        System.out.println(servicioConstructor.ejecutarConsulta());
        System.out.println(servicioAutowired.ejecutarConsulta());
    }
}
