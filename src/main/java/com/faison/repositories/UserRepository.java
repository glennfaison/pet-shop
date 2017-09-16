package com.faison.repositories;

import com.faison.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, String> {
    Page<User> findByEmailLike(@Param("email") String email, Pageable pageable);

    Page<User> findByFirstNameLike(@Param("firstName") String firstName, Pageable pageable);

    Page<User> findByLastNameLike(@Param("lastName") String lastName, Pageable pageable);

    Page<User> findByFirstNameLikeOrLastNameLike(String name, Pageable pageable);

    Page<User> findByFirstNameLikeAndLastNameLikeAndEmailLike(String name, Pageable pageable);

    Page<User> findByIsAdminIsTrue(Pageable pageable);

    User findByEmail(@Param("email") String email);
}
