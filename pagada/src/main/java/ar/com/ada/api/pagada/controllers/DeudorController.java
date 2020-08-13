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
import ar.com.ada.api.pagada.services.DeudorService.DeudorValidacionEnum;

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

        Deudor deu = new Deudor();
        deu.setPaisId(deuReq.paisId);
        deu.setTipoIdImpositivo(deuReq.tipoIdImpositivo);
        deu.setIdImpositivo(deuReq.idImpositivo);
        deu.setNombre(deuReq.nombre);

        DeudorValidacionEnum resultadoValidacion = deudorService.validarEmpresa(deu);
        if (resultadoValidacion != DeudorValidacionEnum.OK) {
            r.isOk = false;
            r.message = "No se pudo validar la empresa " + resultadoValidacion.toString();

            return ResponseEntity.badRequest().body(r); // http 400
        }

        deudorService.crearDeudor(deu);

        if (deu.getDeudorId() != null) {

            r.isOk = true;
            r.id = deu.getDeudorId();
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