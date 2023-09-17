package com.prueba.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistroTurnoDto {
	private String fecha;
	private String hora;
	private Long idConsultorio;
	private Long idMedico;
	private Long idPaciente;
}
