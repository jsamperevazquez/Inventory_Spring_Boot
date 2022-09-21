package com.company.inventory.services;

import com.company.inventory.model.Category;
import com.company.inventory.response.CategoryResponseRest;
import org.springframework.http.ResponseEntity;

/**
 * Interface to declare all the methods of the service
 * ResponseEntity allow giving an http response
 */
public interface ICategoryService {
    ResponseEntity<CategoryResponseRest> search();
    ResponseEntity<CategoryResponseRest> searchById(Long id);
    ResponseEntity<CategoryResponseRest> save(Category category);
    ResponseEntity<CategoryResponseRest> put(Category category,Long id);

}
