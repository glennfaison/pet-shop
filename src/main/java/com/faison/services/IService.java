package com.faison.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IService<Model> {
    /**
     * Check for the existence of a {@link Model} with given id.
     *
     * @param id The id to search for.
     * @return true if such a record exists, and false otherwise.
     */
    boolean exists(String id);

    /**
     * Create and save a new {@link Model}.
     *
     * @param record The record to be persisted
     * @return the saved record
     */
    Model create(Model record);

    /**
     * Find the {@link Model} with the given id.
     *
     * @param id The id to be searched for.
     * @return {@link Model}
     * the record if found, and null otherwise.
     */
    Model findById(String id);

    /**
     * Return all available {@link Model} in pages.
     *
     * @param pageable The page number and size to be returned.
     * @return the given page of records.
     */
    Page<Model> findAll(Pageable pageable);

    /**
     * Update an existing {@link Model} with the specified id.
     *
     * @param id     The id of the record to update.
     * @param record The record to be updated.
     * @return {@link Model}
     * the saved record, if update was successful, or null if the update was unsuccessful.
     */
    Model updateById(String id, Model record);

    /**
     * Delete the {@link Model} with the specified id.
     *
     * @param id The id of the record to be deleted.
     */
    void deleteById(String id);
}
