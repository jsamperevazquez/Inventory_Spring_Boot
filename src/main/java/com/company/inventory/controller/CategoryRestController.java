package com.company.inventory.controller;

import com.company.inventory.response.CategoryResponseRest;
import com.company.inventory.services.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * @return the response of the query response
     * @GetMapping is a get method whit the general uri + the uri inside ()
     */
    @GetMapping("categories")
    public ResponseEntity<CategoryResponseRest> searchCategories(){
        ResponseEntity<CategoryResponseRest> response = service.search();
        return response;
    }
}
