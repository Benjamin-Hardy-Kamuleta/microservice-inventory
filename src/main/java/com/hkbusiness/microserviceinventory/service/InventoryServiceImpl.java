package com.hkbusiness.microserviceinventory.service;

import com.hkbusiness.microserviceinventory.dao.InventoryRepository;
import com.hkbusiness.microserviceinventory.exception.ProductCodeNotFoundException;
import com.hkbusiness.microserviceinventory.model.Inventory;
import com.hkbusiness.microserviceinventory.model.ProductClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService{

    private final InventoryRepository inventoryRepository;

    @Override
    public Inventory checkProductStock(String productCode) throws ProductCodeNotFoundException {
       return inventoryRepository.findById(productCode).orElseThrow(
                       ()->new ProductCodeNotFoundException("Product with code "+productCode+" is not found")
               );
    }

    @Override
    public Inventory addStock(Inventory inventory) throws ProductCodeNotFoundException {
        if (!isProductExist(inventory.getProductCode())){
            throw new ProductCodeNotFoundException("Product with code "+inventory.getProductCode()+" does not exist");
        }
        return inventoryRepository.save(inventory);
    }

    @Override
    public Inventory updateStock(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    @Override
    public List<Inventory> availableProducts() {
        return inventoryRepository.findAll().stream().filter(inv -> inv.getProductQuantity()>0).toList();
    }

    @Override
    public boolean deleteInventoryByProductCode(String productCode) {
        Optional<Inventory> inventoryToDelete =  inventoryRepository.findAll().stream()
                .filter(product -> product.getProductCode().equals(productCode)).findFirst();
        if (inventoryToDelete.isPresent()){
            inventoryRepository.deleteById(productCode);
            return true;
        }
        return false;
    }

    @Override
    public List<Inventory> inventories() {
        return inventoryRepository.findAll();
    }
    private boolean isProductExist(String productCode){
        List<String> allExistingProductCodes = new ArrayList<>();
        allExistingProductCodes.add("pr");
        return allExistingProductCodes.contains(productCode);

    }
}
