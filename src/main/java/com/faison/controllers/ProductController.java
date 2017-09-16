package com.faison.controllers;

import com.faison.models.Product;
import com.faison.services.ProductService;
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

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/products")
@Api(value = "Product Rest Controller")
public class ProductController {

    @Autowired
    private ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    /**
     * Create a new product in the system.
     *
     * @param product the product to be created
     * @return an HttpStatus.CREATED if product was successfully created
     * @see ProductService#create(Product)
     */
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ApiOperation(value = "create", notes = "Creates and saves a new Product.")
    public ResponseEntity<Product> create(@RequestBody Product product) {
        final Product result = service.create(product);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/").buildAndExpand("").toUri());
        return new ResponseEntity<>(result, headers, HttpStatus.CREATED);
    }

    /**
     * Get product with given product id.
     *
     * @param id the id of the product to return
     * @return the product of given id
     * @see ProductService#findById(String)
     */
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "getById", notes = "Gets a Product by their id.")
    public ResponseEntity<Product> getById(@PathVariable(value = "id") String id) {
        final Product result = service.findById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Search for products in the system.
     *
     * @return the list of products
     * @see ProductService#findAll(Pageable)
     */
    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ApiOperation(value = "search", notes = "Searches for a Product by their name and/or description.")
    public ResponseEntity<Page<Product>> search(@RequestParam(value = "name", defaultValue = "", required = false) String name,
                                                @RequestParam(value = "description", defaultValue = "", required = false) String description,
                                                @RequestParam(value = "categoryId", defaultValue = "", required = false) String categoryId,
                                                @RequestParam(value = "unitPriceEquals", defaultValue = "", required = false) String unitPriceEquals,
                                                @RequestParam(value = "unitPriceLessThan", defaultValue = "", required = false) String unitPriceLessThan,
                                                @RequestParam(value = "unitPriceGreaterThan", defaultValue = "", required = false) String unitPriceGreaterThan,
                                                @RequestParam(value = "page", defaultValue = "0", required = false) String page,
                                                @RequestParam(value = "size", defaultValue = "10", required = false) String size) {
        int pg = Integer.parseInt(page);
        int sz = Integer.parseInt(size);
        Pageable pageable = new PageRequest(pg, sz);
        List<Product> temp = new ArrayList<>();

        String all = name + description + categoryId + unitPriceEquals + unitPriceGreaterThan + unitPriceLessThan;
        if (all.isEmpty()) {
            temp.addAll(service.findAll(pageable).getContent());
        }

        if (!name.isEmpty()) {
            temp.addAll(service.findByNameLike(name, pageable).getContent());
        }
        if (!description.isEmpty()) {
            temp.addAll(service.findByDescriptionLike(description, pageable).getContent());
        }
        if (!categoryId.isEmpty()) {
            temp.addAll(service.findByCategoryId(categoryId, pageable).getContent());
        }
        if (!unitPriceEquals.isEmpty()) {
            double price = Double.parseDouble(unitPriceEquals);
            temp.addAll(service.findByUnitPrice(price, pageable).getContent());
        }
        if (!unitPriceGreaterThan.isEmpty() && !unitPriceLessThan.isEmpty()) {
            double from = Double.parseDouble(unitPriceGreaterThan);
            double to = Double.parseDouble(unitPriceLessThan);
            temp.addAll(service.findByUnitPriceBetween(from, to, pageable).getContent());
        } else if (!unitPriceLessThan.isEmpty()) {
            double price = Double.parseDouble(unitPriceLessThan);
            temp.addAll(service.findByUnitPriceLessThan(price, pageable).getContent());
        } else if (!unitPriceGreaterThan.isEmpty()) {
            double price = Double.parseDouble(unitPriceGreaterThan);
            temp.addAll(service.findByUnitPriceGreaterThan(price, pageable).getContent());
        }

        Page<Product> result = new PageImpl<>(temp);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    /**
     * Updates an existing product.
     *
     * @param id      the id of the product been product
     * @param product an product instance to persist
     * @return the product product or an HttpStatus.NOT_FOUND if the product been
     * product does not exist
     * @see ProductService#updateById(String, Product)
     */
    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    @ApiOperation(value = "Update an existing product", notes = "Returns the created product.")
    public ResponseEntity<Product> updateById(@PathVariable("id") String id, @RequestBody Product product) {
        if (!service.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        product = service.updateById(id, product);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    /**
     * Deletes a product from the system.
     *
     * @param id the id of the product been deleted
     * @return if product was not found an HttpStatus.NOT_FOUND is returned else an
     * HttpStatus.NO_CONTENT is returned
     * @see ProductService#deleteById(String)
     */
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.DELETE)
    @ApiOperation(value = "Delete a product")
    public ResponseEntity<Product> deleteById(@PathVariable("id") String id) {
        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
