package com.github.geekuniversity_java_215.cmsbackend.core.repositories;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.Account;
import com.github.geekuniversity_java_215.cmsbackend.utils.repositories.CustomRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.util.Optional;


public interface AccountRepository extends CustomRepository<Account, Long> {

    // SELECT FOR UPDATE
    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("FROM Account a " +
           "WHERE a = :#{#account}")
    Optional<Account> lockByAccount(@Param("account")Account account);
}
