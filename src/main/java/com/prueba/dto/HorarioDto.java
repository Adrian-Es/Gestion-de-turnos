package com.prueba.dto;

import java.time.LocalTime;

import com.prueba.model.Medico;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class HorarioDto {
	private Long id;
	private LocalTime horaEntrada;
	private LocalTime horaSalida;
	private String dia;
	private Medico medico;
}
