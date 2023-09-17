package com.prueba.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.prueba.model.Especialidad;
import com.prueba.model.Medico;
import com.prueba.repository.IMedicoRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MedicoService {

	private IMedicoRepository repository;
	
	public void agregarMedico(String nombre, String apellido, Especialidad especialidad) throws Exception {
		if(nombre == null || apellido == null) 
			throw new Exception("Error: el nombre y el apellido no pueden ser nulos");
		
		if(especialidad == null)
			throw new Exception("Error: la especialidad no existe");
		
		repository.save(new Medico(nombre, apellido, especialidad));
	}
	
	public Medico traer(Long id) {
		return repository.findById(id).orElse(null);
	}
	
	public Medico traerConDependencias(Long id) {
		return repository.traerConDependencias(id);
	}

	public List<Medico> traerTodos() {
		return repository.findAll();
	}
	
}
