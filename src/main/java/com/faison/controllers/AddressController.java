package com.faison.controllers;

import com.faison.models.Address;
import com.faison.services.AddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import java.util.List;

@RestController
@RequestMapping(value = "/api/locations")
@Api(value = "Address Rest Controller")
public class AddressController {

    @EJB
    private AddressService service;

    public AddressController(AddressService service) {
        this.service = service;
    }

    /**
     * Create a new address in the system.
     *
     * @param address the address to be created
     * @return an HttpStatus.CREATED if address was successfully created
     * @see AddressService#create(Address)
     */
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ApiOperation(value = "create", notes = "Creates and saves a new Address.")
    public ResponseEntity<Address> create(@RequestBody Address address) {
        final Address result = service.create(address);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/").buildAndExpand("").toUri());
        return new ResponseEntity<>(result, headers, HttpStatus.CREATED);
    }

    /**
     * Get address with given address id.
     *
     * @param id the id of the address to return
     * @return the address of given id
     * @see AddressService#findById(String)
     */
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "getById", notes = "Gets a Address by their id.")
    public ResponseEntity<Address> getById(@PathVariable(value = "id") String id) {
        final Address result = service.findById(id);
        return new ResponseEntity<>(result, HttpStatus.FOUND);
    }

    /**
     * Search for addresss in the system.
     *
     * @return the list of addresss
     * @see AddressService#findAll(Pageable)
     */
    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ApiOperation(value = "search", notes = "Searches for a Address by their country and/or state.")
    public ResponseEntity<Page<Address>> search(@RequestParam(value = "country", defaultValue = "", required = false) String country,
                                                @RequestParam(value = "state", defaultValue = "", required = false) String state,
                                                @RequestParam(value = "city", defaultValue = "", required = false) String city,
                                                @RequestParam(value = "street", defaultValue = "", required = false) String street,
                                                @RequestParam(value = "page", defaultValue = "0", required = false) String page,
                                                @RequestParam(value = "size", defaultValue = "10", required = false) String size) {
        int pg = Integer.parseInt(page);
        int sz = Integer.parseInt(size);
        Pageable pageable = new PageRequest(pg, sz);
        List<Address> temp = new ArrayList<>();

        String all = country + state + city + street;
        if (all.isEmpty()) {
            temp.addAll(service.findAll(pageable).getContent());
        }

        if (!country.isEmpty()) {
            temp.addAll(service.findByCityLike(country, pageable).getContent());
        }
        if (!state.isEmpty()) {
            temp.addAll(service.findByStateLike(state, pageable).getContent());
        }
        if (!city.isEmpty()) {
            temp.addAll(service.findByCityLike(city, pageable).getContent());
        }
        if (!street.isEmpty()) {
            temp.addAll(service.findByStreetLike(street, pageable).getContent());
        }

        Page<Address> result = new PageImpl<>(temp);
        return new ResponseEntity<>(result, HttpStatus.FOUND);
    }


    /**
     * Updates an existing address.
     *
     * @param id      the id of the address been address
     * @param address an address instance to persist
     * @return the address address or an HttpStatus.NOT_FOUND if the address been
     * address does not exist
     * @see AddressService#updateById(String, Address)
     */
    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    @ApiOperation(value = "Update an existing address", notes = "Returns the created address.")
    public ResponseEntity<Address> updateById(@PathVariable("id") String id, @RequestBody Address address) {
        if (!service.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        address = service.updateById(id, address);
        return new ResponseEntity<>(address, HttpStatus.OK);
    }

    /**
     * Deletes a address from the system.
     *
     * @param id the id of the address been deleted
     * @return if address was not found an HttpStatus.NOT_FOUND is returned else an
     * HttpStatus.NO_CONTENT is returned
     * @see AddressService#deleteById(String)
     */
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.DELETE)
    @ApiOperation(value = "Delete a address")
    public ResponseEntity<Address> deleteById(@PathVariable("id") String id) {
        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
