package com.company.inventory.services;

import com.company.inventory.response.CategoryResponseRest;
import org.springframework.http.ResponseEntity;

/**
 * Interface to declare all the methods of the service
 * ResponseEntity allow giving an http response
 */
public interface ICategoryService {
    ResponseEntity<CategoryResponseRest> search();
}
