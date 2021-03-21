package com.orderme.ordermebackend.service.impl;

import com.orderme.ordermebackend.model.dto.GoodsDto;
import com.orderme.ordermebackend.model.dtomappers.GoodsMapper;
import com.orderme.ordermebackend.model.entity.Goods;
import com.orderme.ordermebackend.model.entity.GoodsType;
import com.orderme.ordermebackend.repository.GoodsRepository;
import com.orderme.ordermebackend.repository.GoodsTypeRepository;
import com.orderme.ordermebackend.service.GoodsService;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.server.MethodNotAllowedException;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
public class GoodsServiceImpl implements GoodsService {

    private final GoodsRepository goodsRepository;

    private final GoodsTypeRepository goodsTypeRepository;

    private final GoodsMapper goodsMapper;

    public GoodsServiceImpl(GoodsRepository goodsRepository, GoodsTypeRepository goodsTypeRepository, GoodsMapper goodsMapper) {
        this.goodsRepository = goodsRepository;
        this.goodsTypeRepository = goodsTypeRepository;
        this.goodsMapper = goodsMapper;
    }

    @Override
    public Page<Goods> getAllByPageable(Pageable pageable) {
        return goodsRepository.findAll(pageable);
    }

    @Override
    public Goods getById(UUID goodsId) {
        return goodsRepository.findById(goodsId).orElseThrow(
                () -> new EntityNotFoundException("Couldn't find goods with the given id: " + goodsId));
    }

    @Override
    @Transactional
    public Page<Goods> getByGoodsTypeId(int goodsTypeId, Pageable pageable) {
        GoodsType goodsTypeRef = goodsTypeRepository.getOne(goodsTypeId);
        return goodsRepository.findAllByGoodsType(goodsTypeRef, pageable);
    }

    @Override
    @Transactional
    public Goods create(GoodsDto goodsDto) {
        GoodsType goodsTypeRef = goodsTypeRepository.getOne(goodsDto.getGoodsTypeId());
        Goods goodsToSave = Goods.builder()
                .title(goodsDto.getTitle())
                .description(goodsDto.getDescription())
                .oldPrice(goodsDto.getOldPrice())
                .actualPrice(goodsDto.getActualPrice())
                .goodsType(goodsTypeRef)
                .imageLink(goodsDto.getImageLink())
                .build();
        return goodsRepository.save(goodsToSave);
    }

    @Override
    @Transactional
    public Goods patch(GoodsDto goodsDto, UUID id) {
        Optional<Goods> target = goodsRepository.findById(id);
        Goods goodsToPatch = target.orElseThrow(() -> new EntityNotFoundException("Couldn't find the goods entity to update with id: " + id));
        goodsMapper.updateGoodsFromDto(goodsDto, goodsToPatch);
        return goodsRepository.save(goodsToPatch);
    }

    @Override
    public void delete(UUID id) {
        goodsRepository.deleteById(id);
    }


    @Override
    public Collection<Goods> getAllNotPaginated() {
        throw new NotImplementedException("This method is not implemented. Please, use the paginated analogue");
    }
}
