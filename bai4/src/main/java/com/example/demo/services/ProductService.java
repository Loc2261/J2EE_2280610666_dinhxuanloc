package com.example.demo.services;



import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.models.Product;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

@Service
public class ProductService {
    private List<Product> listProduct = new ArrayList<>();

    public List<Product> getAll() { return listProduct; }

    public Product get(int id) {
        return listProduct.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void add(Product newProduct) {
        int maxId = listProduct.stream()
                .mapToInt(Product::getId)
                .max()
                .orElse(0);
        newProduct.setId(maxId + 1);
        listProduct.add(newProduct);
    }

    public void update(Product editProduct) {
        Product find = get(editProduct.getId());
        if (find != null) {
            find.setName(editProduct.getName());
            find.setPrice(editProduct.getPrice());
            find.setCategory(editProduct.getCategory());
            // Chỉ cập nhật ảnh nếu đối tượng sửa có tên ảnh mới
            if (editProduct.getImage() != null && !editProduct.getImage().isEmpty()) {
                find.setImage(editProduct.getImage());
            }
        }
    }

    public void delete(int id) {
        listProduct.removeIf(p -> p.getId() == id);
    }

    public void updateImage(Product product, MultipartFile imageProduct) {
        if (imageProduct != null && !imageProduct.isEmpty()) {
            try {
                // 1. Lấy đường dẫn tuyệt đối đến thư mục static/images trong dự án
                String uploadDir = "src/main/resources/static/images/";
                Path uploadPath = Paths.get(uploadDir);

                // 2. Tạo thư mục nếu chưa tồn tại
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                // 3. Tạo tên file duy nhất (để tránh trùng lặp)
                String fileName = UUID.randomUUID().toString() + "_" + imageProduct.getOriginalFilename();

                // 4. Lưu file
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(imageProduct.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                // 5. Lưu tên file vào đối tượng product
                product.setImage(fileName);

                // Mẹo nhỏ: In ra để kiểm tra xem file thực sự nằm ở đâu trên máy bạn
                System.out.println("File saved to: " + filePath.toAbsolutePath());

            } catch (IOException e) {
                System.err.println("Lỗi lưu file: " + e.getMessage());
            }
        }
    }
}