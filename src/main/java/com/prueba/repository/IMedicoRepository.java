package com.prueba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.prueba.model.Medico;

@Repository
public interface IMedicoRepository extends JpaRepository<Medico, Long>{

	@Query("SELECT m FROM Medico m LEFT JOIN FETCH m.horarios WHERE m.id = :idMedico")
	public Medico traerConDependencias(@Param("idMedico") Long id);
	
}
