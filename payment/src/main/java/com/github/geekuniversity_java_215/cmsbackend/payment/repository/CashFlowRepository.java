package com.github.geekuniversity_java_215.cmsbackend.payment.repository;

import com.github.geekuniversity_java_215.cmsbackend.core.repositories.CustomRepository;
import com.github.geekuniversity_java_215.cmsbackend.payment.entities.CashFlow;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface CashFlowRepository extends CustomRepository<CashFlow, Long> {

    @Query("FROM CashFlow WHERE dateSuccess='4000.01.01'")
    List<CashFlow> findAllWithEmptyDateSuccess();

    @Query("SELECT dateSuccess FROM CashFlow")
    List<Date> findAllDateSuccess();

//    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END " +
//            "FROM User u " +
//            "WHERE " +
//            "lower(u.username) = :#{#user.username.toLowerCase()} OR " +
//            "lower(u.lastName) = :#{#user.lastName.toLowerCase()} AND u.firstName = :#{#user.firstName} OR " +
//            "lower(u.email) = :#{#user.email.toLowerCase()} OR " +
//            "u.phoneNumber = :#{#user.phoneNumber}")
}
