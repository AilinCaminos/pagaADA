package ar.com.ada.api.pagada;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ar.com.ada.api.pagada.entities.Deudor;
import ar.com.ada.api.pagada.entities.Empresa;
import ar.com.ada.api.pagada.entities.Pais.TipoIdImpositivoEnum;
import ar.com.ada.api.pagada.services.DeudorService;
import ar.com.ada.api.pagada.services.EmpresaService;
import ar.com.ada.api.pagada.services.DeudorService.DeudorValidacionEnum;
import ar.com.ada.api.pagada.services.EmpresaService.EmpresaValidacionEnum;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	EmpresaService empresaService;
	@Autowired
	DeudorService deudorService;

	//Tests Empresa

	@Test
	void Empresa_Quefalle_IdImpositivoConLetras() {

		EmpresaValidacionEnum empresaValidacion;

		Empresa emp = new Empresa();
		emp.setPaisId(32);
		emp.setTipoIdImpositivo(TipoIdImpositivoEnum.CUIT);
		emp.setIdImpositivo("3373737373737A");
		emp.setNombre("TESTING EMPRESA");

		empresaValidacion = empresaService.validarEmpresa(emp);

		assertTrue(empresaValidacion == EmpresaValidacionEnum.ID_IMPOSITIVO_INVALIDO);

	}

	@Test
	void Empresa_QueFalle_NombreNulo() {

		EmpresaValidacionEnum empresaValidacion;

		Empresa emp = new Empresa();
		emp.setPaisId(32);
		emp.setTipoIdImpositivo(TipoIdImpositivoEnum.CUIT);
		emp.setIdImpositivo("121212121212121");
		emp.setNombre(null);

		empresaValidacion = empresaService.validarEmpresa(emp);

		assertTrue(empresaValidacion == EmpresaValidacionEnum.NOMBRE_INVALIDO);

	}

	@Test
	void Empresa_QueFalle_NombreMasDe100() {

		EmpresaValidacionEnum empresaValidacion;

		Empresa emp = new Empresa();
		emp.setPaisId(32);
		emp.setTipoIdImpositivo(TipoIdImpositivoEnum.CUIT);
		emp.setIdImpositivo("121212121212121");
		emp.setNombre("Eueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueuu");

		empresaValidacion = empresaService.validarEmpresa(emp);

		assertTrue(empresaValidacion == EmpresaValidacionEnum.NOMBRE_INVALIDO);

	}

	@Test
	void Empresa_QueFalle_IdImpositivoNulo() {

		EmpresaValidacionEnum empresaValidacion;

		Empresa emp = new Empresa();
		emp.setPaisId(32);
		emp.setTipoIdImpositivo(TipoIdImpositivoEnum.CUIT);
		emp.setIdImpositivo(null);
		emp.setNombre("TESTING EMPRESA");

		empresaValidacion = empresaService.validarEmpresa(emp);

		assertTrue(empresaValidacion == EmpresaValidacionEnum.ID_IMPOSITIVO_INVALIDO);

	}

	@Test
	void Empresa_TodoOk() {

		EmpresaValidacionEnum empresaValidacion;

		Empresa emp = new Empresa();
		emp.setPaisId(32);
		emp.setTipoIdImpositivo(TipoIdImpositivoEnum.CUIT);
		emp.setIdImpositivo("121212121212121");
		emp.setNombre("TESTING EMPRESA");

		empresaValidacion = empresaService.validarEmpresa(emp);

		assertTrue(empresaValidacion == EmpresaValidacionEnum.OK);

	}

	//Tests Deudor	

	@Test
	void Deudor_Quefalle_IdImpositivoConLetras() {

		DeudorValidacionEnum deudorValidacion;

		Deudor deu = new Deudor();
		deu.setPaisId(32);
		deu.setTipoIdImpositivo(TipoIdImpositivoEnum.CUIT);
		deu.setIdImpositivo("3373737373737A");
		deu.setNombre("TESTING EMPRESA");

		deudorValidacion = deudorService.validarEmpresa(deu);

		assertTrue(deudorValidacion == DeudorValidacionEnum.ID_IMPOSITIVO_INVALIDO);

	}

	@Test
	void Deudor_QueFalle_NombreNulo() {

		DeudorValidacionEnum deudorValidacion;

		Deudor deu = new Deudor();
		deu.setPaisId(32);
		deu.setTipoIdImpositivo(TipoIdImpositivoEnum.CUIT);
		deu.setIdImpositivo("121212121212121");
		deu.setNombre(null);

		deudorValidacion = deudorService.validarEmpresa(deu);

		assertTrue(deudorValidacion == DeudorValidacionEnum.NOMBRE_INVALIDO);

	}

	@Test
	void Deudor_QueFalle_NombreMasDe100() {

		DeudorValidacionEnum deudorValidacion;

		Deudor deu = new Deudor();
		deu.setPaisId(32);
		deu.setTipoIdImpositivo(TipoIdImpositivoEnum.CUIT);
		deu.setIdImpositivo("121212121212121");
		deu.setNombre("Eueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueueuu");

		deudorValidacion = deudorService.validarEmpresa(deu);

		assertTrue(deudorValidacion == DeudorValidacionEnum.NOMBRE_INVALIDO);

	}

	@Test
	void Deudor_QueFalle_IdImpositivoNulo() {

		DeudorValidacionEnum deudorValidacion;

		Deudor deu = new Deudor();
		deu.setPaisId(32);
		deu.setTipoIdImpositivo(TipoIdImpositivoEnum.CUIT);
		deu.setIdImpositivo(null);
		deu.setNombre("TESTING EMPRESA");

		deudorValidacion = deudorService.validarEmpresa(deu);

		assertTrue(deudorValidacion == DeudorValidacionEnum.ID_IMPOSITIVO_INVALIDO);

	}

	@Test
	void Deudor_TodoOk() {

		DeudorValidacionEnum deudorValidacion;

		Deudor deu = new Deudor();
		deu.setPaisId(32);
		deu.setTipoIdImpositivo(TipoIdImpositivoEnum.CUIT);
		deu.setIdImpositivo("121212121212121");
		deu.setNombre("TESTING EMPRESA");

		deudorValidacion = deudorService.validarEmpresa(deu);

		assertTrue(deudorValidacion == DeudorValidacionEnum.OK);

	}

}
