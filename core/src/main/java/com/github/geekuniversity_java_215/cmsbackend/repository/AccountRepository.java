package com.github.geekuniversity_java_215.cmsbackend.repository;

import com.github.geekuniversity_java_215.cmsbackend.entites.Account;
import com.github.geekuniversity_java_215.cmsbackend.repository.base.CustomRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.util.Optional;


public interface AccountRepository extends CustomRepository<Account, Long> {

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("FROM Account a " +
           "WHERE a = :#{#account}")
    Optional<Account> lockByAccount(@Param("account")Account account);
}
