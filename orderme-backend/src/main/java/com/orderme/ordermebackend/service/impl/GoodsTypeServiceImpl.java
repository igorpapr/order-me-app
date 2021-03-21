package com.orderme.ordermebackend.service.impl;

import com.orderme.ordermebackend.model.dto.GoodsTypeDto;
import com.orderme.ordermebackend.model.dtomappers.GoodsTypeMapper;
import com.orderme.ordermebackend.model.entity.GoodsType;
import com.orderme.ordermebackend.repository.GoodsTypeRepository;
import com.orderme.ordermebackend.service.GoodsTypeService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.Optional;

@Service
public class GoodsTypeServiceImpl implements GoodsTypeService {

    private final GoodsTypeRepository goodsTypeRepository;

    private final GoodsTypeMapper goodsTypeMapper;

    public GoodsTypeServiceImpl(GoodsTypeRepository goodsTypeRepository,
                                GoodsTypeMapper goodsTypeMapper) {
        this.goodsTypeRepository = goodsTypeRepository;
        this.goodsTypeMapper = goodsTypeMapper;
    }

    @Override
    public Collection<GoodsType> getAllNotPaginated() {
        return goodsTypeRepository.findAll();
    }

    @Override
    public GoodsType create(GoodsTypeDto dto) {
        GoodsType goodsType = GoodsType.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .build();
        return goodsTypeRepository.save(goodsType);
    }

    @Override
    public GoodsType getById(Integer id) {
        return goodsTypeRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Couldn't find goods type with id: " + id));
    }

    @Override
    public GoodsType patch(GoodsTypeDto dto, Integer id) {
        Optional<GoodsType> target = goodsTypeRepository.findById(id);
        GoodsType goodsTypeToPatch = target.orElseThrow(
                () -> new EntityNotFoundException("Couldn't find the goods type entity to update with id: " + id));
        goodsTypeMapper.updateGoodsTypeFromDto(dto, goodsTypeToPatch);
        return goodsTypeRepository.save(goodsTypeToPatch);
    }

    @Override
    public void delete(Integer id) {
        goodsTypeRepository.deleteById(id);
    }
}
