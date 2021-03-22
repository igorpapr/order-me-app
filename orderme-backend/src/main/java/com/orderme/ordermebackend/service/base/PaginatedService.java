package com.orderme.ordermebackend.service.base;

import com.orderme.ordermebackend.model.entity.AbstractEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaginatedService<Id, E extends AbstractEntity<Id>> {

    Page<E> getAllByPageable(Pageable pageable);

}
