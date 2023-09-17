package com.prueba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prueba.model.Horario;

@Repository
public interface IHorarioRepository extends JpaRepository<Horario, Long>{

}
