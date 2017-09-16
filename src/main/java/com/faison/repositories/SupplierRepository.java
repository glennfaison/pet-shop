package com.faison.repositories;

import com.faison.models.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends PagingAndSortingRepository<Supplier, String> {
    Page<Supplier> findByNameLike(String name, Pageable pageable);

    Page<Supplier> findByEmailLike(String email, Pageable pageable);

    Page<Supplier> findByPhoneNumberLike(String phoneNumber, Pageable pageable);
}
