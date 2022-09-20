package com.company.inventory.controller;

import com.company.inventory.model.Category;
import com.company.inventory.response.CategoryResponseRest;
import com.company.inventory.services.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class to allow other applications can consume it
 * @RequestMapping is the general uri of our application
 */
@RestController
@RequestMapping("/api/v1")
public class CategoryRestController{
    /**
     * Variable type ICategoryService to use methods (autowired to dependencies inject)
     */
    @Autowired
    private ICategoryService service;

    /**
     * Method to get all categories
     * @return the query response
     * @GetMapping is a get method whit the general uri + the uri inside ()
     */
    @GetMapping("/categories")
    public ResponseEntity<CategoryResponseRest> searchCategories(){
        ResponseEntity<CategoryResponseRest> response = service.search();
        return response;
    }

    /**
     * Method to get category by ID
     * @param id Id of the object
     * @return the query response
     */
    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryResponseRest> searchById(@PathVariable Long id){
        ResponseEntity<CategoryResponseRest> response = service.searchById(id);
        return response;
    }

    /**
     * Method to save a new category in our BD
     * @RequestBody return a JSON format to mapping a category object
     * @param category category object that receives as parameter
     * @return response of query
     */
    @PostMapping("/categories")
    public ResponseEntity<CategoryResponseRest> save(@RequestBody Category category){
        ResponseEntity<CategoryResponseRest> response = service.save(category);
        return response;
    }


}
