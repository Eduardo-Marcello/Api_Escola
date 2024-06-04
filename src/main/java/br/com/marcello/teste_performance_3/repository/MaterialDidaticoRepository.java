package br.com.marcello.teste_performance_3.repository;

import br.com.marcello.teste_performance_3.model.MaterialDidatico;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialDidaticoRepository extends MongoRepository<MaterialDidatico, String> {

}
