package com.company.inventory.controller;

import com.company.inventory.model.Category;
import com.company.inventory.response.CategoryResponseRest;
import com.company.inventory.services.ICategoryService;
import com.company.inventory.util.CategoryExcelExport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Controller class to allow other applications can consume it
 *
 * @RequestMapping is the general uri of our application
 */
@CrossOrigin(originPatterns = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS, RequestMethod.PUT})
@RestController
@RequestMapping("/api/v1")
public class CategoryRestController {
    /**
     * Variable type ICategoryService to use methods (autowired to dependencies inject)
     */
    @Autowired
    private ICategoryService service;

    /**
     * Method to get all categories
     *
     * @return the query response
     * @GetMapping is a get method whit the general uri + the uri inside ()
     */
    @GetMapping("/categories")
    public ResponseEntity<CategoryResponseRest> searchCategories() {
        ResponseEntity<CategoryResponseRest> response = service.search();
        return response;
    }

    /**
     * Method to get category by ID
     *
     * @param id Id of the object
     * @return the query response
     */
    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryResponseRest> searchById(@PathVariable Long id) {
        ResponseEntity<CategoryResponseRest> response = service.searchById(id);
        return response;
    }

    /**
     * Method to save a new category in our BD
     *
     * @param category category object that receives as parameter
     * @return response of query
     * @RequestBody return a JSON format to mapping a category object
     */
    @PostMapping("/categories")
    public ResponseEntity<CategoryResponseRest> save(@RequestBody Category category) {
        ResponseEntity<CategoryResponseRest> response = service.save(category);
        return response;
    }

    /**
     * Method to update the categories
     *
     * @param category category to update
     * @param id       id of the category
     * @return response of query
     */
    @PutMapping("/categories/{id}")
    public ResponseEntity<CategoryResponseRest> update(@RequestBody Category category, @PathVariable Long id) {
        ResponseEntity<CategoryResponseRest> response = service.put(category, id);
        return response;
    }

    /**
     * Method to delete category
     *
     * @param id category id
     * @return response
     */
    @DeleteMapping("/categories/{id}")
    public ResponseEntity<CategoryResponseRest> delete(@PathVariable Long id) {
        ResponseEntity<CategoryResponseRest> response = service.deleteById(id);
        return response;
    }

    /**
     * Export to excel file
     *
     * @param response
     * @throws IOException
     */
    @GetMapping("/categories/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {

        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename= result_category";

        response.setHeader(headerKey, headerValue);

        ResponseEntity<CategoryResponseRest> categoryResponse = service.search();

        CategoryExcelExport excelExport = new CategoryExcelExport(categoryResponse.getBody().getCategoryResponse().getCategory());
        excelExport.export(response);
    }

}
