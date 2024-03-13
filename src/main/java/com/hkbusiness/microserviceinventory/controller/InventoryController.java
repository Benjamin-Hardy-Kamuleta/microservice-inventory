package com.hkbusiness.microserviceinventory.controller;

import com.hkbusiness.microserviceinventory.exception.ProductCodeNotFoundException;
import com.hkbusiness.microserviceinventory.model.Inventory;
import com.hkbusiness.microserviceinventory.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
public class InventoryController {
    private final InventoryService inventoryService;
    private final Logger logger = LoggerFactory.getLogger(InventoryController.class);

    @Operation(tags = "Api Root")
    @GetMapping
    public void redirect(HttpServletResponse response) throws IOException {
        logger.info("redirecting to swagger");
        response.sendRedirect("/swagger-ui.html");
    }
    @Operation(
            summary = "Retrieve a product stock by product code",
            description = "Get stock of product by specifying his code",
            tags = { "Get product stock"})
    @ApiResponse(responseCode = "200", description = "successful operation", content = { @Content(schema = @Schema(implementation = Inventory.class), mediaType = "application/json") })
    @ApiResponse(responseCode = "404", description = "Product stock not found",content = { @Content(schema = @Schema()) })
    @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
    @GetMapping("/inventories/{productCode}")
    public Inventory checkProductStock(@PathVariable String productCode) throws ProductCodeNotFoundException {
        return inventoryService.checkProductStock(productCode);
    }

    @Operation(
            summary = "Add a stock to database",
            description = "Add a stock to database",
            tags = { "Add stock"})
    @ApiResponse(responseCode = "201", description = "new product stock added", content = { @Content(schema = @Schema(implementation = Inventory.class), mediaType = "application/json") })
    @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
    @PostMapping("/inventories")
    public ResponseEntity<Inventory>  addStock(@Valid @RequestBody Inventory inventory) throws ProductCodeNotFoundException {
        logger.info("saving new stock: {}", inventory);
        Inventory stockAdded =  inventoryService.addStock(inventory);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{productCode}")
                .buildAndExpand(stockAdded.getProductCode())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @Operation(
            summary = "Update an existing stock, if the provided product code is not found then" +
                    " this stock will be added as new stock",
            description = "Update an existing stock",
            tags = { "Update stock"})
    @ApiResponse(responseCode = "200", description = "successful operation", content = { @Content(schema = @Schema(implementation = Inventory.class), mediaType = "application/json") })
    @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
    @PutMapping("/inventories")
    public Inventory updateStock(@Valid @RequestBody Inventory inventory) {
        logger.info("updating stock: {}", inventory);
        return inventoryService.updateStock(inventory);
    }

    @Operation(
            summary = "Get all available products",
            description = "Get all available products",
            tags = { "Available products"})
    @ApiResponse(responseCode = "200", description = "successful operation", content = { @Content(schema = @Schema(implementation = Inventory.class), mediaType = "application/json") })
    @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
    @GetMapping("/inventories/products")
    public List<Inventory> availableProducts() {
        logger.info("fetching all available products");
        return inventoryService.availableProducts();
    }

    @Operation(
            summary = "Delete a stock",
            description = "Delete a stock by specifying the product code. The response is true if the stock is found " +
                    "and deleted, otherwise the response will be false",
            tags = { "Delete stock"})
    @ApiResponse(responseCode = "200", description = "successful operation", content = { @Content(schema = @Schema(implementation = Boolean.class)) })
    @ApiResponse(responseCode = "202", description = "The operation is accepted but the result will be false", content = { @Content(schema = @Schema(implementation = Boolean.class)) })
    @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
    @DeleteMapping("/inventories/{productCode}")
    public ResponseEntity<Boolean> deleteInventoryByProductCode(@PathVariable String productCode) {
        logger.info("deleting product stock with code: {}", productCode);
        if (inventoryService.deleteInventoryByProductCode(productCode)){
            return ResponseEntity.ok().body(Boolean.TRUE);
        }
        logger.info("Stock with product code: {} is not found to be deleted", productCode);
        return ResponseEntity.accepted().body(Boolean.FALSE);
    }

    @Operation(
            summary = "Get all stocks",
            description = "Get all available stocks",
            tags = { "All stocks"})
    @ApiResponse(responseCode = "200", description = "successful operation", content = { @Content(schema = @Schema(implementation = Inventory.class), mediaType = "application/json") })
    @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
    @GetMapping("/inventories")
    public List<Inventory> inventories() {
        logger.info("fetching all stocks");
        return inventoryService.inventories();
    }
}
