package ar.com.ada.api.pagada.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.pagada.entities.Deudor;
import ar.com.ada.api.pagada.models.request.DeudorRequest;
import ar.com.ada.api.pagada.models.response.GenericResponse;
import ar.com.ada.api.pagada.services.DeudorService;

@RestController
public class DeudorController {

    /*
     * Crear/Obtener deudores
     * 
     * GET /api/deudores POST /api/deudores
     */

    @Autowired
    DeudorService deudorService;

    @PostMapping("/api/deudores")
    public ResponseEntity<GenericResponse> crearDeudor(@RequestBody DeudorRequest deuReq) {

        GenericResponse r = new GenericResponse();

        Deudor deudor = deudorService.crearDeudor(deuReq.paisId, deuReq.tipoIdImpositivo, deuReq.idImpositivo,
                deuReq.nombre);

        if (deudor.getDeudorId() != null) {

            r.isOk = true;
            r.id = deudor.getDeudorId();
            r.message = "Se creo el deudor con exito.";

            return ResponseEntity.ok(r);

        }

        r.isOk = false;
        r.message = "Ocurrio un error. No se pudo crear el deudor.";

        return ResponseEntity.badRequest().body(r);

    }

    @GetMapping("/api/deudores")
    public ResponseEntity<List<Deudor>> listarDeudores() {

        return ResponseEntity.ok(deudorService.listarDeudores());

    }

}