package br.com.marcello.teste_performance_3.repository;

import br.com.marcello.teste_performance_3.model.Aluno;
import br.com.marcello.teste_performance_3.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlunoRepository  extends JpaRepository<Aluno, Integer> {
    List<Aluno> findByCursoId(Integer cursoId);
}
