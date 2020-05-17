package com.github.geekuniversity_java_215.cmsbackend.core.repositories;

import com.github.geekuniversity_java_215.cmsbackend.core.data.enums.CurrencyCode;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.ExchangeRate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.Optional;

@Repository
public interface CurrencyConverterRepository extends CustomRepository<ExchangeRate, Long>  {

    Optional<ExchangeRate> findOneByCurrencyCodeAndDate(CurrencyCode code, Date date);

}
