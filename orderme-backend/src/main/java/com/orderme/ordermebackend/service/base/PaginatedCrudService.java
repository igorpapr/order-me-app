package com.orderme.ordermebackend.service.base;

import com.orderme.ordermebackend.model.dto.AbstractDto;
import com.orderme.ordermebackend.model.entity.AbstractEntity;

public interface PaginatedCrudService<Id, E extends AbstractEntity<Id>, D extends AbstractDto>
        extends PaginatedService<Id, E>, DefaultCrudService<Id, E, D> {
}
