package com.company.inventory.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Jose Angel Sampere Vazquez
 * @version 0.0.1
 * Class to instance categoryResponse
 */
@Getter
@Setter
public class CategoryResponseRest extends ResponseRest{

    /**
     * Variable to assign category response
     */
    private CategoryResponse categoryResponse = new CategoryResponse();

}
