package com.orderme.ordermebackend.service.impl;

import com.orderme.ordermebackend.model.dto.GoodsAvailabilityDto;
import com.orderme.ordermebackend.model.dtomappers.GoodsAvailabilityMapper;
import com.orderme.ordermebackend.model.entity.*;
import com.orderme.ordermebackend.repository.GoodsAvailabilityRepository;
import com.orderme.ordermebackend.repository.GoodsRepository;
import com.orderme.ordermebackend.repository.ShopRepository;
import com.orderme.ordermebackend.service.GoodsAvailabilityService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class GoodsAvailablityServiceImpl implements GoodsAvailabilityService {

    private final GoodsRepository goodsRepository;

    private final GoodsAvailabilityRepository goodsAvailabilityRepository;

    private final ShopRepository shopRepository;

    private final GoodsAvailabilityMapper goodsAvailabilityMapper;

    public GoodsAvailablityServiceImpl(GoodsRepository goodsRepository,
                                       GoodsAvailabilityRepository goodsAvailabilityRepository,
                                       ShopRepository shopRepository,
                                       GoodsAvailabilityMapper goodsAvailabilityMapper) {
        this.goodsRepository = goodsRepository;
        this.goodsAvailabilityRepository = goodsAvailabilityRepository;
        this.shopRepository = shopRepository;
        this.goodsAvailabilityMapper = goodsAvailabilityMapper;
    }

    @Override
    public GoodsAvailability getByShopIdAndGoodsId(Integer shopId, UUID goodsId) {
        return goodsAvailabilityRepository.findById(new GoodsAvailabilitiesKey(shopId, goodsId))
                .orElseThrow(() -> new EntityNotFoundException("Couldn't find goods availability with shopId: " + shopId + " and goodsId: " + goodsId));
    }

    @Override
    @Transactional
    public GoodsAvailability createByShopIdAndGoodsId(GoodsAvailabilityDto dto, Integer shopId, UUID goodsId) {
        GoodsAvailability goodsAvailability;
        GoodsAvailabilitiesKey id = new GoodsAvailabilitiesKey(shopId, goodsId);

        if (!goodsAvailabilityRepository.existsById(id)) {
            Goods goodsRef = goodsRepository.getOne(goodsId);
            Shop shopRef = shopRepository.getOne(shopId);
            goodsAvailability = GoodsAvailability.builder()
                        .goodsAvailabilitiesId(id)
                        .amount(dto.getAmount())
                        .goods(goodsRef)
                        .shop(shopRef)
                        .build();
        } else {
            throw new EntityExistsException("The goods availability with shopId: " + shopId +
                    " and goodsId: " + goodsId + " already exists.");
        }
        return goodsAvailabilityRepository.save(goodsAvailability);
    }

    @Override
    @Transactional
    public GoodsAvailability patchByShopIdAndGoodsId(GoodsAvailabilityDto dto, Integer shopId, UUID goodsId) {
        Optional<GoodsAvailability> target =
                goodsAvailabilityRepository.findById(new GoodsAvailabilitiesKey(shopId, goodsId));
        GoodsAvailability goodsAvailabilityToPatch =
                target.orElseThrow(() -> new EntityNotFoundException(
                                "Couldn't find the goods availability entity to update with shopId: " + shopId
                                        + " and goodsId: " + goodsId));
        goodsAvailabilityMapper.updateGoodsAvailabilityFromDto(dto, goodsAvailabilityToPatch);
        return goodsAvailabilityRepository.save(goodsAvailabilityToPatch);
    }

    @Override
    public void deleteByShopIdAndGoodsId(Integer shopId, UUID goodsId) {
        goodsAvailabilityRepository.deleteById(new GoodsAvailabilitiesKey(shopId, goodsId));
    }

}
