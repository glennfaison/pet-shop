package com.faison.controllers;

import com.faison.models.User;
import com.faison.services.UserService;
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

/**
 * RestController that handles request for {@link User} objects
 *
 * @author faison
 * @see User
 * @see UserService
 */
@RestController
@RequestMapping(value = "/api/users")
@Api(value = "User Rest Controller")
public class UserController {

    @EJB
    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    /**
     * Create a new user in the system.
     *
     * @param user the user to be created
     * @return an HttpStatus.CREATED if user was successfully created
     * @see UserService#create(User)
     */
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ApiOperation(value = "create", notes = "Creates and saves a new User.")
    public ResponseEntity<User> create(@RequestBody User user) {
        final User result = service.create(user);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/").buildAndExpand("").toUri());
        return new ResponseEntity<>(result, headers, HttpStatus.CREATED);
    }

    /**
     * Get user with given user id.
     *
     * @param id the id of the user to return
     * @return the user of given id
     * @see UserService#findById(String)
     */
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "getById", notes = "Gets a User by their id.")
    public ResponseEntity<User> getById(@PathVariable(value = "id") String id) {
        final User result = service.findById(id);
        return new ResponseEntity<>(result, HttpStatus.FOUND);
    }

    /**
     * Search for users in the system.
     *
     * @return the list of users
     * @see UserService#findAll(Pageable)
     */
    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ApiOperation(value = "search", notes = "Searches for a User by their name and/or email.")
    public ResponseEntity<Page<User>> search(@RequestParam(value = "email", defaultValue = "", required = false) String email,
                                             @RequestParam(value = "name", defaultValue = "", required = false) String name,
                                             @RequestParam(value = "page", defaultValue = "0", required = false) String page,
                                             @RequestParam(value = "size", defaultValue = "10", required = false) String size) {
        int pg = Integer.parseInt(page);
        int sz = Integer.parseInt(size);
        Pageable pageable = new PageRequest(pg, sz);
        Page<User> result = new PageImpl<>(new ArrayList<>());
        if (name.isEmpty() && email.isEmpty()) {
            result = service.findAll(pageable);
        } else if (!name.isEmpty() && !email.isEmpty()) {
            result = service.findAll(pageable);
        } else if (!name.isEmpty() && email.isEmpty()) {
            result = service.findByNameLike(name, pageable);
        } else if (name.isEmpty() && !email.isEmpty()) {
            result = service.findByEmailLike(email, pageable);
        }

        return new ResponseEntity<>(result, HttpStatus.FOUND);
    }


    /**
     * Updates an existing user.
     *
     * @param id   the id of the user been user
     * @param user an user instance to persist
     * @return the user user or an HttpStatus.NOT_FOUND if the user been
     * user does not exist
     * @see UserService#updateById(String, User)
     */
    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    @ApiOperation(value = "Update an existing user", notes = "Returns the created user.")
    public ResponseEntity<User> updateById(@PathVariable("id") String id, @RequestBody User user) {
        if (!service.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        user = service.updateById(id, user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * Deletes a user from the system.
     *
     * @param id the id of the user been deleted
     * @return if user was not found an HttpStatus.NOT_FOUND is returned else an
     * HttpStatus.NO_CONTENT is returned
     * @see UserService#deleteById(String)
     */
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.DELETE)
    @ApiOperation(value = "Delete a user")
    public ResponseEntity<User> deleteById(@PathVariable("id") String id) {
        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
