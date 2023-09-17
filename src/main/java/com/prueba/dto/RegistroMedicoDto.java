package com.prueba.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegistroMedicoDto {
	private String nombre;
	private String apellido;
	private Long idEspecialidad;
}
