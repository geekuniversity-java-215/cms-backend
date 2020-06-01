package com.github.geekuniversity_java_215.cmsbackend.payment.repository;

import com.github.geekuniversity_java_215.cmsbackend.core.repositories.CustomRepository;
import com.github.geekuniversity_java_215.cmsbackend.payment.entities.CashFlow;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface CashFlowRepository extends CustomRepository<CashFlow, Long> {

    @Query("FROM CashFlow WHERE dateSuccess is null")
    List<CashFlow> findAllWithEmptyDateSuccess();

    @Query("SELECT dateSuccess FROM CashFlow")
    List<Date> findAllDateSuccess();

}
