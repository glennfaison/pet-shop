package com.faison.controllers;

import com.faison.models.Order;
import com.faison.services.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * RestController that handles request for {@link Order} objects
 *
 * @author faison
 * @see Order
 * @see OrderService
 */
@RestController
@RequestMapping("/api/orders")
@Api(value = "Order Rest Controller")
public class OrderController {

    @Autowired
    private OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    /**
     * Create a new order in the system.
     *
     * @param order the order to be created
     * @return an HttpStatus.CREATED if order was successfully created
     * @see OrderService#create(Order)
     */
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ApiOperation(value = "create", notes = "Creates and saves a new Order.")
    public ResponseEntity<Order> create(@RequestBody Order order) {
        final Order result = service.create(order);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/").buildAndExpand("").toUri());
        return new ResponseEntity<>(result, headers, HttpStatus.CREATED);
    }

    /**
     * Get order with given order id.
     *
     * @param id the id of the order to return
     * @return the order of given id
     * @see OrderService#findById(String)
     */
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "getById", notes = "Gets a Order by their id.")
    public ResponseEntity<Order> getById(@PathVariable(value = "id") String id) {
        final Order result = service.findById(id);
        return new ResponseEntity<>(result, HttpStatus.FOUND);
    }

    /**
     * Search for orders in the system.
     *
     * @return the list of orders
     * @see OrderService#findAll(Pageable)
     */
    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ApiOperation(value = "search", notes = "Searches for a Order by their buyerId and/or placedAfter.")
    public ResponseEntity<Page<Order>> search(@RequestParam(value = "buyerId", defaultValue = "", required = false) String buyerId,
                                              @RequestParam(value = "placedAfter", defaultValue = "", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date placedAfter,
                                              @RequestParam(value = "placedBefore", defaultValue = "", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date placedBefore,
                                              @RequestParam(value = "page", defaultValue = "0", required = false) String page,
                                              @RequestParam(value = "size", defaultValue = "10", required = false) String size) {
        int pg = Integer.parseInt(page);
        int sz = Integer.parseInt(size);
        Pageable pageable = new PageRequest(pg, sz);
        List<Order> temp = new ArrayList<>();

        if (buyerId.isEmpty() && placedAfter == null && placedBefore == null) {
            temp.addAll(service.findAll(pageable).getContent());
        }

        if (!buyerId.isEmpty()) {
            temp.addAll(service.findByBuyer_Id(buyerId, pageable).getContent());
        }

        if (placedAfter != null && placedBefore != null) {
            Date from = placedAfter;
            Date to = placedBefore;
            temp.addAll(service.findByPlacedOnBetween(from, to, pageable).getContent());
        } else if (placedAfter != null) {
            temp.addAll(service.findByPlacedOnAfter(placedAfter, pageable).getContent());
        } else if (placedBefore != null) {
            temp.addAll(service.findByPlacedOnBefore(placedBefore, pageable).getContent());
        }

        Page<Order> result = new PageImpl<>(temp);
        return new ResponseEntity<>(result, HttpStatus.FOUND);
    }


    /**
     * Updates an existing order.
     *
     * @param id    the id of the order been order
     * @param order an order instance to persist
     * @return the order order or an HttpStatus.NOT_FOUND if the order been
     * order does not exist
     * @see OrderService#updateById(String, Order)
     */
    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    @ApiOperation(value = "Update an existing order", notes = "Returns the created order.")
    public ResponseEntity<Order> updateById(@PathVariable("id") String id, @RequestBody Order order) {
        if (!service.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        order = service.updateById(id, order);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    /**
     * Deletes a order from the system.
     *
     * @param id the id of the order been deleted
     * @return if order was not found an HttpStatus.NOT_FOUND is returned else an
     * HttpStatus.NO_CONTENT is returned
     * @see OrderService#deleteById(String)
     */
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.DELETE)
    @ApiOperation(value = "Delete a order")
    public ResponseEntity<Order> deleteById(@PathVariable("id") String id) {
        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
