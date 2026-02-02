package com.example.demo.models;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor // BẮT BUỘC có cái này để fix lỗi CategoryService:[12,26]
public class Category {
    private int id;
    @NotBlank(message = "Tên danh mục không được để trống")
    private String name;
}