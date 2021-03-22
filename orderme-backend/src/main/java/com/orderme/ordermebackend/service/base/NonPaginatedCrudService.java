package com.orderme.ordermebackend.service.base;

import com.orderme.ordermebackend.model.dto.AbstractDto;
import com.orderme.ordermebackend.model.entity.AbstractEntity;

public interface NonPaginatedCrudService<Id, E extends AbstractEntity<Id>, D extends AbstractDto>
        extends NonPaginatedService<Id, E>, DefaultCrudService<Id, E, D> {

}
