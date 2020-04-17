package com.github.geekuniversity_java_215.cmsbackend.repository;

import com.github.geekuniversity_java_215.cmsbackend.entites.Item;
import com.github.geekuniversity_java_215.cmsbackend.repository.base.CustomRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ItemRepository extends CustomRepository<Item, Long>, JpaSpecificationExecutor<Item> {
}
