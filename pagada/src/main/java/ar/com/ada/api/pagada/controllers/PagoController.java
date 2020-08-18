package ar.com.ada.api.pagada.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.pagada.entities.Pago;
import ar.com.ada.api.pagada.models.response.GenericResponse;
import ar.com.ada.api.pagada.models.response.PagoResponse;
import ar.com.ada.api.pagada.services.ServicioService;

@RestController
public class PagoController {
    
    @Autowired
    ServicioService servicioService;


    /*
    Obtener Info de un pago
	GET /api/pagos/{id}: devuelve la info basica de un pago. Esta info tiene que tener la siguiente estructura:

		{
            "empresaId": 92222,
            "nombreEmpresa": "NombreEmpresa",
            "deudorId": 3393,
            "nombreDeudor": "Nombre del deudor",
            "comprobanteDePago": 9999, <- es el id de Pago
            "fecha": "2020-06-20", <- Fecha de pago
            "importePagado": 393993,
            "medioPago": transferencia
            "infoMedioPago": debera devolver asi: "*123", donde "123" son los ultimos 3 digitos del medio de pago.
		}
     */

    
    @GetMapping("/api/pagos/{pagoId}")
    public ResponseEntity<?> buscarPagoPorId(@PathVariable Integer pagoId) {
        
        PagoResponse pagoR = new PagoResponse();
        GenericResponse genericResponse = new GenericResponse();

        Pago pagoEncontrado = servicioService.buscarPagoPorId(pagoId);

        if(pagoEncontrado == null){

            genericResponse.isOk = false;
            genericResponse.message = "No se encontro el pago.";

            return ResponseEntity.badRequest().body(genericResponse);
        }

        pagoR.empresaId = pagoEncontrado.getServicio().getEmpresa().getEmpresaId();
        pagoR.nombreEmpresa = pagoEncontrado.getServicio().getEmpresa().getNombre();
        pagoR.deudorId = pagoEncontrado.getServicio().getDeudor().getDeudorId();
        pagoR.nombreDeudor = pagoEncontrado.getServicio().getDeudor().getNombre();
        pagoR.comprobanteDePago = pagoEncontrado.getPagoId();
        pagoR.fecha = pagoEncontrado.getFechaPago();
        pagoR.importePagado = pagoEncontrado.getImportePagado();
        pagoR.medioPago = pagoEncontrado.getMedioPago();
        pagoR.infoMedioPago = pagoEncontrado.ofuscarInfoMedioPago();

        return ResponseEntity.ok(pagoR);

    }
}