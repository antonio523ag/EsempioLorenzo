package dev.antoniogrillo.esempilorenzo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Domanda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Difficolta difficolta;
    @ManyToOne
    @JoinColumn(name = "id_categoria",nullable = false)
    private Categoria categoria;
    private String domanda;
    @OneToMany(mappedBy = "domanda")
    private List<Risposta> risposte;
}
