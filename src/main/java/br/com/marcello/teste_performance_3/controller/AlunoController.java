package br.com.marcello.teste_performance_3.controller;

import br.com.marcello.teste_performance_3.exception.ResourceNotFoundException;
import br.com.marcello.teste_performance_3.model.Aluno;
import br.com.marcello.teste_performance_3.model.Curso;
import br.com.marcello.teste_performance_3.payload.MessagePayload;
import br.com.marcello.teste_performance_3.repository.CursoRepository;
import br.com.marcello.teste_performance_3.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/aluno")
public class AlunoController {

    @Autowired
    final private AlunoService alunoService;
    @Autowired
    private CursoRepository cursoRepository;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Aluno>> getAll(@RequestParam(required = false) Optional<String> codigo){
        if(codigo.isEmpty()){
            return ResponseEntity.ok((List<Aluno>) alunoService.getAll());
        } else {
            return ResponseEntity.notFound().build();
            /*if(alunoService.getByCod(String.valueOf(codigo)) == null){
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(alunoService.getByCod(String.valueOf(codigo)));
            }*/
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<Aluno> getById(@PathVariable int id){
        try {
            return ResponseEntity.ok(alunoService.getById(id));
        }catch (ResourceNotFoundException ex){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/curso/{cursoId}")
    public ResponseEntity<?> getAllAlunosByCurso(@PathVariable Integer cursoId){
        return alunoService.getAllAlunosCursos(cursoId);
    }

    @PostMapping("/save")
    public void save(@RequestBody Aluno aluno){
        alunoService.save(aluno);

    }

   @PostMapping("/curso/{curso_id}")
    public Aluno addCursoAluno(@PathVariable int curso_id, @RequestBody Aluno aluno){
        return alunoService.addCursoAluno(curso_id, aluno);

    }


    @PutMapping("/update/{id}")
    public ResponseEntity<MessagePayload> update(@PathVariable int id, @RequestBody Aluno aluno){
        try {
            alunoService.update(id, aluno);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new MessagePayload("Aluno alterado com sucesso!"));
        } catch (ResourceNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload(ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new MessagePayload(ex.getMessage()));
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MessagePayload> deleteByCod(@PathVariable int id){
        try {
            alunoService.delete(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new MessagePayload("Aluno deletado com sucesso!"));
        } catch (ResourceNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload(ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new MessagePayload(ex.getMessage()));
        }
    }
}
