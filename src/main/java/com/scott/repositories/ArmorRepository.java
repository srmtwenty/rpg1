package com.scott.repositories;

import com.scott.models.Armor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArmorRepository extends JpaRepository<Armor, Long> {
}
