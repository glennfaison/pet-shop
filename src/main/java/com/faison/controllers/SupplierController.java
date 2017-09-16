package com.faison.controllers;

import com.faison.models.Supplier;
import com.faison.services.SupplierService;
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
@RequestMapping(value = "/api/farmers")
@Api(value = "Supplier Rest Controller")
public class SupplierController {

    @Autowired
    private SupplierService service;

    public SupplierController(SupplierService service) {
        this.service = service;
    }


    /**
     * Create a new supplier in the system.
     *
     * @param supplier the supplier to be created
     * @return an HttpStatus.CREATED if supplier was successfully created
     * @see SupplierService#create(Supplier)
     */
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ApiOperation(value = "create", notes = "Creates and saves a new Supplier.")
    public ResponseEntity<Supplier> create(@RequestBody Supplier supplier) {
        final Supplier result = service.create(supplier);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/").buildAndExpand("").toUri());
        return new ResponseEntity<>(result, headers, HttpStatus.CREATED);
    }

    /**
     * Get supplier with given supplier id.
     *
     * @param id the id of the supplier to return
     * @return the supplier of given id
     * @see SupplierService#findById(String)
     */
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "getById", notes = "Gets a Supplier by their id.")
    public ResponseEntity<Supplier> getById(@PathVariable(value = "id") String id) {
        final Supplier result = service.findById(id);
        return new ResponseEntity<>(result, HttpStatus.FOUND);
    }

    /**
     * Search for suppliers in the system.
     *
     * @return the list of suppliers
     * @see SupplierService#findAll(Pageable)
     */
    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ApiOperation(value = "search", notes = "Searches for a Supplier by their name, email or phone number.")
    public ResponseEntity<Page<Supplier>> search(@RequestParam(value = "name", defaultValue = "", required = false) String name,
                                                 @RequestParam(value = "email", defaultValue = "", required = false) String email,
                                                 @RequestParam(value = "phoneNumber", defaultValue = "", required = false) String phoneNumber,
                                                 @RequestParam(value = "page", defaultValue = "0", required = false) String page,
                                                 @RequestParam(value = "size", defaultValue = "10", required = false) String size) {
        int pg = Integer.parseInt(page);
        int sz = Integer.parseInt(size);
        Pageable pageable = new PageRequest(pg, sz);
        List<Supplier> temp = new ArrayList<>();

        String all = name + email + phoneNumber;
        if (all.isEmpty()) {
            temp.addAll(service.findAll(pageable).getContent());
        }

        if (!name.isEmpty()) {
            temp.addAll(service.findByNameLike(name, pageable).getContent());
        }
        if (!email.isEmpty()) {
            temp.addAll(service.findByEmailLike(email, pageable).getContent());
        }
        if (!phoneNumber.isEmpty()) {
            temp.addAll(service.findByPhoneNumberLike(phoneNumber, pageable).getContent());
        }

        Page<Supplier> result = new PageImpl<>(temp);
        return new ResponseEntity<>(result, HttpStatus.FOUND);
    }


    /**
     * Updates an existing supplier.
     *
     * @param id       the id of the supplier been supplier
     * @param supplier an supplier instance to persist
     * @return the supplier supplier or an HttpStatus.NOT_FOUND if the supplier been
     * supplier does not exist
     * @see SupplierService#updateById(String, Supplier)
     */
    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    @ApiOperation(value = "Update an existing supplier", notes = "Returns the created supplier.")
    public ResponseEntity<Supplier> updateById(@PathVariable("id") String id, @RequestBody Supplier supplier) {
        if (!service.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        supplier = service.updateById(id, supplier);
        return new ResponseEntity<>(supplier, HttpStatus.OK);
    }

    /**
     * Deletes a supplier from the system.
     *
     * @param id the id of the supplier been deleted
     * @return if supplier was not found an HttpStatus.NOT_FOUND is returned else an
     * HttpStatus.NO_CONTENT is returned
     * @see SupplierService#deleteById(String)
     */
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.DELETE)
    @ApiOperation(value = "Delete a supplier")
    public ResponseEntity<Supplier> deleteById(@PathVariable("id") String id) {
        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
