package com.company.inventory.services;

import com.company.inventory.dao.ICategoryDao;
import com.company.inventory.model.Category;
import com.company.inventory.response.CategoryResponseRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service class to implements all method
 * @service is used to say that is a service class
 */
@Service
public class CategoryServicesImpl implements ICategoryService{
    /**
     * variable type ICategoryDao with @Autowired to inject and allow use the object (Not necessary = new categoryDao)
     */
    @Autowired
    private ICategoryDao categoryDao;

    /**
     * Method to return the response our consult
     * @return the response with metadata
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<CategoryResponseRest> search() {
        CategoryResponseRest response = new CategoryResponseRest();
        try {
            List<Category> category = (List<Category>) categoryDao.findAll();
            response.getCategoryResponse().setCategory(category);
            response.setMetadata("ok","00","Good Response");
        }catch (Exception e){
            response.setMetadata("Nok","-1","Bad Response");
            e.getStackTrace();
            System.out.println( HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
    }
}
