package com.faison.repositories;

import com.faison.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, String> {
    Page<Product> findByNameLike(String name, Pageable pageable);

    Page<Product> findByCategoryId(String categoryId, Pageable pageable);

    Page<Product> findByDescriptionLike(String description, Pageable pageable);

    Page<Product> findByUnitPrice(Double unitPrice, Pageable pageable);

    Page<Product> findByUnitPriceLessThan(Double unitPrice, Pageable pageable);

    Page<Product> findByUnitPriceGreaterThan(Double unitPrice, Pageable pageable);

    Page<Product> findByUnitPriceBetween(Double from, Double to, Pageable pageable);
}
