package com.prueba.controller;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.prueba.dto.HorarioDto;
import com.prueba.dto.RegistroHorarioDto;
import com.prueba.dto.RegistroMedicoDto;
import com.prueba.model.Medico;
import com.prueba.service.EspecialidadService;
import com.prueba.service.HorarioService;
import com.prueba.service.MedicoService;
import com.prueba.service.TurnoService;
import com.prueba.util.Util;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/medicos")
@RequiredArgsConstructor
public class MedicoController {
	private final MedicoService medicoService;
	private final HorarioService horarioService;
	private final TurnoService turnoService;
	private final EspecialidadService especialidadService;

	@GetMapping
	public String medicos(Model model) {
		model.addAttribute("medicos", medicoService.traerTodos());

		return "/medicos/home";
	}

	@GetMapping("/add")
	public String formAgregarMedico(Model model) {
		model.addAttribute("medico", new RegistroMedicoDto());
		model.addAttribute("especialidades", especialidadService.traerTodos());

		return "/medicos/add";
	}

	@PostMapping("/add")
	public String agregarMedico(@ModelAttribute("medico") RegistroMedicoDto m) throws Exception {
		try {
			medicoService.agregarMedico(m.getNombre(), m.getApellido(),
					especialidadService.traer(m.getIdEspecialidad()));
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}

		return "redirect:/medicos";
	}

	@GetMapping("/{id}")
	public String medico(@PathVariable(name = "id") String id, Model model) {
		Long idLong = Long.valueOf(id);
		Medico medico = medicoService.traerConDependencias(idLong);
		String vista = "/medicos/id";

		if (medico == null) {
			vista = "redirect:/medicos";
		} else {
			model.addAttribute("medico", medico);
			model.addAttribute("horarios", medico.getHorarios().stream().map(t -> new HorarioDto(t.getId(),
					t.getHoraEntrada(), t.getHoraSalida(), Util.dia(t.getDia()), t.getMedico()))
					.toList());
			model.addAttribute("turnos", turnoService.turnosPorMedico(idLong));
		}
		return vista;
	}

	@GetMapping("/{id}/add")
	public String formAgregarHorario(@PathVariable(name = "id") String id, Model model) {
		model.addAttribute("horario", new RegistroHorarioDto());
		model.addAttribute("id", id);

		return "/medicos/addHorarios";
	}

	@PostMapping("/{id}/add")
	public String agregarHorario(@PathVariable(name = "id") String id, @ModelAttribute("medico") RegistroHorarioDto h)
			throws Exception {
		try {
			horarioService.agregarHorario(LocalTime.parse(h.getHoraEntrada(), DateTimeFormatter.ISO_LOCAL_TIME),
					LocalTime.parse(h.getHoraSalida(), DateTimeFormatter.ISO_LOCAL_TIME), Integer.valueOf(h.getDia()),
					medicoService.traerConDependencias(Long.valueOf(id)));
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}

		return "redirect:/medicos/" + id;
	}

	@PostMapping("/{medId}/delete/{horId}")
	public String eliminarHorario(@PathVariable(name = "medId") String idMedico,
			@PathVariable(name = "horId") String idHorario) {

		horarioService.eliminarHorario(Long.valueOf(idHorario));

		return "redirect:/medicos/" + idMedico;
	}

}
