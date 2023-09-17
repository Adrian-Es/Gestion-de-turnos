package com.prueba.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.prueba.model.Paciente;
import com.prueba.repository.IPacienteRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PacienteService {

	private IPacienteRepository repository;
	
	public void agregarPersona(String nombre, String apellido, Integer dni) throws Exception{
		if(nombre == null || apellido == null || dni == null)
			throw new Exception("Error: ninguno de los valores puede ser nulo");
		
		repository.save(new Paciente(nombre, apellido, dni));
	}
	
	public Paciente traerPaciente(Long id) {
		return repository.findById(id).orElse(null);
	}

	public List<Paciente> traerTodos(){
		return repository.findAll();
	}
	
}
