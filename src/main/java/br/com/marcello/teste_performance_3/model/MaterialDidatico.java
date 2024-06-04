package br.com.marcello.teste_performance_3.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "materiais")
public class MaterialDidatico {
    @Id
    private String id;
    private String nome;
    private String descricao;

    public MaterialDidatico(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }
}
