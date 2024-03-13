package com.hkbusiness.microserviceinventory.dao;

import com.hkbusiness.microserviceinventory.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, String> {
}
