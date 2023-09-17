package com.prueba.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.prueba.dto.RegistroTurnoDto;
import com.prueba.service.ConsultorioService;
import com.prueba.service.MedicoService;
import com.prueba.service.PacienteService;
import com.prueba.service.TurnoService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/turnos")
@RequiredArgsConstructor
public class TurnoController {
	private final TurnoService turnoService;
	private final MedicoService medicoService;
	private final PacienteService pacienteService;
	private final ConsultorioService consultorioService;

	@GetMapping
	public String turnos(Model model) {
		model.addAttribute("turnos", turnoService.todosLosTurnos());

		return "/turnos/home";
	}

	@GetMapping("/add")
	public String formAgregarTurno(Model model) {
		model.addAttribute("turno", new RegistroTurnoDto());
		model.addAttribute("consultorios", consultorioService.traerTodos());
		model.addAttribute("medicos",medicoService.traerTodos());
		model.addAttribute("pacientes", pacienteService.traerTodos());

		return "/turnos/add";
	}

	@PostMapping("/add")
	public String agregarTurno(@ModelAttribute("turno") RegistroTurnoDto t) throws Exception {
		try {
			turnoService.agregarTurno(LocalDate.parse(t.getFecha(), DateTimeFormatter.ISO_DATE),
					LocalTime.parse(t.getHora(), DateTimeFormatter.ISO_LOCAL_TIME),
					medicoService.traerConDependencias(t.getIdMedico()),
					pacienteService.traerPaciente(t.getIdPaciente()),
					consultorioService.traerConDependencias(t.getIdConsultorio()));

		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}

		return "redirect:/turnos";
	}

	@PostMapping("/delete/{id}")
	public String eliminarTurno(@PathVariable(name = "id") String id) throws Exception {
		try {
			turnoService.eliminarTurno(Long.valueOf(id));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}

		return "redirect:/turnos";
	} 

	@GetMapping("/{id}")
	public String formModificarTurno(@PathVariable(name = "id") String id, Model model) {	
		model.addAttribute("turno",  turnoService.turnoConDependencia(Long.valueOf(id)));
		model.addAttribute("registro", new RegistroTurnoDto());
		model.addAttribute("consultorios", consultorioService.traerTodos());
		model.addAttribute("medicos",medicoService.traerTodos());
		model.addAttribute("pacientes", pacienteService.traerTodos()); 
		
		return "/turnos/update"; 
	}

	@PostMapping("/{id}")
	public String modificarTurno(@PathVariable(name = "id") String id, @ModelAttribute("registro") RegistroTurnoDto t)
			throws Exception {
		try {
			System.out.println(id);
			turnoService.modificarTurno( Long.valueOf(id) 
					,LocalDate.parse(t.getFecha(), DateTimeFormatter.ISO_DATE),
					LocalTime.parse(t.getHora(), DateTimeFormatter.ISO_LOCAL_TIME),
					medicoService.traerConDependencias(t.getIdMedico()),
					pacienteService.traerPaciente(t.getIdPaciente()),
					consultorioService.traerConDependencias(t.getIdConsultorio()));
		} catch (Exception e) {
			e.printStackTrace(); 
			throw e;
		}

		return "redirect:/turnos";
	}

}
