package com.faison.repositories;

import com.faison.models.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends PagingAndSortingRepository<Address, String> {
    Page<Address> findByStreetLike(String street, Pageable pageable);

    Page<Address> findByCityLike(String city, Pageable pageable);

    Page<Address> findByStateLike(String state, Pageable pageable);

    Page<Address> findByCountryLike(String country, Pageable pageable);
}
