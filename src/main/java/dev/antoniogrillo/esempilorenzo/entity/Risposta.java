package dev.antoniogrillo.esempilorenzo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Risposta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String testo;
    private boolean vera;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_domanda",nullable = false)
    private Domanda domanda;
}
