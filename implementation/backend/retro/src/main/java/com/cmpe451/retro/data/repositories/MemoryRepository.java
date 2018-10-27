package com.cmpe451.retro.data.repositories;

import com.cmpe451.retro.data.entities.Memory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface MemoryRepository extends CrudRepository<Memory,Long> {

    Optional<Memory> findById(Long id);
    List<Memory> findAll();

}
