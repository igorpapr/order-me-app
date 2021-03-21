package com.orderme.ordermebackend.service;

import com.orderme.ordermebackend.model.entity.AbstractEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaginatedService<Id, T extends AbstractEntity<Id>> {

    Page<T> getAllByPageable(Pageable pageable);

}
