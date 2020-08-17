package ar.com.ada.api.pagada.models.request;

import java.math.BigDecimal;
import java.util.Date;

import ar.com.ada.api.pagada.entities.Pago.MedioPagoEnum;

public class InfoPagoRequest {

    public BigDecimal importePagado;
    public String moneda;
    public Date fechaPago;
    public MedioPagoEnum medioPago;
    public String infoMedioPago;

    
}