package com.faison.repositories;

import com.faison.models.Session;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends PagingAndSortingRepository<Session, String> {
    Session findByAccessToken(String accessToken);

    Session findByUserId(String userId);

    void deleteByUserId(String userId);

    void deleteByAccessToken(String accessToken);
}
