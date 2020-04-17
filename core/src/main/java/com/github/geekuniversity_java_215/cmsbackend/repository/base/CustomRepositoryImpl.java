package com.github.geekuniversity_java_215.cmsbackend.repository.base;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.io.Serializable;


// делаем нашу реализацию репо-интерфейса  CustomRepository базовым для Spring Data JPA,
// Spring будет генерить для него методы доступа(Find.One.By.Parent.Address и т.д.)
// См мануал по @NoRepositoryBean, @EnableJpaRepositories
// (providing an extended base interface for all repositories
// in combination with a custom repository base class)
// Do not forget to put @NoRepositoryBean on interface CustomRepository too or won't work

@NoRepositoryBean
public class CustomRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID>
    implements CustomRepository<T, ID> {

    private final EntityManager entityManager;

    public CustomRepositoryImpl(JpaEntityInformation entityInformation, EntityManager entityManager) {
        //noinspection unchecked
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void refresh(T t) {

        entityManager.refresh(t);
    }
}



