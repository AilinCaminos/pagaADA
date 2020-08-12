package ar.com.ada.api.pagada.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.pagada.entities.Empresa;
import ar.com.ada.api.pagada.entities.Pais.TipoIdImpositivoEnum;
import ar.com.ada.api.pagada.repositories.EmpresaRepository;

@Service
public class EmpresaService {

    @Autowired
    EmpresaRepository empresaRepository;

    public Empresa crearEmpresa(Integer paisId, TipoIdImpositivoEnum tipoIdImpositivo, String idImpositivo,
            String nombre) {

        Empresa emp = new Empresa();

        emp.setPaisId(paisId);
        emp.setTipoIdImpositivo(tipoIdImpositivo);
        emp.setIdImpositivo(idImpositivo);
        emp.setNombre(nombre);

        return empresaRepository.save(emp);

    }

    public List<Empresa> listarEmpresas(){
        
        return empresaRepository.findAll();
        
    }

}