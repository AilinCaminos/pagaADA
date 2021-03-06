package ar.com.ada.api.pagada.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.com.ada.api.pagada.entities.Servicio;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Integer> {

    @Query("select s from Servicio s where s.empresa.empresaId = :empresaId") // JPQL
    List<Servicio> findAllByEmpresaId(Integer empresaId);

    @Query("select s from Servicio s where s.empresa.empresaId = :empresaId and s.estadoId = 0") //JPQL, en este caso, 0 es PENDIENTE
    List<Servicio> findAllByPendientesEmpresaId(Integer empresaId);

    @Query("select s from Servicio s where s.empresa.empresaId = :empresaId and s.deudor.deudorId = :deudorId and s.estadoId = 0")
    List<Servicio> findAllByPendientesEmpresaIdAndDeudorId(Integer empresaId, Integer deudorId);

    @Query("select s from Servicio s where s.empresa.empresaId = :empresaId and s.deudor.deudorId = :deudorId")
    List<Servicio> findAllByEmpresaIdAndDeudorIdHistorico(Integer empresaId, Integer deudorId);

    @Query("select s from Servicio s where s.codigoBarras = :codigoBarras")
    List<Servicio> findAllByCodigoBarras(String codigoBarras);

    Servicio findByServicioId(Integer servicioId);

}