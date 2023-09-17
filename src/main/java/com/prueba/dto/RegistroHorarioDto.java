package com.prueba.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegistroHorarioDto {
	private String horaEntrada;
	private String horaSalida;
	private String dia;
}
