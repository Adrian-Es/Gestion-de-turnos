package com.prueba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prueba.model.Paciente;

@Repository
public interface IPacienteRepository extends JpaRepository<Paciente, Long>{

}
