package com.prueba.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.prueba.model.Especialidad;
import com.prueba.service.EspecialidadService;
import com.prueba.service.TurnoService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/especialidades")
@RequiredArgsConstructor
public class EspecialidadController {
	private final EspecialidadService especialidadService;
	private final TurnoService turnoService;
	
	@GetMapping
	public String especialidades(Model model) {
		model.addAttribute("especialidades", especialidadService.traerTodos());
		
		return "/especialidades/home";
	} 
	
	@GetMapping("/{id}")
	public String especialidad(@PathVariable(name = "id") String id, Model model) {
		Long idLong = Long.valueOf(id);
		Especialidad especialidad = especialidadService.traer(idLong);
		String vista = "/especialidades/id";
		
		if(especialidad == null) {
			vista = "redirect:/especialidades";
		}else {
			model.addAttribute("especialidad",especialidad);
			model.addAttribute("turnos", turnoService.turnosPorEspecialidad(idLong));
		} 
		
		return vista;
	} 
	
	@GetMapping("/add")
	public String formAgregarEspecialidad(Model model) {
		model.addAttribute("especialidad", new Especialidad());
		
		return "/especialidades/add";
	}
	
	@PostMapping("/add")
	public String agregarEspecialidad(@ModelAttribute("especialidad") Especialidad e) throws Exception {
		try {
			especialidadService.agregarEspecialidad(e.getNombre(), e.getDescripcion());
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		
		return "redirect:/especialidades";
	}
	
}
