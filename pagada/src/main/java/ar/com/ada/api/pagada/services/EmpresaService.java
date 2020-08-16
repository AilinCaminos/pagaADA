package ar.com.ada.api.pagada.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.pagada.entities.Empresa;
import ar.com.ada.api.pagada.repositories.EmpresaRepository;

@Service
public class EmpresaService {

    @Autowired
    EmpresaRepository empresaRepository;

    public void crearEmpresa(Empresa emp) {

        empresaRepository.save(emp);

    }

    public List<Empresa> listarEmpresas() {

        return empresaRepository.findAll();

    }

    public EmpresaValidacionEnum validarEmpresa(Empresa emp) {

        // Si es nulo, error
        if (emp.getIdImpositivo() == null)
            return EmpresaValidacionEnum.ID_IMPOSITIVO_INVALIDO;

        // ID impositivo al menos de 11 digitos y maximo 20
        if (!(emp.getIdImpositivo().length() >= 11 && emp.getIdImpositivo().length() <= 20))
            return EmpresaValidacionEnum.ID_IMPOSITIVO_INVALIDO;

        // solo numeros
        String idImpositivo = emp.getIdImpositivo();
        for (char caracter : idImpositivo.toCharArray()) {
            if (!Character.isDigit(caracter))
                return EmpresaValidacionEnum.ID_IMPOSITIVO_INVALIDO;

        }

        if (emp.getNombre() == null)
            return EmpresaValidacionEnum.NOMBRE_INVALIDO;

        if (emp.getNombre().length() > 100)
            return EmpresaValidacionEnum.NOMBRE_INVALIDO;

        // Si llego hasta aqui, es que todo lo de arriba, era valido
        return EmpresaValidacionEnum.OK;
    }

    public enum EmpresaValidacionEnum {
        OK, // Cuando esta todo validado ok
        NOMBRE_INVALIDO, // Nombre tenga algun problema
        ID_IMPOSITIVO_INVALIDO // ID impositivo tenga un problema
    }

    public Empresa buscarEmpresaPorId(Integer empresaId){

        Optional<Empresa> oEmp = empresaRepository.findById(empresaId);

        if(oEmp.isPresent()){
            return oEmp.get();
        }
        return null;
    }

}