package com.example.bai5.service;

import com.example.bai5.model.Product;
import com.example.bai5.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product getById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void delete(int id) {
        productRepository.deleteById(id);
    }

    // upload ảnh
    public void updateImage(Product product, MultipartFile imageFile) {
        if (imageFile == null || imageFile.isEmpty()) return;

        try {
            Path uploadDir = Paths.get("src/main/resources/static/images");
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
            Path filePath = uploadDir.resolve(fileName);

            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            product.setImage(fileName);

        } catch (IOException e) {
            throw new RuntimeException("Upload ảnh thất bại", e);
        }
    }
}