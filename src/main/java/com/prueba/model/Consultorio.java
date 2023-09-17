package com.prueba.model;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "consultorio")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Consultorio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Setter(AccessLevel.PROTECTED)
	private Long id;
	
	@Column(nullable = false)
	private Integer numero;
	
	@ManyToMany(fetch = FetchType.LAZY)
	private Set<Especialidad> especialidades; //refiere al tipo de especialidades para las cuales está adecuado el consultorio

	public Consultorio(Integer numero) {
		super();
		this.numero = numero;
	}
	
}
