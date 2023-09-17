package com.prueba.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.prueba.dto.RegistroConsultorioEspecialidadDto;
import com.prueba.model.Consultorio;
import com.prueba.service.ConsultorioService;
import com.prueba.service.EspecialidadService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/consultorios")
@RequiredArgsConstructor
public class ConsultorioController {
	private final ConsultorioService consultorioService;
	private final EspecialidadService especialidadService;

	@GetMapping
	public String consultorios(Model model) {
		model.addAttribute("consultorios", consultorioService.traerTodos());

		return "/consultorios/home";
	}

	@GetMapping("/add")
	public String formAgregarConsultorio(Model model) {
		model.addAttribute("consultorio", new Consultorio());

		return "/consultorios/add";
	}

	@PostMapping("/add")
	public String agregarConsultorio(@ModelAttribute("consultorio") Consultorio c) throws Exception {
		try {
			consultorioService.agregarConsultorio(c.getNumero());
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}

		return "redirect:/consultorios";
	}

	@GetMapping("/{id}")
	public String consultorio(@PathVariable(name = "id") String id, Model model) {
		Long idLong = Long.valueOf(id);
		Consultorio consultorio = consultorioService.traerConDependencias(idLong);
		String vista = "/consultorios/id";

		if (consultorio == null) {
			vista = "redirect:/consultorios";
		} else {
			model.addAttribute("consultorio", consultorio);
		}
		return vista;
	}

	@GetMapping("/{id}/add")
	public String formAgregarEspecialidad(@PathVariable(name = "id") String id, Model model) {
		RegistroConsultorioEspecialidadDto registro = new RegistroConsultorioEspecialidadDto();

		model.addAttribute("registro", registro);
		model.addAttribute("id", id); 
		model.addAttribute("especialidades", especialidadService.traerTodos());

		return "/consultorios/addEspecialidad";
	}

	@PostMapping("/{id}/add")
	public String agregarEspecialidad(@PathVariable(name = "id") String id,
			@ModelAttribute("registro") RegistroConsultorioEspecialidadDto c) throws Exception {
		try {
			consultorioService.agregarEspecialidadAConsultorio(Long.valueOf(id),
					especialidadService.traer(c.getIdEspecialidad()));
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}

		return "redirect:/consultorios/" + id;
	}

}
