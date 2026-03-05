package com.example.bai5.config;

import com.example.bai5.model.Category;
import com.example.bai5.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initCategories(CategoryRepository categoryRepository) {
        return args -> {

            // Nếu DB đã có category thì KHÔNG tạo nữa
            if (categoryRepository.count() > 0) return;

            categoryRepository.save(new Category( "Điện thoại"));
            categoryRepository.save(new Category("Laptop"));
            categoryRepository.save(new Category( "Máy tính bảng"));
            categoryRepository.save(new Category( "Phụ kiện"));
            categoryRepository.save(new Category( "Thiết bị mạng"));

            System.out.println("✅ Đã tạo dữ liệu Category mẫu");
        };
    }
}