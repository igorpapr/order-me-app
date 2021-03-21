package com.orderme.ordermebackend.service;

import com.orderme.ordermebackend.model.dto.AbstractDto;
import com.orderme.ordermebackend.model.entity.AbstractEntity;

import java.util.Collection;

public interface CrudService<Id, E extends AbstractEntity<Id>, D extends AbstractDto> {

    Collection<E> getAllNotPaginated();

    E create(D dto);

    E getById(Id id);

    E patch(D dto, Id id);

    void delete(Id id);

}
