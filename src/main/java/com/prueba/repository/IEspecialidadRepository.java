package com.prueba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prueba.model.Especialidad;

@Repository
public interface IEspecialidadRepository extends JpaRepository<Especialidad, Long>{

}
