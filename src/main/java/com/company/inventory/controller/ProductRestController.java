package com.company.inventory.controller;

import com.company.inventory.model.Product;
import com.company.inventory.response.ProductResponseRest;
import com.company.inventory.services.IProductService;
import com.company.inventory.util.Util;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("api/v1")
public class ProductRestController {
    public ProductRestController(IProductService iProductService) {
        this.iProductService = iProductService;
    }

    private IProductService iProductService;

    /**
     * Save Method
     *
     * @param picture
     * @param name
     * @param price
     * @param amount
     * @param categoryId
     * @return
     * @throws IOException
     */
    @PostMapping("/products")
    public ResponseEntity<ProductResponseRest> save(
            @RequestParam("picture") MultipartFile picture,
            @RequestParam("name") String name,
            @RequestParam("price") int price,
            @RequestParam("amount") int amount,
            @RequestParam("categoryId") Long categoryId
    ) throws IOException {
        Product product = new Product();
        product.setName(name);
        product.setAmount(amount);
        product.setPrice(price);
        product.setPicture(Util.compressZLib(picture.getBytes()));

        ResponseEntity<ProductResponseRest> response = iProductService.save(product, categoryId);
        return response;
    }

    /**
     * Search by ID method
     *
     * @param id
     * @return response
     */
    @GetMapping("/products/{id}")
    public ResponseEntity<ProductResponseRest> searchById(@PathVariable Long id) {
        ResponseEntity<ProductResponseRest> response = iProductService.searchById(id);
        return response;
    }

    /**
     * Search by name Method
     *
     * @param name
     * @return response
     */
    @GetMapping("/products/filter/{name}")
    public ResponseEntity<ProductResponseRest> searchByName(@PathVariable String name) {
        ResponseEntity<ProductResponseRest> response = iProductService.searchByName(name);
        return response;
    }

    /**
     * Delete by ID Method
     *
     * @param id
     * @return response
     */
    @DeleteMapping("/products/{id}")
    public ResponseEntity<ProductResponseRest> deleteById(@PathVariable Long id) {
        ResponseEntity<ProductResponseRest> response = iProductService.deleteById(id);
        return response;
    }

    @GetMapping("/products")
    public ResponseEntity<ProductResponseRest> search() {
        ResponseEntity<ProductResponseRest> response = iProductService.search();
        return response;
    }

    /**
     * Update product method
     * @param picture
     * @param name
     * @param price
     * @param amount
     * @param categoryId
     * @param id
     * @return response
     * @throws IOException
     */
    @PutMapping("/products/{id}")
    public ResponseEntity<ProductResponseRest> update(
            @RequestParam("picture") MultipartFile picture,
            @RequestParam("name") String name,
            @RequestParam("price") int price,
            @RequestParam("amount") int amount,
            @RequestParam("categoryId") Long categoryId,
            @PathVariable Long id
    ) throws IOException {
        Product product = new Product();
        product.setName(name);
        product.setAmount(amount);
        product.setPrice(price);
        product.setPicture(Util.compressZLib(picture.getBytes()));

        ResponseEntity<ProductResponseRest> response = iProductService.update(product, categoryId,id);
        return response;
    }


}


