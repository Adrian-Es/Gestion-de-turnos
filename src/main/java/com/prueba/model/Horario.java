package com.prueba.model;

import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "horario")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Horario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Setter(AccessLevel.PROTECTED)
	private Long id;
	
	@Column(nullable = false)
	private LocalTime horaEntrada;

	@Column(nullable = false)
	private LocalTime horaSalida;
	
	@Column(nullable = false)
	private Integer dia;
	
	@ManyToOne(fetch = FetchType.LAZY,optional = false)
	private Medico medico;

	public Horario(LocalTime horaEntrada, LocalTime horaSalida, Integer dia, Medico medico) {
		super();
		this.horaEntrada = horaEntrada;
		this.horaSalida = horaSalida;
		this.dia = dia;
		this.medico = medico;
	}

	
}
