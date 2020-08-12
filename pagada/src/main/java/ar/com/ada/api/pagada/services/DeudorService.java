package ar.com.ada.api.pagada.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.pagada.entities.Deudor;
import ar.com.ada.api.pagada.entities.Pais.TipoIdImpositivoEnum;
import ar.com.ada.api.pagada.repositories.DeudorRepository;

@Service
public class DeudorService {

    @Autowired
    DeudorRepository deudorRepository;

    public Deudor crearDeudor(Integer paisId, TipoIdImpositivoEnum tipoIdImpositivo, String idImpositivo,
            String nombre) {

        Deudor deu = new Deudor();

        deu.setPaisId(paisId);
        deu.setTipoIdImpositivo(tipoIdImpositivo);
        deu.setIdImpositivo(idImpositivo);
        deu.setNombre(nombre);

        return deudorRepository.save(deu);

    }

    public List<Deudor> listarDeudores() {

        return deudorRepository.findAll();

    }

}