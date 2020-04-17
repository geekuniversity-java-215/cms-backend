package com.github.geekuniversity_java_215.cmsbackend.repository.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@NoRepositoryBean
public interface CustomRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

    @Transactional
    void refresh(T t);

//    @Transactional
//    T merge(T t);
}