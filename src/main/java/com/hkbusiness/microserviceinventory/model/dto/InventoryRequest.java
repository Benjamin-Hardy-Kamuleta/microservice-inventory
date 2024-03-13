package com.hkbusiness.microserviceinventory.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryRequest {
    private String productCode;
    private Integer productQuantity;
    private Double productPrice;
}
