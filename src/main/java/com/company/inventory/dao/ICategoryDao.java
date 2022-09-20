package com.company.inventory.dao;

import com.company.inventory.model.Category;
import org.springframework.data.repository.CrudRepository;

/**
 * Interface to allow access to out data
 */
public interface ICategoryDao extends CrudRepository <Category, Long>{

}
