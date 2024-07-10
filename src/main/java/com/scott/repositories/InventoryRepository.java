package com.scott.repositories;

import com.scott.models.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.*;


public interface InventoryRepository extends JpaRepository<Inventory, Long> {



}
