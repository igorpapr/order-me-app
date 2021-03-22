package com.orderme.ordermebackend.service.base;

import com.orderme.ordermebackend.model.dto.AbstractDto;
import com.orderme.ordermebackend.model.entity.AbstractEntity;

public interface DefaultCrudService<Id, E extends AbstractEntity<Id>, D extends AbstractDto> {
    E create(D dto);

    E getById(Id id);

    E patch(D dto, Id id);

    void delete(Id id);


}
