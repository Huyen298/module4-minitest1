package com.codegym.service;

import org.springframework.expression.Operation;

import java.util.Optional;

public interface IGeneralService <T>{
    Iterable<T> findAll();

    Optional<T> findById(Long id);

    void save(T t);

    void remove(Long id);
}
