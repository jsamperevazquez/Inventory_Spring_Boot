package com.company.inventory.response;

import com.company.inventory.model.Category;
import lombok.Data;

import java.util.List;

/**
 * @author Jose Angel Sampere Vazquez
 * @version 0.0.1
 * Class to save category objects
 */
@Data
public class CategoryResponse {
    /**
     * List to assign category object
     */
    private List<Category> category;

}
