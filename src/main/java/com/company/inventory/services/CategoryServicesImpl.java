package com.company.inventory.services;

import com.company.inventory.dao.ICategoryDao;
import com.company.inventory.model.Category;
import com.company.inventory.response.CategoryResponseRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    /**
     * Method to search category by id
     * @param id ID of the object to search
     * @return response of query
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<CategoryResponseRest> searchById(Long id) {
        CategoryResponseRest response = new CategoryResponseRest();
        List<Category> list = new ArrayList<>();
        try {
            Optional<Category> category = categoryDao.findById(id);
            if (category.isPresent()){
                list.add(category.get());
                response.getCategoryResponse().setCategory(list);
                response.setMetadata("Ok","1","Good Response");

            }else{
                response.setMetadata("Nok","-1","category not found");
                System.out.println( HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            response.setMetadata("Nok","-1","Bad Response in id query");
            e.getStackTrace();
            System.out.println( HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
    }

    /**
     * Method to save a new category object
     * @param category category that receives as parameter to save
     * @return response of query
     */
    @Override
    @Transactional
    public ResponseEntity<CategoryResponseRest> save(Category category) {
        CategoryResponseRest response = new CategoryResponseRest();
        List<Category> list = new ArrayList<>();
        try {
           Category categorySaved = categoryDao.save(category);
            if (categorySaved != null){
                list.add(categorySaved);
                response.setMetadata("Ok","1","Category save");
                response.getCategoryResponse().setCategory(list);
            }else {
                response.setMetadata("Nok","-1","Category not save");
                System.out.println( HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            response.setMetadata("Nok","-1","Error saving category");
            e.getStackTrace();
            System.out.println( HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
    }

    /**
     * Method to update the category
     * @param category
     * @param id
     * @return
     */
    @Override
    @Transactional
    public ResponseEntity<CategoryResponseRest> put(Category category, Long id) {
        CategoryResponseRest response = new CategoryResponseRest();
        List<Category> list = new ArrayList<>();
        try {
            Optional<Category> categorySearch = categoryDao.findById(id);
            if (categorySearch.isPresent()){

                // Update the categories
                categorySearch.get().setName(category.getName());
                categorySearch.get().setDescription(category.getDescription());

                //Save the new parameters
                Category categoryToUpdate = categoryDao.save(categorySearch.get());

                if (categoryToUpdate != null){
                    list.add(categoryToUpdate);
                    response.getCategoryResponse().setCategory(list);
                    response.setMetadata("OK","1","Category update");
                }else {
                    response.setMetadata("NOK","-1","Category not update");
                    System.out.println( HttpStatus.BAD_REQUEST );
                }

            }else {
                response.setMetadata("Nok","0","ID not found");
                System.out.println( HttpStatus.NOT_FOUND);
            }

        }catch (Exception e){
            response.setMetadata("Nok","-1","Error updating category");
            e.getStackTrace();
            System.out.println( HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
    }
}
