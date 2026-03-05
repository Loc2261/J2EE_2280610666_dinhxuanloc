package com.example.bai5.controller;

import com.example.bai5.model.Product;
import com.example.bai5.service.CategoryService;
import com.example.bai5.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService,
                             CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    // LIST
    @GetMapping
    public String list(Model model) {
        model.addAttribute("products", productService.getAll());
        return "product/products";
    }

    // FORM CREATE
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAll());
        return "product/create";
    }

    // 👉 POST CREATE (QUAN TRỌNG)
    @PostMapping("/create")
    public String create(@ModelAttribute Product product,
                         @RequestParam("imageFile") MultipartFile imageFile) {

        // upload ảnh
        productService.updateImage(product, imageFile);

        // lưu product
        productService.save(product);

        // quay lại danh sách
        return "redirect:/products";
    }
    // 1. Hiển thị Form Edit
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable int id, Model model) {
        Product product = productService.getById(id);
        if (product != null) {
            model.addAttribute("product", product);
            model.addAttribute("categories", categoryService.getAll());
            return "product/edit"; // Đường dẫn tới file edit.html (hoặc dùng chung file create)
        }
        return "redirect:/products";
    }

    // 2. Xử lý cập nhật Product
    @PostMapping("/edit/{id}")
    public String update(@PathVariable int id,
                         @ModelAttribute Product product,
                         @RequestParam("imageFile") MultipartFile imageFile) {

        // Đảm bảo ID được giữ nguyên để JPA hiểu đây là lệnh Update chứ không phải Create mới
        product.setId(id);

        // Xử lý ảnh: Nếu người dùng chọn ảnh mới thì cập nhật, không thì giữ ảnh cũ
        if (!imageFile.isEmpty()) {
            productService.updateImage(product, imageFile);
        } else {
            // Lấy lại ảnh cũ từ database để tránh bị null khi lưu
            Product existingProduct = productService.getById(id);
            product.setImage(existingProduct.getImage());
        }

        productService.save(product);
        return "redirect:/products";
    }
    // DELETE
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        productService.delete(id);
        return "redirect:/products";
    }
}