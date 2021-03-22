package com.orderme.ordermebackend.service.base;

import com.orderme.ordermebackend.model.entity.AbstractEntity;

import java.util.Collection;

public interface NonPaginatedService<Id, E extends AbstractEntity<Id>> {

    Collection<E> getAllNotPaginated();

}
