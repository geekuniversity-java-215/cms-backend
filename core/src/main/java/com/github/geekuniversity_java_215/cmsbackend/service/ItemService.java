package com.github.geekuniversity_java_215.cmsbackend.service;

import com.github.geekuniversity_java_215.cmsbackend.entites.Item;
import com.github.geekuniversity_java_215.cmsbackend.repository.ItemRepository;
import com.github.geekuniversity_java_215.cmsbackend.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ItemService {


    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }


    public Optional<Item> findById(Long id) {
        return itemRepository.findById(id);
    }

    public List<Item> findAllById(List<Long> listId) {

        return itemRepository.findAllById(listId);
    }

    public List<Item> findAll(Specification<Item> spec) {
        return itemRepository.findAll(spec);
    }

    public Item save(Item item) {

        return itemRepository.save(item);
    }

    public void delete(Item item) {

        itemRepository.delete(item);
    }

    public Page<Item> findAll(Specification<Item> spec, PageRequest pageable) {

        return itemRepository.findAll(spec, pageable);
    }
}
