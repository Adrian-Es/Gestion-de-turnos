package com.prueba.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.prueba.model.Turno;

@Repository
public interface ITurnoRepository extends JpaRepository<Turno, Long>{

	@Query("SELECT t FROM Turno t "
			+ "WHERE t.paciente.id = :idPaciente")
	public List<Turno> turnosPorPaciente(@Param("idPaciente") Long idPaciente);
	
	@Query("SELECT t FROM Turno t "
			+ "WHERE t.medico.id = :idMedico")
	public List<Turno> turnosPorMedico(@Param("idMedico") Long idMedico);
	
	@Query("SELECT t FROM Turno t "
			+ "WHERE t.medico.especialidad.id = :idEspecialidad")
	public List<Turno> turnosPorEspecialidad(@Param("idEspecialidad") Long idEspecialidad);
	
	@Query("SELECT t FROM Turno t "
			+ "WHERE t.fecha = :fecha")
	public List<Turno> turnosPorFecha(@Param("fecha") LocalDate fecha);
	
	@Query("SELECT t FROM Turno t "
			+ "LEFT JOIN t.medico.horarios "
			+ "LEFT JOIN t.consultorio.especialidades "
			+ "WHERE t.id = :id")
	public Turno turnoConDependencias(@Param("id") Long id);
	
}
