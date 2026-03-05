package com.example.demo.controllers;

import com.example.demo.models.Product;
import com.example.demo.services.CategoryService;
import com.example.demo.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired private ProductService productService;
    @Autowired private CategoryService categoryService;

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("listproduct", productService.getAll());
        return "product/products";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAll());
        return "product/create";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("product") Product product, BindingResult result,
                         @RequestParam("category.id") int categoryId,
                         @RequestParam("imageProduct") MultipartFile imageProduct, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAll());
            return "product/create";
        }
        productService.updateImage(product, imageProduct);
        product.setCategory(categoryService.get(categoryId));
        productService.add(product);
        return "redirect:/products";
    }

    // 1. Mở trang Edit và đổ dữ liệu sản phẩm cũ vào form
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable int id, Model model) {
        Product product = productService.get(id);
        if (product == null) {
            return "redirect:/products";
        }
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAll());
        return "product/edit";
    }

    // 2. Xử lý lưu thông tin sau khi sửa
    @PostMapping("/edit")
    public String editSubmit(@Valid @ModelAttribute("product") Product product,
                             BindingResult result,
                             @RequestParam("category.id") int categoryId,
                             @RequestParam("imageProduct") MultipartFile imageProduct,
                             Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAll());
            return "product/edit";
        }

        // Xử lý ảnh: Nếu người dùng chọn ảnh mới thì mới cập nhật
        if (!imageProduct.isEmpty()) {
            productService.updateImage(product, imageProduct);
        } else {
            // Giữ lại tên ảnh cũ nếu không upload ảnh mới
            Product oldProduct = productService.get(product.getId());
            product.setImage(oldProduct.getImage());
        }

        product.setCategory(categoryService.get(categoryId));
        productService.update(product);
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        productService.delete(id);
        return "redirect:/products";
    }
}