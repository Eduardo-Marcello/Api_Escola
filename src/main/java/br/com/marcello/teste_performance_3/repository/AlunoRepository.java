package br.com.marcello.teste_performance_3.controller;

import br.com.marcello.teste_performance_3.model.Aluno;
import org.springframework.data.repository.CrudRepository;

public interface AlunoRepository  extends CrudRepository<Aluno, Integer> {

}
