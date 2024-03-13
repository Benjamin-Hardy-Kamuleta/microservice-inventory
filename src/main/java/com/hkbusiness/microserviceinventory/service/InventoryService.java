package com.hkbusiness.microserviceinventory.service;

import com.hkbusiness.microserviceinventory.exception.ProductCodeNotFoundException;
import com.hkbusiness.microserviceinventory.model.Inventory;
import com.hkbusiness.microserviceinventory.model.dto.InventoryRequest;
import com.hkbusiness.microserviceinventory.model.dto.InventoryResponse;

import java.util.List;

public interface InventoryService {
    Inventory checkProductStock(String productCode) throws ProductCodeNotFoundException;
    Inventory addStock(Inventory inventory) throws ProductCodeNotFoundException;
    Inventory updateStock(Inventory inventory);
    List<Inventory> availableProducts();

    boolean deleteInventoryByProductCode(String productCode);

    List<Inventory> inventories();
}
