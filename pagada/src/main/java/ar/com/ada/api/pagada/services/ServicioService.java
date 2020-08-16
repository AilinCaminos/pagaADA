package ar.com.ada.api.pagada.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.pagada.entities.Servicio;
import ar.com.ada.api.pagada.entities.TipoServicio;
import ar.com.ada.api.pagada.repositories.ServicioRepository;
import ar.com.ada.api.pagada.repositories.TipoServicioRepository;

@Service
public class ServicioService {

    @Autowired
    TipoServicioRepository tServicioRepository;
    @Autowired
    ServicioRepository servicioRepository;

    public boolean crearTipoServicio(TipoServicio tipo) {

        if (tServicioRepository.existsById(tipo.getTipoServicioId()))
            return false;

        tServicioRepository.save(tipo);

        return true;

    }

    public List<TipoServicio> listarTiposDeServicios() {

        return tServicioRepository.findAll();

    }

    public Servicio crearServicio(Servicio servicio){

        return servicioRepository.save(servicio);

    }

    public TipoServicio buscarTipoServicioPorId(Integer tSevicioId){

        Optional<TipoServicio> oTipoServicio = tServicioRepository.findById(tSevicioId);

        if(oTipoServicio.isPresent()){
            return oTipoServicio.get();
        }
        return null;
    }

    public ServicioValidacionEnum validacionServicio(Servicio servicio){

        if (servicio.getImporte().compareTo(new BigDecimal(0)) <= 0) {
            return ServicioValidacionEnum.IMPORTE_INVALIDO;
        }
        return ServicioValidacionEnum.OK;

    }

    public enum ServicioValidacionEnum{
        OK,IMPORTE_INVALIDO
    }

}