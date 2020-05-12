package com.github.geekuniversity_java_215.cmsbackend.core.repositories;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.ExchangeRate;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyConverterRepository extends CustomRepository<ExchangeRate, Long>  {
}
