package com.prueba.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.prueba.model.Consultorio;
import com.prueba.model.Especialidad;
import com.prueba.repository.IConsultorioRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ConsultorioService {

	private IConsultorioRepository repository;

	public void agregarConsultorio(Integer numero) throws Exception {
		if(numero == null)
			throw new Exception("Error: el consultorio debe tener un numero");
		
		repository.save(new Consultorio(numero));
	}
	
	public Consultorio traer(Long id) {
		return repository.findById(id).orElse(null);
	}
	
	public List<Consultorio> traerTodos() {
		return repository.findAll();
	}
	
	public Consultorio traerConDependencias(Long id) {
		return repository.traerConDependencias(id);
	}
	
	public void agregarEspecialidadAConsultorio(Long idConsultorio, Especialidad especialidad) throws Exception{
		if(especialidad == null)
			throw new Exception("Error: la especialidad es nula");
		
		Consultorio consultorio = repository.traerConDependencias(idConsultorio);
		
		if(consultorio == null)
			throw new Exception("Error: El consultorio no existe");
		
		consultorio.getEspecialidades().add(especialidad);
		repository.save(consultorio);
		
	}
	
}
