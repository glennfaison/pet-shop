package com.faison.services;

import com.faison.models.Category;
import com.faison.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Component
@Local
public class CategoryService implements IService<Category> {

    @Autowired
    private CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    /**
     * Check for the existence of a {@link Category} with given id.
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
     * Create and save a new {@link Category}.
     *
     * @param record The record to be persisted
     * @return the saved record
     */
    public Category create(Category record) {
        if (exists(record.getId())) {
            return null;
        }
        record.setId(null);
        return repository.save(record);
    }

    /**
     * Find the {@link Category} with the given id.
     *
     * @param id The id to be searched for.
     * @return {@link Category}
     * the record if found, and null otherwise.
     */
    public Category findById(String id) {
        return repository.findOne(id);
    }

    /**
     * Return all available {@link Category} in pages.
     *
     * @param pageable The page number and size to be returned.
     * @return the given page of records.
     */
    public Page<Category> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    /**
     * Return all {@link Category} objects in pages, whose parents have the given id.
     *
     * @param pageable The page number and size to be returned.
     * @return the given page of records.
     */
    public Page<Category> findByParentCategoryId(String parentCategoryId, Pageable pageable) {
        return repository.findByParentCategoryId(parentCategoryId, pageable);
    }

    /**
     * Return all {@link Category} objects in pages, whose descriptions are similar to the given one.
     *
     * @param pageable The page number and size to be returned.
     * @return the given page of records.
     */
    public Page<Category> findByDescriptionLike(String description, Pageable pageable) {
        return repository.findByDescriptionLike(description, pageable);
    }

    /**
     * Update an existing {@link Category} with the specified id.
     *
     * @param id     The id of the record to update.
     * @param record The record to be updated.
     * @return {@link Category}
     * the saved record, if update was successful, or null if the update was unsuccessful.
     */
    public Category updateById(String id, Category record) {
        if (!exists(id)) {
            return null;
        }
        record.setId(id);
        return repository.save(record);
    }

    /**
     * Delete the {@link Category} with the specified id.
     *
     * @param id The id of the record to be deleted.
     */
    public void deleteById(String id) {
        repository.delete(id);
    }
}