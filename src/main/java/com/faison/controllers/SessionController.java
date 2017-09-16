package com.faison.controllers;

import com.faison.models.Session;
import com.faison.models.User;
import com.faison.services.SessionService;
import com.faison.services.UserService;
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

import javax.ejb.EJB;

@RestController
@RequestMapping("/api/sessions")
@Api(value = "Session Rest Controller", consumes = "Session")
public class SessionController {

    @EJB
    private SessionService service;
    @EJB
    private UserService userService;

    public SessionController(SessionService service) {
        this.service = service;
    }

    /**
     * Create a new session in the system.
     *
     * @param password the password of the user who is attempting to login
     * @param email    the email of the user who is attempting to login
     * @return an HttpStatus.CREATED if session was successfully created
     * @see SessionService#create(Session)
     */
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(value = "create", notes = "Creates and saves a new Session.")
    public ResponseEntity<Session> create(@RequestParam(value = "email", defaultValue = "") String email,
                                          @RequestParam(value = "password", defaultValue = "") String password) {
        User user = userService.findbyEmailAndPassword(email, password);
        Session session = new Session();
        session.setUserId(user.getId());
        final Session result = service.create(session);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/").buildAndExpand("").toUri());
        return new ResponseEntity<>(result, headers, HttpStatus.CREATED);
    }

    /**
     * Search for sessions in the system.
     *
     * @return the list of sessions
     * @see SessionService#findById(String)
     */
    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ApiOperation(value = "search", notes = "Searches for a Session by their userId, accessToken or session id.")
    public ResponseEntity<Session> search(@RequestParam(value = "id", defaultValue = "") String id,
                                          @RequestParam(value = "userId", defaultValue = "", required = false) String userId,
                                          @RequestParam(value = "accessToken", defaultValue = "", required = false) String accessToken,
                                          @RequestParam(value = "page", defaultValue = "0", required = false) String page,
                                          @RequestParam(value = "size", defaultValue = "10", required = false) String size) {
        int pg = Integer.parseInt(page);
        int sz = Integer.parseInt(size);
        Pageable pageable = new PageRequest(pg, sz);

        if (id.isEmpty()) {
            Session result = service.findById(id);
            return new ResponseEntity<>(result, HttpStatus.FOUND);
        } else if (!userId.isEmpty()) {
            Session result = service.findByUserId(userId);
            return new ResponseEntity<>(result, HttpStatus.FOUND);
        } else if (!accessToken.isEmpty()) {
            Session result = service.findByAccessToken(accessToken);
            return new ResponseEntity<>(result, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Finds all sessions in the system.
     *
     * @return the list of sessions
     * @see SessionService#findAll(Pageable)
     */
    @ResponseBody
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ApiOperation(value = "search", notes = "Searches for a Session by their userId, accessToken or session id.")
    public ResponseEntity<Page<Session>> getAll(@RequestParam(value = "page", defaultValue = "0", required = false) String page,
                                                @RequestParam(value = "size", defaultValue = "10", required = false) String size) {
        int pg = Integer.parseInt(page);
        int sz = Integer.parseInt(size);
        Pageable pageable = new PageRequest(pg, sz);

        Page<Session> result = service.findAll(pageable);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    /**
     * Updates an existing session.
     *
     * @param id      the id of the session been session
     * @param session an session instance to persist
     * @return the session session or an HttpStatus.NOT_FOUND if the session been
     * session does not exist
     * @see SessionService#updateById(String, Session)
     */
    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    @ApiOperation(value = "Update an existing session", notes = "Returns the created session.")
    public ResponseEntity<Session> updateById(@PathVariable("id") String id, @RequestBody Session session) {
        if (!service.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        session = service.updateById(id, session);
        return new ResponseEntity<>(session, HttpStatus.OK);
    }

    /**
     * Deletes a session from the system.
     *
     * @param id the id of the session been deleted
     * @return if session was not found an HttpStatus.NOT_FOUND is returned else an
     * HttpStatus.NO_CONTENT is returned
     * @see SessionService#deleteById(String)
     */
    @RequestMapping(value = "/{id}/logout", method = RequestMethod.DELETE)
    @ApiOperation(value = "Delete a session")
    public ResponseEntity<Session> deleteById(@PathVariable("id") String id) {
        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
