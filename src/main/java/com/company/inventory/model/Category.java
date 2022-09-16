package com.company.inventory.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
@Data // this is from lombok library to avoid use the setter and getters (press command + 7 into category class to show methods)
@Entity
@Table (name = "category")
public class Category implements Serializable {
    /**
     * ID class
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    /**
     *  Serial UID from Serializable
     */
    private static final long serialVersionUID = -4355535631243545434L;
    /**
     * Product name
     */
    private String name;
    /**
     * Product description
     */
    private String description;

}
