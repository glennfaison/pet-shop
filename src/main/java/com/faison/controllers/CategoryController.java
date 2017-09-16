package com.faison.controllers;

import com.faison.models.Category;
import com.faison.services.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.ejb.EJB;
import java.util.ArrayList;

@RestController
@RequestMapping(value = "/api/categories")
@Api(value = "Category Rest Controller")
public class CategoryController {

    @EJB
    private CategoryService service;

    /**
     * Create a new category in the system.
     *
     * @param category the category to be created
     * @return an HttpStatus.CREATED if category was successfully created
     * @see CategoryService#create(Category)
     */
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ApiOperation(value = "create", notes = "Creates and saves a new Category.")
    public ResponseEntity<Category> create(@RequestBody Category category) {
        final Category result = service.create(category);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/").buildAndExpand("").toUri());
        return new ResponseEntity<>(result, headers, HttpStatus.CREATED);
    }

    /**
     * Get category with given category id.
     *
     * @param id the id of the category to return
     * @return the category of given id
     * @see CategoryService#findById(String)
     */
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "getById", notes = "Gets a Category by their id.")
    public ResponseEntity<Category> getById(@PathVariable(value = "id") String id) {
        final Category result = service.findById(id);
        return new ResponseEntity<>(result, HttpStatus.FOUND);
    }

    /**
     * Search for categorys in the system.
     *
     * @return the list of categorys
     * @see CategoryService#findAll(Pageable)
     */
    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ApiOperation(value = "search", notes = "Searches for a Category by their description and/or parent category id.")
    public ResponseEntity<Page<Category>> search(@RequestParam(value = "description", defaultValue = "", required = false) String description,
                                                 @RequestParam(value = "parentCategoryId", defaultValue = "", required = false) String parentCategoryId,
                                                 @RequestParam(value = "page", defaultValue = "0", required = false) String page,
                                                 @RequestParam(value = "size", defaultValue = "10", required = false) String size) {
        int pg = Integer.parseInt(page);
        int sz = Integer.parseInt(size);
        Pageable pageable = new PageRequest(pg, sz);
        Page<Category> result = new PageImpl<>(new ArrayList<>());
        if (parentCategoryId.isEmpty() && description.isEmpty()) {
            result = service.findAll(pageable);
        }
        if (!parentCategoryId.isEmpty()) {
            result = service.findByDescriptionLike(description, pageable);
        }
        if (!description.isEmpty()) {
            result = service.findByParentCategoryId(parentCategoryId, pageable);
        }

        return new ResponseEntity<>(result, HttpStatus.FOUND);
    }


    /**
     * Updates an existing category.
     *
     * @param id       the id of the category been category
     * @param category an category instance to persist
     * @return the category category or an HttpStatus.NOT_FOUND if the category been
     * category does not exist
     * @see CategoryService#updateById(String, Category)
     */
    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    @ApiOperation(value = "Update an existing category", notes = "Returns the created category.")
    public ResponseEntity<Category> updateById(@PathVariable("id") String id, @RequestBody Category category) {
        if (!service.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        category = service.updateById(id, category);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    /**
     * Deletes a category from the system.
     *
     * @param id the id of the category been deleted
     * @return if category was not found an HttpStatus.NOT_FOUND is returned else an
     * HttpStatus.NO_CONTENT is returned
     * @see CategoryService#deleteById(String)
     */
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.DELETE)
    @ApiOperation(value = "Delete a category")
    public ResponseEntity<Category> deleteById(@PathVariable("id") String id) {
        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}