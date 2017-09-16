package com.faison.services;

import com.faison.models.User;
import com.faison.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.ejb.Local;
import javax.ejb.Stateless;

/**
 * Implementation of {@link UserService}
 */
@Stateless
@Component
@Local
public class UserService implements IService<User> {

    @Autowired
    private UserRepository repository;

    public UserService() {
    }

    /**
     * Check for the existence of a record with given id.
     *
     * @param id The id to search for.
     * @return true if such a record exists, and false otherwise.
     */
    public boolean exists(String id) {
        if (id == null) {
            return false;
        }
        return repository.exists(id);
    }

    /**
     * Create and save a new record.
     *
     * @param record The record to be persisted
     * @return the saved record
     */
    public User create(User record) {
        if (exists(record.getId())) {
            return null;
        }
        record.setId(null);
        return repository.save(record);
    }

    /**
     * Creates a new Administrator in the system.
     *
     * @param user the user to create
     * @return the user created
     */
    public User createAdmin(User user) {
        user.setAdmin(true);
        return create(user);
    }

    /**
     * Find the record with the given id.
     *
     * @param id The id to be searched for.
     * @return the record if found, and null otherwise.
     */
    public User findById(String id) {
        return repository.findOne(id);
    }

    public User findbyEmailAndPassword(String email, String password) {
        User user = repository.findByEmail(email);
        if (user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    /**
     * Return all available records in pages.
     *
     * @param pageable The page number and size to be returned.
     * @return the given page of records.
     */
    public Page<User> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<User> findByNameLike(String name, Pageable pageable) {
        return repository.findByFirstNameLikeOrLastNameLike(name, pageable);
    }

    public Page<User> findByFirstName(String firstName, Pageable pageable) {
        return repository.findByFirstNameLike(firstName, pageable);
    }

    public Page<User> findByLastName(String lastName, Pageable pageable) {
        return repository.findByLastNameLike(lastName, pageable);
    }

    public Page<User> findByIsAdminIsTrue(Pageable pageable) {
        return repository.findByIsAdminIsTrue(pageable);
    }

    /**
     * Find the users with emails similar to the given email.
     *
     * @param email The email to search for.
     * @return the users with similar emails.
     */
    public Page<User> findByEmailLike(String email, Pageable pageable) {
        return repository.findByEmailLike(email, pageable);
    }

    /**
     * Update an existing record with the specified id.
     *
     * @param id     The id of the record to update.
     * @param record The record to be updated.
     * @return the saved record, if update was successful, or null if the update was unsuccessful.
     */
    public User updateById(String id, User record) {
        if (!exists(id)) {
            return null;
        }
        record.setId(id);
        return repository.save(record);
    }

    /**
     * Delete the {@link User} with the specified id.
     *
     * @param id The id of the record to be deleted.
     */
    public void deleteById(String id) {
        repository.delete(id);
    }
}


