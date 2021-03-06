package com.joaopedro.librarymanager.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "livro")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 40)
    private String nome;

    @Column(nullable = false, length = 40)
    private String autor;

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "dd-MM-yyyy")
    private LocalDate lancamento;

    @Column(nullable = false)
    private int quantidade;

    @ManyToOne
    @JoinColumn(name = "editora", nullable = false)
    private Editora editora;

    @OneToMany(mappedBy = "livro", fetch = FetchType.LAZY)
    private List<Aluguel> aluguelList;
}
