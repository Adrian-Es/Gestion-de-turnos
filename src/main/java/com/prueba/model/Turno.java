package com.prueba.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "turno")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Turno {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Setter(AccessLevel.PROTECTED)
	private Long id;

	@Column(nullable = false)
	private LocalDate fecha;

	@Column(nullable = false)
	private LocalTime hora;

	@ManyToOne(optional = false)
	private Medico medico;

	@ManyToOne(optional = false)
	private Paciente paciente;

	@ManyToOne(optional = false)
	private Consultorio consultorio;

	public Turno(LocalDate fecha, LocalTime hora, Medico medico, Paciente paciente, Consultorio consultorio) {
		super();
		this.fecha = fecha;
		this.hora = hora;
		this.medico = medico;
		this.paciente = paciente;
		this.consultorio = consultorio;
	}

}
