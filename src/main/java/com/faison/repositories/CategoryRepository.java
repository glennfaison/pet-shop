package com.faison.repositories;

import com.faison.models.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category, String> {

    Page<Category> findByNameLike(@Param("name") String name, Pageable pageable);

    Page<Category> findByDescriptionLike(@Param("description") String description, Pageable pageable);

    Page<Category> findByParentCategoryId(@Param("parentCategoryId") String parentCategoryId, Pageable pageable);
}
