package rikkei.academy.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IGenericService <T>{
    Page<T> findAll(Pageable pageable);
    Optional<T> findById(Long id);
    T save(T t);
    void deleteById(Long id);
}
