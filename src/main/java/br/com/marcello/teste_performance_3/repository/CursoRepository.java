package br.com.marcello.teste_performance_3.controller;

import br.com.marcello.teste_performance_3.model.Curso;
import org.springframework.data.repository.CrudRepository;

public interface CursoRepository extends CrudRepository<Curso, Integer> {

}
