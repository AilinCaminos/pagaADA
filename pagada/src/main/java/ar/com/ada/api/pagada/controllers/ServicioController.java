package ar.com.ada.api.pagada.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.pagada.entities.Deudor;
import ar.com.ada.api.pagada.entities.Empresa;
import ar.com.ada.api.pagada.entities.Servicio;
import ar.com.ada.api.pagada.entities.TipoServicio;
import ar.com.ada.api.pagada.models.request.ServicioRequest;
import ar.com.ada.api.pagada.models.response.GenericResponse;
import ar.com.ada.api.pagada.services.DeudorService;
import ar.com.ada.api.pagada.services.EmpresaService;
import ar.com.ada.api.pagada.services.ServicioService;
import ar.com.ada.api.pagada.services.ServicioService.ServicioValidacionEnum;

@RestController
public class ServicioController {

    @Autowired
    ServicioService servicioService;
    @Autowired
    EmpresaService empresaService;
    @Autowired
    DeudorService deudorService;

    /*
    POST /api/servicios
     */

    @PostMapping("/api/servicios")
    public ResponseEntity<GenericResponse> crearServicio(@RequestBody ServicioRequest servReq){

        GenericResponse r = new GenericResponse();

        Servicio servicio = new Servicio();
        Empresa empresaEncontrada = empresaService.buscarEmpresaPorId(servReq.empresaId);
        servicio.setEmpresa(empresaEncontrada);
        Deudor deudorEncontado = deudorService.buscarDeudorPorId(servReq.deudorId);
        servicio.setDeudor(deudorEncontado);
        TipoServicio tServicioEncontrado = servicioService.buscarTipoServicioPorId(servReq.tipoServicioId);
        servicio.setTipoServicio(tServicioEncontrado);
        servicio.setTipoComprobante(servReq.tipoComprobanteId);
        servicio.setNumero(servReq.numero);
        servicio.setFechaEmision(servReq.fechaEmision);
        servicio.setFechaVencimiento(servReq.fechaVencimiento);
        servicio.setImporte(servReq.importe);
        servicio.setMoneda(servReq.moneda);
        servicio.setCodigoBarras(servReq.codigoDeBarras);
        servicio.setEstadoId(servReq.estadoId);

        ServicioValidacionEnum resultadoValidacion = servicioService.validacionServicio(servicio);
        if (resultadoValidacion != ServicioValidacionEnum.OK) {
            r.isOk = false;
            r.message = "No se pudo validar el Servicio " + resultadoValidacion.toString();

            return ResponseEntity.badRequest().body(r);
        }

        servicioService.crearServicio(servicio);

        if(servicio.getServicioId() != null){

            r.isOk = true;
            r.id = servicio.getServicioId();
            r.message = "Se creo el Servicio con exito";

            return ResponseEntity.ok(r);
        }

        r.isOk = false;
        r.message = "Ocurrio un error. No se pudo crear el Servicio.";

        return ResponseEntity.badRequest().body(r);
    }
}