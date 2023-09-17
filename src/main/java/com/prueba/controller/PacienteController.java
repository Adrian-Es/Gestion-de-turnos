package com.prueba.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.prueba.model.Paciente;
import com.prueba.service.PacienteService;
import com.prueba.service.TurnoService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/pacientes")
@RequiredArgsConstructor 
public class PacienteController {
	private final PacienteService pacienteService;
	private final TurnoService turnoService;
	
	@GetMapping
	public String pacientes(Model model) {
		model.addAttribute("pacientes", pacienteService.traerTodos()); 
		
		return "/pacientes/home";
	} 
	
	@GetMapping("/{id}")
	public String pacientes(@PathVariable(name = "id") String id, Model model) {
		Long idLong = Long.valueOf(id);
		Paciente paciente = pacienteService.traerPaciente(idLong);
		String vista = "/pacientes/id";
		
		if(paciente == null) {
			vista = "redirect:/pacientes";
		}else {
			model.addAttribute("paciente",paciente);
			model.addAttribute("turnos", turnoService.turnosPorPaciente(idLong));
		} 
		
		return vista;
	} 

	@GetMapping("/add")
	public String formAgregarPaciente(Model model) {
		model.addAttribute("paciente", new Paciente());

		return "/pacientes/add";
	}

	@PostMapping("/add")
	public String agregarPaciente(@ModelAttribute("paciente") Paciente p) throws Exception {
		try {
			pacienteService.agregarPersona(p.getNombre(), p.getApellido(), p.getDni());
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}

		return "redirect:/pacientes"; 
	}
	
}
