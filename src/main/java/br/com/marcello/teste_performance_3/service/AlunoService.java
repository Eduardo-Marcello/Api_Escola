package br.com.marcello.teste_performance_3.service;

import br.com.marcello.teste_performance_3.repository.AlunoRepository;
import br.com.marcello.teste_performance_3.exception.ResourceNotFoundException;
import br.com.marcello.teste_performance_3.model.Aluno;
import br.com.marcello.teste_performance_3.repository.CursoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final CursoRepository cursoRepository;

    private boolean notCodigo(String codigo) {
        boolean flag = true;
        if (codigo.isEmpty() && !codigo.matches("[0-9]*")) {
            return flag;
        } else {
            for (Aluno aluno : alunoRepository.findAll()) {
                if (Objects.equals(aluno.getCodigo(), codigo)) {
                    flag = false;
                }
            }
        }
        return flag;
    }


    private boolean alunoExists(Aluno aluno){

        for (Aluno aluno1 : alunoRepository.findAll()) {
            if(Objects.equals(aluno.getCpf(), aluno1.getCpf())) {
                return true;
            }
        }
        return false;
    }

    public AlunoService(AlunoRepository alunoRepository, CursoRepository cursoRepository) {
        this.alunoRepository = alunoRepository;
        alunoRepository.saveAll(List.of(
                new Aluno("Eduardo", "18204918704" ,25, "234"),
                new Aluno("Marcello", "12234567890" , 26, "245")));
        this.cursoRepository = cursoRepository;
    }


    public Iterable<Aluno> getAll() {
        return this.alunoRepository.findAll();
    }



    public Aluno getById(int id) {
        if(id <= 0 && id > alunoRepository.count()){
            throw new ResourceNotFoundException("Valor Inválido - Id inexistente!");
        } else {
            return this.alunoRepository.findById(id).get();
        }

    }

    public void save(Aluno aluno) {
        if (alunoExists(aluno)) {
            throw new ResourceNotFoundException("Cpf existente!");
        } else {
            alunoRepository.save(aluno);
        }
    }

    public Aluno addCursoAluno(int curso_id, Aluno aluno){
         return cursoRepository.findById(curso_id).map(curso -> {
            aluno.setCurso(curso);
            return alunoRepository.save(aluno);
        }).orElseThrow(()-> new ResourceNotFoundException("Curso não encontrado com id: " +curso_id));

    }

    public void update(int id, Aluno alunoUpdate) {
        alunoRepository.findById(id).map(aluno -> {
            aluno.setNome(alunoUpdate.getNome());
            aluno.setCpf(alunoUpdate.getCpf());
            aluno.setIdade(alunoUpdate.getIdade());
            aluno.setCodigo(alunoUpdate.getCodigo());
            return alunoRepository.save(aluno);
        }).orElseThrow(() -> new ResourceNotFoundException("Aluno não encontrado com id: "+id));

    }

    public ResponseEntity<?> getAllAlunosCursos(int cursoId){
        List<Aluno> alunos = alunoRepository.findByCursoId(cursoId);
        if(alunos.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(alunos);
    }

    public void delete(int id) {
        if(alunoRepository.findById(id).isEmpty()){
            throw new ResourceNotFoundException("Aluno não encontrado!");
        } else {
            alunoRepository.deleteById(id);
        }

    }
}
