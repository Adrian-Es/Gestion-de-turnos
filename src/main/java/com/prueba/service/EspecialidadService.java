package com.prueba.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.prueba.model.Especialidad;
import com.prueba.repository.IEspecialidadRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EspecialidadService {

	private IEspecialidadRepository repository;

	public void agregarEspecialidad(String nombre, String descripcion) throws Exception {
		if (nombre == null || descripcion == null)
			throw new Exception("Error: el nombre o la descripcion son nulos");

		repository.save(new Especialidad(nombre, descripcion));

	}

	public Especialidad traer(Long id) {
		return repository.findById(id).orElse(null);
	}

	public List<Especialidad> traerTodos() {
		return repository.findAll();
	}

}
