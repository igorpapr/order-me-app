package com.orderme.ordermebackend.service.validation;

import com.orderme.ordermebackend.model.dto.AbstractDto;

public abstract class AbstractDtoValidationService<DTO extends AbstractDto> implements DtoValidationService<DTO> {

    protected abstract void failWithMissingParameter(String field);

    protected void failWithMissingParameter(String field, Class<DTO> clazz) {
        throw new IllegalArgumentException("Missing parameter: " + field + " in the given entity: " + clazz.getSimpleName());
    }

//    protected void failWithMissingId(Class<DTO> clazz) {
//        throw new IllegalArgumentException("Missing an id field value which is required for this operation for entity: "
//                + clazz.getSimpleName());
//    }

}
