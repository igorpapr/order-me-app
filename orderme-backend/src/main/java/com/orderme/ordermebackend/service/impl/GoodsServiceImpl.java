package com.orderme.ordermebackend.service.impl;

import com.orderme.ordermebackend.model.dto.GoodsDto;
import com.orderme.ordermebackend.model.dtomappers.GoodsMapper;
import com.orderme.ordermebackend.model.entity.Goods;
import com.orderme.ordermebackend.model.entity.GoodsType;
import com.orderme.ordermebackend.repository.GoodsRepository;
import com.orderme.ordermebackend.repository.GoodsTypeRepository;
import com.orderme.ordermebackend.service.GoodsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public Page<Goods> getAllByPageable(int shopId, Pageable pageable) {
        Page<Goods> goodsPage = goodsRepository.findAll(pageable);
        filterGoodsAvailabilitiesByShop(goodsPage, shopId);
        return goodsPage;
    }

    @Override
    public Goods getByIdAndShopId(Integer shopId, UUID goodsId) {
        Goods res = goodsRepository.findById(goodsId).orElseThrow(
                () -> new EntityNotFoundException("Couldn't find goods with the given id: " + goodsId));
        filterGoodsAvailabilitiesByShop(res, shopId);
        return res;
    }

    @Override
    public Goods getById(UUID goodsId) {
        return goodsRepository.findById(goodsId).orElseThrow(
                () -> new EntityNotFoundException("Couldn't find goods with the given id: " + goodsId));
    }

    @Override
    @Transactional
    public Page<Goods> getByGoodsTypeId(Integer shopId, int goodsTypeId, Pageable pageable) {
        GoodsType goodsTypeRef = goodsTypeRepository.getOne(goodsTypeId);
        Page<Goods> goodsPage = goodsRepository.findAllByGoodsType(goodsTypeRef, pageable);
        if (shopId != null) {
            filterGoodsAvailabilitiesByShop(goodsPage, shopId);
        }
        return goodsPage;
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

    /**
     * This method is used because goods availabilities can be absent at the particular moment of new goods' "lifetime"
     * due to business logic. That's why the goods is not fetched with
     * @param goodsPage the page of goods to be filtered
     * @param shopId    the shop id to filter goods availabilities by
     */
    private void filterGoodsAvailabilitiesByShop(Page<Goods> goodsPage, int shopId) {
        goodsPage.forEach(goods -> filterGoodsAvailabilitiesByShop(goods, shopId));
    }

    /**
     * This method is used because goods availabilities can be absent at the particular moment of new goods' "lifetime"
     * due to business logic. That's why the goods is not fetched with
     * @param goods  the goods to be filtered
     * @param shopId the shop id to filter goods availabilities by
     */
    private void filterGoodsAvailabilitiesByShop(Goods goods, int shopId) {
        goods.setGoodsAvailabilities(
                goods.getGoodsAvailabilities().stream().filter(
                        goodsAvailability -> goodsAvailability.getGoodsAvailabilitiesId()
                                .getShopId().equals(shopId))
                        .collect(Collectors.toSet()));
    }

}
