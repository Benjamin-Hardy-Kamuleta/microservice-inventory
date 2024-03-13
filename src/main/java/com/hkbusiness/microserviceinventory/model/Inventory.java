package com.hkbusiness.microserviceinventory.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Inventory {
    @Id
    @NotBlank(message = "productCode cannot be blank")
    private String productCode;
    @PositiveOrZero(message = "productQuantity must be positive or 0")
    private Integer productQuantity;
    @PositiveOrZero(message = "productPrice must be positive or 0.0")
    private Double productPrice;

}
