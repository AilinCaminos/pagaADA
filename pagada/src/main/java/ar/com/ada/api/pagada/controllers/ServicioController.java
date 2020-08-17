package ar.com.ada.api.pagada.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.pagada.entities.Deudor;
import ar.com.ada.api.pagada.entities.Empresa;
import ar.com.ada.api.pagada.entities.OperacionPago;
import ar.com.ada.api.pagada.entities.Servicio;
import ar.com.ada.api.pagada.entities.TipoServicio;
import ar.com.ada.api.pagada.models.request.InfoPagoRequest;
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
    public ResponseEntity<GenericResponse> crearServicio(@RequestBody ServicioRequest servReq) {

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

        if (servicio.getServicioId() != null) {

            r.isOk = true;
            r.id = servicio.getServicioId();
            r.message = "Se creo el Servicio con exito";

            return ResponseEntity.ok(r);
        }

        r.isOk = false;
        r.message = "Ocurrio un error. No se pudo crear el Servicio.";

        return ResponseEntity.badRequest().body(r);
    }
    
    /**
    * Obtener servicios:
    *
    * GET /api/servicios : obtiene todos los servicios.
	* GET /api/servicios?empresa=999 : obtiene todos los servicios PENDIENTES de una empresa especifica Formato JSon Esperado: List<Servicio>
	* GET /api/servicios?empresa=999&deudor=888: obtiene todos los servicios PENDIENTES de una empresa y un deudor Formato JSon Esperado: List<Servicio>
	* GET /api/servicios?empresa=999&deudor=888&historico=true: obtiene todos los servicios de una empresa y un deudor(pagados, anulados o pendientes, o sea todos)
	* GET /api/servicios?codigo=AAAAAAA : obtiene un servicio en particular usando el codigo de Barras.
    */

    @GetMapping("/api/servicios")
    public ResponseEntity<List<Servicio>> listarServicios(
            @RequestParam(name = "empresa", required = false) Integer empresaId,
            @RequestParam(name = "deudor", required = false) Integer deudorId,
            @RequestParam(name = "historico", required = false) boolean historico,
            @RequestParam(name = "codigo", required = false) String codigoBarras) {

        if (codigoBarras != null) {
            return ResponseEntity.ok(servicioService.listarServiciosPorCodigoDeBarras(codigoBarras));
        } else if (empresaId == null) {
            return ResponseEntity.ok(servicioService.listarServicios());
        } else if (deudorId == null) {
            return ResponseEntity.ok(servicioService.listarServiciosPendientesPorEmpresaId(empresaId));
        } else if (historico) {
            return ResponseEntity
                    .ok(servicioService.listarHistoricoServiciosPorEmpresaIdYDeudorId(empresaId, deudorId));
        }
        return ResponseEntity.ok(servicioService.listarServiciosPendientesPorEmpresaIdYDeudorId(empresaId, deudorId));

    }

    /**
         * Pagar Servicio:
	    POST /api/servicios/{id}: paga un servicio especifico con el siguiente Payload(RequestBody):
		{
		  "importePagado": 3999.00,
                  "fechaPago": "2020-05-06",
	          "medioPago": "TRANSFERENCIA", //TARJETA, DEPOSITO, ETC
	          "infoMedioPago": "nroTarjeta/cbu/etc"
		}
         */

    @PostMapping("/api/servicios/{id}")
    public ResponseEntity<GenericResponse> pagarServicio(@PathVariable Integer id, @RequestBody InfoPagoRequest pago) {

        GenericResponse r = new GenericResponse();

        OperacionPago resultadoPago = servicioService.realizarPago(id, pago.importePagado, pago.moneda, pago.fechaPago,
                pago.medioPago, pago.infoMedioPago);

        switch (resultadoPago.getResultado()) {
            case RECHAZADO_NO_ACEPTA_PAGO_PARCIAL:

                r.isOk = false;
                r.message = "No acepta pago parcial";

                return ResponseEntity.badRequest().body(r);

            case RECHAZADO_SERVICIO_INEXISTENTE:

                r.isOk = false;
                r.message = "Servicio inexistente";

                return ResponseEntity.badRequest().body(r);

            case RECHAZADO_SERVICIO_YA_PAGO:

                r.isOk = false;
                r.message = "Servicio ya pago";

                return ResponseEntity.badRequest().body(r);

            case ERROR_INESPERADO:

                r.isOk = false;
                r.message = "Error inesperado";

                return ResponseEntity.badRequest().body(r);

            case REALIZADO:

                r.isOk = true;
                r.id = resultadoPago.getPago().getPagoId();
                r.message = "se realizo el pago con exito";
                return ResponseEntity.ok(r);
        }

        return ResponseEntity.badRequest().build();
    }


}