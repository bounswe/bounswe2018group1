package com.cmpe451.retro.data.repositories;

import com.cmpe451.retro.data.entities.Memory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface MemoryRepository extends CrudRepository<Memory,Long>, PagingAndSortingRepository<Memory, Long> {

    Optional<Memory> findById(Long id);
    Page<Memory> findAll(Pageable pageable);

    Page<Memory> findByUserId(Long userId, Pageable pageable);

}
