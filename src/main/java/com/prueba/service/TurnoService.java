package com.prueba.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.prueba.model.Consultorio;
import com.prueba.model.Medico;
import com.prueba.model.Paciente;
import com.prueba.model.Turno;
import com.prueba.repository.ITurnoRepository;
import com.prueba.util.Util;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TurnoService {

	private ITurnoRepository repository;
 
	public void agregarTurno(LocalDate fecha, LocalTime hora, Medico medico, Paciente paciente, Consultorio consultorio)
			throws Exception {

		if (fecha == null || hora == null || medico == null || paciente == null || consultorio == null)
			throw new Exception("Error: ningun valor puede ser nulo");

		if (fecha.getDayOfWeek().getValue() == 7)
			throw new Exception("Error: el turno no puede ser un domingo");

		if (!Util.horaValida(hora))
			throw new Exception("Error: la hora no es valida");

		if (!consultorio.getEspecialidades().stream().anyMatch(t -> t.equals(medico.getEspecialidad())))
			throw new Exception("Error: El consultorio no esta adecuado para la consulta de este medico");

	 	if (!medico.getHorarios().stream()
				.anyMatch(t -> t.getDia() == fecha.getDayOfWeek().getValue()))
			throw new Exception("Error: el medico no atiende ese dia");
		
		if(!medico.getHorarios().stream().filter(t -> t.getDia() == fecha.getDayOfWeek().getValue())
				.anyMatch(t -> !hora.isBefore(t.getHoraEntrada()) && !hora.isAfter(t.getHoraSalida()) ))
			throw new Exception("Error: el medico no esta disponible para atender en ese horario");

		List<Turno> turnosDeEseDia = repository.turnosPorFecha(fecha);

		if (!turnosDeEseDia.isEmpty()) { // si hay turnos ese día...
			turnosDeEseDia = turnosDeEseDia.stream().filter(t -> t.getHora().equals(hora)).toList();
			if (!turnosDeEseDia.isEmpty()) { // y a esa hora
				// si hay algun turno en ese consultorio
				if (turnosDeEseDia.stream().anyMatch(t -> t.getConsultorio().equals(consultorio))) {
					throw new Exception("Error: el consultorio esta ocupado a esa hora y ese dia");
				}
				// o con ese medico
				else if (turnosDeEseDia.stream().anyMatch(t -> t.getMedico().equals(medico))) {
					throw new Exception("Error: el medico tiene un turno a esa hora y ese dia");
				}
				// o paciente
				else if (turnosDeEseDia.stream().anyMatch(t -> t.getPaciente().equals(paciente))) {
					throw new Exception("Error: el paciente tiene un turno a esa hora y ese dia");
				}
			}
		}

		repository.save(new Turno(fecha, hora, medico, paciente, consultorio));

	}

	public void modificarTurno(Long id, LocalDate fecha, LocalTime hora, Medico medico, Paciente paciente,
			Consultorio consultorio) throws Exception {

		Turno turno = repository.turnoConDependencias(id);
		turno.setFecha(fecha);
		turno.setHora(hora);
		turno.setMedico(medico);
		turno.setConsultorio(consultorio);
		turno.setPaciente(paciente);

		if (LocalDate.now().equals(turno.getFecha()))
			if (Duration.between(LocalTime.now(), turno.getHora()).toMinutesPart() <= 60)
				throw new Exception(
						"Error: el turno no se puede modificar porque falta una hora o menos para su realizacion");
		
		if(LocalDateTime.of(turno.getFecha(), turno.getHora()).isBefore(LocalDateTime.now()))
			throw new Exception("Error: el turno no puede modificarse porque ya se realizo");
		

		if (turno.getFecha() == null || turno.getHora() == null || turno.getMedico() == null
				|| turno.getPaciente() == null || turno.getConsultorio() == null)
			throw new Exception("Error: ningun valor puede ser nulo");

		if (turno.getFecha().getDayOfWeek().getValue() == 7)
			throw new Exception("Error: el turno no puede ser un domingo");

		if (!Util.horaValida(turno.getHora()))
			throw new Exception("Error: la hora no es valida");

		if (!turno.getConsultorio().getEspecialidades().stream()
				.anyMatch(t -> t.equals(turno.getMedico().getEspecialidad())))
			throw new Exception("Error: El consultorio no esta adecuado para la consulta de este medico");

		if (!turno.getMedico().getHorarios().stream()
				.anyMatch(t -> t.getDia() == turno.getFecha().getDayOfWeek().getValue()))
			throw new Exception("Error: el medico no atiende ese dia");
		
		if(!turno.getMedico().getHorarios().stream().filter(t -> t.getDia() == turno.getFecha().getDayOfWeek().getValue())
				.anyMatch(t -> !turno.getHora().isBefore(t.getHoraEntrada()) && !turno.getHora().isAfter(t.getHoraSalida()) ))
			throw new Exception("Error: el medico no esta disponible para atender en ese horario");

		List<Turno> turnosDeEseDia = repository.turnosPorFecha(turno.getFecha());

		// busco el turno con el mismo id y lo elimino para evitar hacer comparaciones
		// consigo mismo
		turnosDeEseDia = turnosDeEseDia.stream().filter(t -> t.getId() != turno.getId()).toList();

		if (!turnosDeEseDia.isEmpty()) { // si hay turnos ese día...
			turnosDeEseDia = turnosDeEseDia.stream().filter(t -> t.getHora().equals(turno.getHora())).toList();
			if (!turnosDeEseDia.isEmpty()) { // y a esa hora
				// si hay algun turno en ese consultorio
				if (turnosDeEseDia.stream().anyMatch(t -> t.getConsultorio().equals(turno.getConsultorio()))) {
					throw new Exception("Error: el consultorio esta ocupado a esa hora");
				}
				// o con ese medico
				else if (turnosDeEseDia.stream().anyMatch(t -> t.getMedico().equals(turno.getMedico()))) {
					throw new Exception("Error: el medico tiene un turno a esa hora");
				}
				// o paciente
				else if (turnosDeEseDia.stream().anyMatch(t -> t.getPaciente().equals(turno.getPaciente()))) {
					throw new Exception("Error: el paciente tiene un turno a esa hora");
				}
			}
		}

		repository.save(turno);
	}

	public void eliminarTurno(Long id) throws Exception {
		Turno turno = repository.findById(id).orElse(null);

		if (turno == null)
			throw new Exception("Error: el turno que desea eliminar no existe");
		
		if(LocalDateTime.of(turno.getFecha(), turno.getHora()).isBefore(LocalDateTime.now()))
			throw new Exception("Error: el turno no puede eliminarse porque ya se realizo");

		if (LocalDate.now().equals(turno.getFecha()))
			if (Duration.between(LocalTime.now(), turno.getHora()).toMinutesPart() <= 60)
				throw new Exception(
						"Error: el turno no se puede cancelar porque falta una hora o menos para su realizacion");

		repository.deleteById(id);
	}

	public Turno turnoConDependencia(Long id) {
		return repository.turnoConDependencias(id);
	}

	public List<Turno> todosLosTurnos() {
		return repository.findAll();
	}

	public List<Turno> turnosPorPaciente(Long idPaciente) {
		return repository.turnosPorPaciente(idPaciente);
	}

	public List<Turno> turnosPorMedico(Long idMedico) {
		return repository.turnosPorMedico(idMedico);
	}

	public List<Turno> turnosPorEspecialidad(Long idEspecialidad) {
		return repository.turnosPorEspecialidad(idEspecialidad);
	}

}
