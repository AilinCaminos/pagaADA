package ar.com.ada.api.pagada.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.pagada.entities.TipoServicio;
import ar.com.ada.api.pagada.models.response.GenericResponse;
import ar.com.ada.api.pagada.services.ServicioService;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class TipoServicioController {

    @Autowired
    ServicioService servicioService;
    /*
     * Crear/Obtener TipoServicios 
     * GET /api/tipos-servicios
     * POST /api/tipos-servicios
     */

    @PostMapping("/api/tipos-servicios")
    public ResponseEntity<GenericResponse> crearTipoServicio(@RequestBody TipoServicio tipo) {

        GenericResponse r = new GenericResponse();

        boolean resultado = servicioService.crearTipoServicio(tipo);

        if (resultado) {

            r.isOk = true;
            r.id = tipo.getTipoServicioId();
            r.message = "Se creo con exito el Tipo de Servicio";

            return ResponseEntity.ok(r);

        }

        r.isOk = false;
        r.message = "El Tipo de Servicio ya existe";

        return ResponseEntity.badRequest().body(r);

    }

    @GetMapping("/api/tipos-servicios")
    public ResponseEntity<List<TipoServicio>> listarTiposDeServicios() {

        return ResponseEntity.ok(servicioService.listarTiposDeServicios());

    }

}