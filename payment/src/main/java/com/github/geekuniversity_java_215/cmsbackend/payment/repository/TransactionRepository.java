package com.github.geekuniversity_java_215.cmsbackend.payment.repository;

import com.github.geekuniversity_java_215.cmsbackend.core.repositories.CustomRepository;
import com.github.geekuniversity_java_215.cmsbackend.payment.entities.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends CustomRepository<Transaction, Long> {

}
