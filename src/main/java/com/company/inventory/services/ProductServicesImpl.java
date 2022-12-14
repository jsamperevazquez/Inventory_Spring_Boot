package com.company.inventory.services;

import com.company.inventory.dao.ICategoryDao;
import com.company.inventory.dao.IProductDao;
import com.company.inventory.model.Category;
import com.company.inventory.model.Product;
import com.company.inventory.response.ProductResponseRest;
import com.company.inventory.util.Util;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServicesImpl implements IProductService {

    private ICategoryDao categoryDao;
    private IProductDao productDao;


    public ProductServicesImpl(ICategoryDao categoryDao, IProductDao productDao) {
        super();
        this.categoryDao = categoryDao;
        this.productDao = productDao;
    }

    @Override
    @Transactional
    public ResponseEntity<ProductResponseRest> save(Product product, Long categoryId) {
        ProductResponseRest response = new ProductResponseRest();
        List<Product> listProduct = new ArrayList<>();
        try {
            // Search category to set in the product object
            Optional<Category> category = categoryDao.findById(categoryId);
            if (category.isPresent()) {
                product.setCategory(category.get());
            } else {
                response.setMetadata("Response NOK", "-1", "Category not found");
                return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND);
            }

            // Save product
            Product productSave = productDao.save(product);
            if (productSave != null) {
                listProduct.add(productSave);
                response.getProduct().setProducts(listProduct);
                response.setMetadata("Response ok", "00", "Product save");
            } else {
                response.setMetadata("Response NOK", "-1", "Product not save");
                return new ResponseEntity<ProductResponseRest>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            response.setMetadata("Response NOK", "-1", "Error saving product");
            System.out.println(e.getStackTrace());
            return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ProductResponseRest> searchById(Long id) {
        ProductResponseRest response = new ProductResponseRest();
        List<Product> listProduct = new ArrayList<>();
        try {
            // Search product by ID
            Optional<Product> product = productDao.findById(id);
            if (product.isPresent()) {
                byte[] imageDescompressed = Util.decompressZLib(product.get().getPicture());
                product.get().setPicture(imageDescompressed);
                listProduct.add(product.get());
                response.getProduct().setProducts(listProduct);
                response.setMetadata("Response OK", "00", "Product found");
            } else {
                response.setMetadata("Response NOK", "-1", "Product not found");
                return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            response.setMetadata("Response NOK", "-1", "Error searching product");
            System.out.println(e.getStackTrace());
            return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ProductResponseRest> searchByName(String name) {
        ProductResponseRest response = new ProductResponseRest();
        List<Product> listProduct = new ArrayList<>();
        List<Product> listAuxProduct = new ArrayList<>();

        try {
            // Search product by Name
            listAuxProduct = productDao.findByNameContainingIgnoreCase(name);
            if (listAuxProduct.size() > 0) {
                listAuxProduct.stream().forEach((product -> {
                    byte[] imageDescompressed = Util.decompressZLib(product.getPicture());
                    product.setPicture(imageDescompressed);
                    listProduct.add(product);
                }));
                response.getProduct().setProducts(listProduct);
                response.setMetadata("Response OK", "00", "Product found");
            } else {
                response.setMetadata("Response NOK", "-1", "Product not found");
                return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            response.setMetadata("Response NOK", "-1", "Error searching product");
            System.out.println(e.getStackTrace());
            return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<ProductResponseRest> deleteById(Long id) {

        ProductResponseRest response = new ProductResponseRest();
        try {
            // Delete product by ID
            productDao.deleteById(id);
            response.setMetadata("Response OK", "00", "Product deleted");
        } catch (Exception e) {
            response.setMetadata("Response NOK", "-1", "Error deleting product");
            System.out.println(e.getStackTrace());
            return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);

    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ProductResponseRest> search() {
        ProductResponseRest response = new ProductResponseRest();
        List<Product> listProduct = new ArrayList<>();
        List<Product> listAuxProduct = new ArrayList<>();

        try {
            // Search product
            listAuxProduct = (List<Product>) productDao.findAll();
            if (listAuxProduct.size() > 0) {
                listAuxProduct.stream().forEach((product -> {
                    byte[] imageDescompressed = Util.decompressZLib(product.getPicture());
                    product.setPicture(imageDescompressed);
                    listProduct.add(product);
                }));
                response.getProduct().setProducts(listProduct);
                response.setMetadata("Response OK", "00", "Products found");
            } else {
                response.setMetadata("Response NOK", "-1", "Products not found");
                return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            response.setMetadata("Response NOK", "-1", "Error searching products");
            System.out.println(e.getStackTrace());
            return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<ProductResponseRest> update(Product product, Long categoryId, Long id) {
        ProductResponseRest response = new ProductResponseRest();
        List<Product> listProduct = new ArrayList<>();
        try {
            //  category to set in the product object
            Optional<Category> category = categoryDao.findById(categoryId);
            if (category.isPresent()) {
                product.setCategory(category.get());
            } else {
                response.setMetadata("Response NOK", "-1", "Category not found");
                return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND);
            }

            // Search product to update
            Optional<Product> productSearch = productDao.findById(id);
            if (productSearch.isPresent()) {

                // Update product
                productSearch.get().setAmount(product.getAmount());
                productSearch.get().setCategory(product.getCategory());
                productSearch.get().setPrice(product.getPrice());
                productSearch.get().setName(product.getName());
                productSearch.get().setPicture(product.getPicture());

                //Save the product
                Product productToUpdate = productDao.save(productSearch.get());
                if (productToUpdate != null){
                    listProduct.add(productToUpdate);
                    response.getProduct().setProducts(listProduct);
                    response.setMetadata("Response ok", "00", "Product updated");
                }else {
                    response.setMetadata("Response NOK", "-1", "Product not updated");
                    return new ResponseEntity<ProductResponseRest>(response, HttpStatus.BAD_REQUEST);
                }
            } else {
                response.setMetadata("Response NOK", "-1", "Product not updated");
                return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.setMetadata("Response NOK", "-1", "Error saving product");
            System.out.println(e.getStackTrace());
            return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);
    }
}
