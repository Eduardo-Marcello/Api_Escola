package br.com.marcello.teste_performance_3.service;

import br.com.marcello.teste_performance_3.exception.ResourceNotFoundException;
import br.com.marcello.teste_performance_3.model.MaterialDidatico;
import br.com.marcello.teste_performance_3.repository.MaterialDidaticoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MaterialDidaticoService {

    @Autowired
    private MaterialDidaticoRepository materialDidaticoRepository;

    public List<MaterialDidatico> findAll() {
        return materialDidaticoRepository.findAll();
    }

    public Optional<MaterialDidatico> findById(String id) {
        return materialDidaticoRepository.findById(id);
    }

    public MaterialDidatico save(MaterialDidatico materialDidatico) {
        return materialDidaticoRepository.save(materialDidatico);
    }

    public MaterialDidatico update(String id, MaterialDidatico materialDidatico) {
            return materialDidaticoRepository.findById(id).map(material -> {
                material.setNome(materialDidatico.getNome());
                material.setDescricao(materialDidatico.getDescricao());
                return materialDidaticoRepository.save(material);
            }).orElseThrow(() -> new ResourceNotFoundException("Material não existe!"));
    }

    public void delete(String id) {
        if (materialDidaticoRepository.existsById(id)) {
            materialDidaticoRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Material não encontrado");
        }

    }
}
