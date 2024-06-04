package br.com.marcello.teste_performance_3.repository;

import br.com.marcello.teste_performance_3.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Integer> {

}
