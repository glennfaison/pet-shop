package com.faison.services;

import com.faison.models.Session;
import com.faison.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.ejb.Local;
import javax.ejb.Stateless;

/**
 * Implementation of {@link SessionService}
 */
@Stateless
@Component
@Local
public class SessionService implements IService<Session> {

    @Autowired
    private SessionRepository repository;

    public SessionService() {
    }

    /**
     * Check for the existence of a {@link Session} with given id.
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
     * Create and save a new {@link Session}.
     *
     * @param record The record to be persisted
     * @return the saved record
     */
    public Session create(Session record) {
        if (exists(record.getId())) {
            return null;
        }
        record.setId(null);
        return repository.save(record);
    }

    /**
     * Find the {@link Session} with the given id.
     *
     * @param id The id to be searched for.
     * @return {@link Session}
     * the record if found, and null otherwise.
     */
    public Session findById(String id) {
        return repository.findOne(id);
    }

    /**
     * Return all available {@link Session} in pages.
     *
     * @param pageable The page number and size to be returned.
     * @return the given page of records.
     */
    public Page<Session> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Session findByAccessToken(String accessToken) {
        return repository.findByAccessToken(accessToken);
    }

    public Session findByUserId(String userId) {
        return repository.findByUserId(userId);
    }

    /**
     * Update an existing {@link Session} with the specified id.
     *
     * @param id     The id of the record to update.
     * @param record The record to be updated.
     * @return {@link Session}
     * the saved record, if update was successful, or null if the update was unsuccessful.
     */
    public Session updateById(String id, Session record) {
        if (!exists(id)) {
            return null;
        }
        record.setId(id);
        return repository.save(record);
    }

    public void deleteByUserId(String userId) {
        repository.deleteByUserId(userId);
    }

    public void deleteByAccessToken(String accessToken) {
        repository.deleteByAccessToken(accessToken);
    }

    /**
     * Delete the {@link Session} with the specified id.
     *
     * @param id The id of the record to be deleted.
     */
    public void deleteById(String id) {
        repository.delete(id);
    }
}
