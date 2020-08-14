package ar.com.ada.api.pagada.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.pagada.entities.TipoServicio;
import ar.com.ada.api.pagada.repositories.TipoServicioRepository;

@Service
public class ServicioService {

    @Autowired
    TipoServicioRepository tServicioRepository;

    public boolean crearTipoServicio(TipoServicio tipo) {

        if (tServicioRepository.existsById(tipo.getTipoServicioId()))
            return false;

        tServicioRepository.save(tipo);

        return true;

    }

    public List<TipoServicio> listarTiposDeServicios() {

        return tServicioRepository.findAll();

    }
}