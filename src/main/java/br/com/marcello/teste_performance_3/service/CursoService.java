package br.com.marcello.teste_performance_3.service;


import br.com.marcello.teste_performance_3.repository.CursoRepository;
import br.com.marcello.teste_performance_3.exception.ResourceNotFoundException;
import br.com.marcello.teste_performance_3.model.Curso;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;

    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
        cursoRepository.saveAll(List.of(
                new Curso("SI","Sistemas de informação" ,"634"),
                new Curso("ES","Engenharia de Software","645")));
    }


    private boolean cursoExists(Curso curso){

        for (Curso curso1 : cursoRepository.findAll()) {
            if(Objects.equals(curso1.getCodigo(), curso.getCodigo())) {
                return true;
            }
        }
        return false;
    }

    public Iterable<Curso> getAll() {
        return this.cursoRepository.findAll();
    }

    public Curso getById(int id) {
        if(id <= 0 && id > cursoRepository.count()){
            throw new ResourceNotFoundException("Valor Inválido - Id inexistente!");
        } else {
            return this.cursoRepository.findById(id).get();
        }
    }

    public void save(Curso curso) {
        if (cursoExists(curso)) {
            throw new ResourceNotFoundException("Curso existente!");
        } else {
            cursoRepository.save(curso);
        }
    }

    public void update(int id, Curso curso) {
        if (!cursoRepository.existsById(id) && !cursoExists(curso)) {
            throw new ResourceNotFoundException("Curso não encontrado!");
        } else {
            if (!curso.getNome().isEmpty() && !curso.getCodigo().isEmpty()) {
                curso.setId(id);
                cursoRepository.save(curso);
            } else {
                throw new ResourceNotFoundException("Campos obrigatórios em branco!");
            }
        }
    }

        public void delete(int id) {
            if(cursoRepository.findById(id).isEmpty()){
                throw new ResourceNotFoundException("Curso não encontrado!");
            } else {
                cursoRepository.deleteById(id);
            }

        }

        @Cacheable(value = "cursos")
        public List<Curso> buscarCursos(){
            return cursoRepository.findAll();
        }

        @Cacheable(value = "cursos", key = "#id")
        public Optional<Curso> buscarCurso(int id) {
            if(cursoRepository.findById(id).isPresent()) {
                return cursoRepository.findById(id);
            } else {
                throw new ResourceNotFoundException("Curso não encontrado!");
            }
        }

    @CacheEvict(value = "cursos", key = "#id")
    public Curso updateCache(int id, Curso cursoUpdate){
            return cursoRepository.findById(id).map(curso ->{
                curso.setNome(cursoUpdate.getNome());
                curso.setDescricao(cursoUpdate.getDescricao());
                curso.setCodigo(cursoUpdate.getCodigo());
                return cursoRepository.save(curso);
            }).orElseGet(() -> {
                cursoUpdate.setId(id);
                return cursoRepository.save(cursoUpdate);
            });
    }

    @CacheEvict(value = "cursos", key = "#id")
    public void deleteCacheById(int id){
        if(cursoRepository.existsById(id)){
            cursoRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Curso Não encontrado!");
        }

    }
}
