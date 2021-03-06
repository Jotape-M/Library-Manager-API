package com.joaopedro.librarymanager.exception.aluguel;


import javax.persistence.EntityNotFoundException;

public class AluguelNotFoundException extends EntityNotFoundException {

    public AluguelNotFoundException(Long id) {
        super(String.format("Aluguel with id S% does not exist", id));
    }
}
