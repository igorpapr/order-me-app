package com.orderme.ordermebackend.service.validation;

import com.orderme.ordermebackend.model.dto.AbstractDto;

public interface DtoValidationService<DTO extends AbstractDto > {

    void validateCreate(DTO dto);

    void validatePatch(DTO dto);
//    void validateHasOwnId(DTO dto);

}
