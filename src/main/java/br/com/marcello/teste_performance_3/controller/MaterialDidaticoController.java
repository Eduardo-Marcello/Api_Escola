package br.com.marcello.teste_performance_3.controller;

import br.com.marcello.teste_performance_3.model.MaterialDidatico;
import br.com.marcello.teste_performance_3.service.MaterialDidaticoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/material")
public class MaterialDidaticoController {

    @Autowired
    private MaterialDidaticoService materialDidaticoService;

    @GetMapping("/all")
    public List<MaterialDidatico> getAll() {
        return materialDidaticoService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<MaterialDidatico> getById(@PathVariable String id) {
        return materialDidaticoService.findById(id);
    }

    @PostMapping("/save")
    public MaterialDidatico save(@RequestBody MaterialDidatico material) {
        return materialDidaticoService.save(material);
    }

    @PutMapping("/update/{id}")
    public MaterialDidatico update(@PathVariable String id ,@RequestBody MaterialDidatico material) {
        return materialDidaticoService.update(id, material);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable String id) {
        materialDidaticoService.delete(id);
    }
}
