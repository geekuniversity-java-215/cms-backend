package com.github.geekuniversity_java_215.cmsbackend.core.services.base;

import com.github.geekuniversity_java_215.cmsbackend.core.repositories.CustomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public abstract class BaseRepoAccessService<T> {


    private final CustomRepository<T, Long> baseRepository;

//    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
//    @Autowired
//    public BaseService(CustomRepository<T, Long> baseRepository) {
//        this.baseRepository = baseRepository;
//    }


    public Optional<T> findById(Long id) {
        return baseRepository.findById(id);
    }

    public List<T> findAllById(List<Long> listId) {

        return baseRepository.findAllById(listId);
    }

    public List<T> findAll(Specification<T> spec) {
        return baseRepository.findAll(spec);
    }

    public Page<T> findAll(Specification<T> spec, PageRequest pageable) {
        return baseRepository.findAll(spec, pageable);
    }

    public T save(T t) {
        return baseRepository.save(t);
    }

    public List<T> saveAll(Iterable<T> list) {
        return baseRepository.saveAll(list);
    }

    public void delete(T t) {
        baseRepository.delete(t);
    }


}
