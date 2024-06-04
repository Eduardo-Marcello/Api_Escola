package br.com.marcello.teste_performance_3.controller;

import br.com.marcello.teste_performance_3.exception.ResourceNotFoundException;
import br.com.marcello.teste_performance_3.model.Curso;
import br.com.marcello.teste_performance_3.payload.MessagePayload;
import br.com.marcello.teste_performance_3.repository.CursoRepository;
import br.com.marcello.teste_performance_3.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/curso")
public class CursoController {

    @Autowired
    final private CursoService cursoService;
    @Autowired
    private CursoRepository cursoRepository;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Curso>> getAll(@RequestParam(required = false) Optional<String> codigo){
        if(codigo.isEmpty()){
            return ResponseEntity.ok((List<Curso>) cursoService.getAll());
        } else {
            return ResponseEntity.notFound().build();
            /*if(cursoService.getByCod(String.valueOf(codigo)) == null){
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(cursoService.getByCod(String.valueOf(codigo)));
            }*/
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> getById(@PathVariable int id){
        try {
            return ResponseEntity.ok(cursoService.getById(id));
        }catch (ResourceNotFoundException ex){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/lista")
    public ResponseEntity<List<Curso>> listAllCache(){
        try {
            return ResponseEntity.ok(cursoService.buscarCursos());
        } catch (ResourceNotFoundException ex){
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/cache/{id}")
    public ResponseEntity<Optional<Curso>> getByIdCache(@PathVariable int id){
        try{
            return ResponseEntity.ok(cursoService.buscarCurso(id));
        } catch (ResourceNotFoundException ex){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/save")
    public void save(@RequestBody Curso curso){
        cursoService.save(curso);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MessagePayload> update(@PathVariable int id, @RequestBody Curso curso){
        try {
            cursoService.update(id, curso);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new MessagePayload("Curso alterado com sucesso!"));
        } catch (ResourceNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload(ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new MessagePayload(ex.getMessage()));
        }
    }

    @PutMapping("/update/cache/{id}")
    public ResponseEntity<Curso> updateCache(@PathVariable int id, @RequestBody Curso curso){
       try{
            return ResponseEntity.ok(cursoService.updateCache(id, curso));
       } catch (ResourceNotFoundException ex){
           return ResponseEntity.notFound().build();
       }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MessagePayload> deleteByCod(@PathVariable int id){
        try {
            cursoService.delete(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new MessagePayload("Curso deletado com sucesso!"));
        } catch (ResourceNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload(ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new MessagePayload(ex.getMessage()));
        }
    }

    @DeleteMapping("/delete/cache/{id}")
    public ResponseEntity<MessagePayload> deleteCacheCache(@PathVariable int id){
        try {
            cursoService.deleteCacheById(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new MessagePayload("Curso deletado com sucesso!"));
        } catch (ResourceNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload(ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new MessagePayload(ex.getMessage()));
        }
    }

}
