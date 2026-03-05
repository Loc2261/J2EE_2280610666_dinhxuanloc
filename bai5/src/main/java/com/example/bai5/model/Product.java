package com.example.bai5.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Tên sản phẩm không được để trống")
    private String name;

    private String image;

    @NotNull(message = "Giá sản phẩm không được để trống")
    @Min(value = 1, message = "Giá phải >= 1")
    @Max(value = 9999999, message = "Giá quá lớn")
    private long price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}