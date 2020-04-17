package com.github.geekuniversity_java_215.cmsbackend.repository;

import com.github.geekuniversity_java_215.cmsbackend.entites.Account;
import com.github.geekuniversity_java_215.cmsbackend.repository.base.CustomRepository;
import com.github.geekuniversity_java_215.cmsbackend.repository.base.CustomRepositoryImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AccountRepository extends CustomRepository<Account, Long>, JpaSpecificationExecutor<Account> {
}
