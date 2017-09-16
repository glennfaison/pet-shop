package com.faison.controllers;

import com.faison.models.OrderItem;
import com.faison.services.OrderItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/api/orderItems")
@Api(value = "OrderItem Rest Controller")
public class OrderItemController {

    @Autowired
    private OrderItemService service;

    /**
     * Create a new orderItem in the system.
     *
     * @param orderItem the orderItem to be created
     * @return an HttpStatus.CREATED if orderItem was successfully created
     * @see OrderItemService#create(OrderItem)
     */
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ApiOperation(value = "create", notes = "Creates and saves a new OrderItem.")
    public ResponseEntity<OrderItem> create(@RequestBody OrderItem orderItem) {
        final OrderItem result = service.create(orderItem);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/").buildAndExpand("").toUri());
        return new ResponseEntity<>(result, headers, HttpStatus.CREATED);
    }

    /**
     * Get orderItem with given orderItem id.
     *
     * @param id the id of the orderItem to return
     * @return the orderItem of given id
     * @see OrderItemService#findById(String)
     */
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "getById", notes = "Gets an OrderItem by their id.")
    public ResponseEntity<OrderItem> getById(@PathVariable(value = "id") String id) {
        final OrderItem result = service.findById(id);
        return new ResponseEntity<>(result, HttpStatus.FOUND);
    }

    /**
     * Search for orderItems in the system.
     *
     * @return the list of orderItems
     * @see OrderItemService#findAll(Pageable)
     */
    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ApiOperation(value = "search", notes = "Finds all OrderItems.")
    public ResponseEntity<Page<OrderItem>> search(@RequestParam(value = "page", defaultValue = "0", required = false) String page,
                                                  @RequestParam(value = "size", defaultValue = "10", required = false) String size) {
        int pg = Integer.parseInt(page);
        int sz = Integer.parseInt(size);
        Pageable pageable = new PageRequest(pg, sz);
        Page<OrderItem> result = service.findAll(pageable);
        return new ResponseEntity<>(result, HttpStatus.FOUND);
    }


    /**
     * Updates an existing orderItem.
     *
     * @param id    the id of the orderItem been orderItem
     * @param orderItem an orderItem instance to persist
     * @return the orderItem orderItem or an HttpStatus.NOT_FOUND if the orderItem been
     * orderItem does not exist
     * @see OrderItemService#updateById(String, OrderItem)
     */
    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    @ApiOperation(value = "Update an existing orderItem", notes = "Returns the created orderItem.")
    public ResponseEntity<OrderItem> updateById(@PathVariable("id") String id, @RequestBody OrderItem orderItem) {
        if (!service.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        orderItem = service.updateById(id, orderItem);
        return new ResponseEntity<>(orderItem, HttpStatus.OK);
    }

    /**
     * Deletes a orderItem from the system.
     *
     * @param id the id of the orderItem been deleted
     * @return if orderItem was not found an HttpStatus.NOT_FOUND is returned else an
     * HttpStatus.NO_CONTENT is returned
     * @see OrderItemService#deleteById(String)
     */
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.DELETE)
    @ApiOperation(value = "Delete a orderItem")
    public ResponseEntity<OrderItem> deleteById(@PathVariable("id") String id) {
        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
