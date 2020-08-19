package ar.com.ada.api.pagada.services;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.pagada.entities.OperacionPago;
import ar.com.ada.api.pagada.entities.Pago;
import ar.com.ada.api.pagada.entities.Servicio;
import ar.com.ada.api.pagada.entities.TipoServicio;
import ar.com.ada.api.pagada.entities.OperacionPago.OperacionPagoEnum;
import ar.com.ada.api.pagada.entities.Pago.MedioPagoEnum;
import ar.com.ada.api.pagada.entities.Servicio.EstadoEnum;
import ar.com.ada.api.pagada.repositories.PagoRepository;
import ar.com.ada.api.pagada.repositories.ServicioRepository;
import ar.com.ada.api.pagada.repositories.TipoServicioRepository;

@Service
public class ServicioService {

    @Autowired
    TipoServicioRepository tServicioRepository;
    @Autowired
    ServicioRepository servicioRepository;
    @Autowired
    PagoRepository pagoRepository;
    

    public boolean crearTipoServicio(TipoServicio tipo) {

        if (tServicioRepository.existsById(tipo.getTipoServicioId()))
            return false;

        tServicioRepository.save(tipo);

        return true;

    }

    public List<TipoServicio> listarTiposDeServicios() {

        return tServicioRepository.findAll();

    }

    public Servicio crearServicio(Servicio servicio) {

        return servicioRepository.save(servicio);

    }

    public TipoServicio buscarTipoServicioPorId(Integer tSevicioId) {

        Optional<TipoServicio> oTipoServicio = tServicioRepository.findById(tSevicioId);

        if (oTipoServicio.isPresent()) {
            return oTipoServicio.get();
        }
        return null;
    }

    public ServicioValidacionEnum validacionServicio(Servicio servicio) {

        if (servicio.getImporte().compareTo(new BigDecimal(0)) <= 0) {
            return ServicioValidacionEnum.IMPORTE_INVALIDO;
        }
        return ServicioValidacionEnum.OK;

    }

    public enum ServicioValidacionEnum {
        OK, IMPORTE_INVALIDO
    }

    /**
     * Trae todos los servicios de una empresa
     * 
     * @return
     */
    public List<Servicio> listarServicios() {

        return servicioRepository.findAll();

    }

    /***
     * Trae todos los servicios de una empresa
     * 
     * @param empresaId este parametro es el Id de la empresa
     * @return
     */

    public List<Servicio> listarServiciosPorEmpresaId(Integer empresaId) {

        return servicioRepository.findAllByEmpresaId(empresaId);

    }

    /**
     * Trae todos los servicios PENDIENTES de una empresa
     * 
     * @param empresaId
     * @return
     */
    public List<Servicio> listarServiciosPendientesPorEmpresaId(Integer empresaId) {

        return servicioRepository.findAllByPendientesEmpresaId(empresaId);

    }

    /**
     * Trae todos los servicios PENDIENTES de una empresa
     * 
     * @param empresaId
     * @param deudorId
     * @return
     */
    public List<Servicio> listarServiciosPendientesPorEmpresaIdYDeudorId(Integer empresaId, Integer deudorId) {

        return servicioRepository.findAllByPendientesEmpresaIdAndDeudorId(empresaId, deudorId);

    }

        /**
     * Trae todos los servicios PENDIENTES de una empresa
     * 
     * @param empresaId
     * @param deudorId
     * @return
     */
    public List<Servicio> listarHistoricoServiciosPorEmpresaIdYDeudorId(Integer empresaId, Integer deudorId) {

        return servicioRepository.findAllByEmpresaIdAndDeudorIdHistorico(empresaId, deudorId);

    }

    public List<Servicio> listarServiciosPorCodigoDeBarras(String codigoDeBarras){

        return servicioRepository.findAllByCodigoBarras(codigoDeBarras);

    }

    public OperacionPago realizarPago(Integer servicioId, BigDecimal importePagado, String moneda, Date fechaPago,
            MedioPagoEnum medioPago, String infoMedioPago) {

        OperacionPago opPago = new OperacionPago();

        Servicio servicio = buscarServicioPorId(servicioId);
        if (servicio == null) {
            opPago.setResultado(OperacionPagoEnum.RECHAZADO_SERVICIO_INEXISTENTE);
            return opPago;
        }

        if (servicio.getEstadoId() != EstadoEnum.PENDIENTE) {

            opPago.setResultado(OperacionPagoEnum.RECHAZADO_SERVICIO_YA_PAGO);
            return opPago;
        }

        if (servicio.getImporte().compareTo(importePagado) != 0) {

            opPago.setResultado(OperacionPagoEnum.RECHAZADO_NO_ACEPTA_PAGO_PARCIAL);
            return opPago;
        }

        Pago pago = new Pago();
        pago.setImportePagado(importePagado);
        pago.setMoneda(moneda);
        pago.setFechaPago(fechaPago);
        pago.setMedioPago(medioPago);
        pago.setInfoMedioPago(infoMedioPago);

        servicio.setPago(pago);

        servicio.setEstadoId(EstadoEnum.PAGADO);

        servicioRepository.save(servicio);

        opPago.setPago(servicio.getPago());
        opPago.setResultado(OperacionPagoEnum.REALIZADO);

        return opPago;

    }

    public Servicio buscarServicioPorId(Integer servicioId) {

        return servicioRepository.findByServicioId(servicioId);

    }

	public Pago buscarPagoPorId(Integer pagoId){
		return pagoRepository.findByPagoId(pagoId);
	}

	public List<Pago> buscarPagosPorEmpresaId(Integer empresaId) {
		return pagoRepository.findAllByEmpresaId(empresaId);
	}

	public List<Pago> buscarPagosPorDeudorId(Integer deudorId) {
		return pagoRepository.findAllByDeudorId(deudorId);
	}


}