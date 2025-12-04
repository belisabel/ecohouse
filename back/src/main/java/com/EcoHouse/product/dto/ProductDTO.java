package com.EcoHouse.product.dto;

import lombok.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;

    private String imageUrl;
    private List<String> additionalImages;

    private Integer stock;

    private Long brandId;
    private Long categoryId;

    private Long environmentalDataId;

    private List<Long> certificationIds;

    private Boolean isActive;
    private Date createdAt;
}
