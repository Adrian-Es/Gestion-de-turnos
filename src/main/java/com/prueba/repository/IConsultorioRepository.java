package com.prueba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.prueba.model.Consultorio;

@Repository
public interface IConsultorioRepository extends JpaRepository<Consultorio, Long>{
	
	@Query("SELECT c FROM Consultorio c LEFT JOIN FETCH c.especialidades WHERE c.id = :idConsultorio")
	public Consultorio traerConDependencias(@Param("idConsultorio") Long idConsultorio); 
}
