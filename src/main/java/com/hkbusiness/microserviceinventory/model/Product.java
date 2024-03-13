package com.hkbusiness.microserviceinventory.model;

import java.time.LocalDate;

public record Product(String productCode, String productName, String productDescription,
                      String productMadeIn, LocalDate productExpDate, LocalDate productManufDate) {
}
