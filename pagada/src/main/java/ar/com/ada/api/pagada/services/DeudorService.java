package ar.com.ada.api.pagada.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.pagada.entities.Deudor;
import ar.com.ada.api.pagada.repositories.DeudorRepository;

@Service
public class DeudorService {

    @Autowired
    DeudorRepository deudorRepository;

    public void crearDeudor(Deudor deudor) {

        deudorRepository.save(deudor);

    }

    public List<Deudor> listarDeudores() {

        return deudorRepository.findAll();

    }

    public DeudorValidacionEnum validarEmpresa(Deudor deu) {

        // Si es nulo, error
        if (deu.getIdImpositivo() == null)
            return DeudorValidacionEnum.ID_IMPOSITIVO_INVALIDO;

        // ID impositivo al menos de 11 digitos y maximo 20
        if (!(deu.getIdImpositivo().length() >= 11 && deu.getIdImpositivo().length() <= 20))
            return DeudorValidacionEnum.ID_IMPOSITIVO_INVALIDO;

        // solo numeros
        String idImpositivo = deu.getIdImpositivo();
        for (char caracter : idImpositivo.toCharArray()) {
            if (!Character.isDigit(caracter))
                return DeudorValidacionEnum.ID_IMPOSITIVO_INVALIDO;

        }

        if (deu.getNombre() == null)
            return DeudorValidacionEnum.NOMBRE_INVALIDO;

        if (deu.getNombre().length() > 100)
            return DeudorValidacionEnum.NOMBRE_INVALIDO;

        // Si llego hasta aqui, es que todo lo de arriba, era valido
        return DeudorValidacionEnum.OK;
    }

    public enum DeudorValidacionEnum {
        OK, // Cuando esta todo validado ok
        NOMBRE_INVALIDO, // Nombre tenga algun problema
        ID_IMPOSITIVO_INVALIDO // ID impositivo tenga un problema
    }

}