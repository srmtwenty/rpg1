package com.scott.repositories;

import com.scott.models.Filedb;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FiledbRepository extends JpaRepository<Filedb, String> {
    List<Filedb> findByOrderByNameAsc();
    Page<Filedb> findAll(Pageable pageable);
    List<Filedb> findByNameContaining(String name);

    Filedb findByName(String name);
}
