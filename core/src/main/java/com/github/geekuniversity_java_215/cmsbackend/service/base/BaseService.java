package com.github.geekuniversity_java_215.cmsbackend.service.base;

import com.github.geekuniversity_java_215.cmsbackend.repository.base.CustomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public abstract class BaseService<T> {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final CustomRepository<T, Long> baseRepository;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    public BaseService(CustomRepository<T, Long> baseRepository) {
        this.baseRepository = baseRepository;
    }


    public Optional<T> findById(Long id) {
        return baseRepository.findById(id);
    }

    public List<T> findAllById(List<Long> listId) {

        return baseRepository.findAllById(listId);
    }

    public List<T> findAll(Specification<T> spec) {
        return baseRepository.findAll(spec);
    }

    public T save(T t) {

        return baseRepository.save(t);
    }

    public void delete(T t) {

        baseRepository.delete(t);
    }

    public Page<T> findAll(Specification<T> spec, PageRequest pageable) {

        return baseRepository.findAll(spec, pageable);
    }
}
