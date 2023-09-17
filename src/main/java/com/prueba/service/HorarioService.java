package com.prueba.service;

import java.time.LocalTime;

import org.springframework.stereotype.Service;

import com.prueba.model.Horario;
import com.prueba.model.Medico;
import com.prueba.repository.IHorarioRepository;
import com.prueba.util.Util;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class HorarioService {

	private IHorarioRepository repository;

	public void agregarHorario(LocalTime horaEntrada, LocalTime horaSalida, Integer dia, Medico medico) throws Exception{
		if(medico == null)
			throw new Exception("Error: el medico no existe");
		
		if(horaSalida.isBefore(horaEntrada))
			throw new Exception("Error: la hora de salida no puede ser anterior a la de entrada");
		
		if(dia < 1 || dia > 6)
			throw new Exception("Error: el dia ingresado es invalido");
		
		if(!Util.horaValida(horaEntrada) || !Util.horaValida(horaSalida))
			throw new Exception("Error: los horarios ingresados estan fuera del rango de apertura de la clinica");
		
		repository.save(new Horario(horaEntrada,horaSalida,dia, medico));
	}
	
	public void eliminarHorario(Long id){
		repository.deleteById(id);
	}
	
}
