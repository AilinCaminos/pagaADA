package ar.com.ada.api.pagada.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.pagada.entities.Empresa;
import ar.com.ada.api.pagada.models.request.EmpresaRequest;
import ar.com.ada.api.pagada.models.response.GenericResponse;
import ar.com.ada.api.pagada.services.EmpresaService;

@RestController
public class EmpresaController {
    
    /*
    Crear/Obtener empresas

	GET /api/empresas
	POST /api/empresas
    */

    @Autowired
    EmpresaService empresaService;

    @PostMapping("/api/empresas")
    public ResponseEntity<GenericResponse> crearEmpresa(@RequestBody EmpresaRequest empReq){
        
        GenericResponse r = new GenericResponse();

        Empresa empresa = empresaService.crearEmpresa(empReq.paisId,empReq.tipoIdImpositivo,empReq.idImpositivo,empReq.nombre);

        if(empresa.getEmpresaId() != null){

            r.isOk = true;
            r.id = empresa.getEmpresaId();
            r.message = "La empresa se creo con exito.";

            return ResponseEntity.ok(r);
        }

        r.isOk = false;
        r.message = "Ocurrio un error. No se pudo crear la empresa.";

        return ResponseEntity.badRequest().body(r);

    }

    @GetMapping("/api/empresas")
    public ResponseEntity<List<Empresa>> listarEmpresas(){

        return ResponseEntity.ok(empresaService.listarEmpresas());
        
    }
}