package com.orderme.ordermebackend.service.impl;

import com.orderme.ordermebackend.model.dto.ShopDto;
import com.orderme.ordermebackend.model.dtomappers.ShopMapper;
import com.orderme.ordermebackend.model.entity.Shop;
import com.orderme.ordermebackend.repository.ShopRepository;
import com.orderme.ordermebackend.service.ShopService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.Optional;

@Service
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;

    private final ShopMapper shopMapper;

    public ShopServiceImpl(ShopRepository shopRepository, ShopMapper shopMapper) {
        this.shopRepository = shopRepository;
        this.shopMapper = shopMapper;
    }

    @Override
    public Collection<Shop> getAllNotPaginated() {
        return shopRepository.findAll();
    }

    @Override
    public Shop create(ShopDto dto) {
        Shop shopToCreate = Shop.builder()
                .title(dto.getTitle())
                .address(dto.getAddress())
                .build();
        return shopRepository.save(shopToCreate);
    }

    @Override
    public Shop getById(Integer id) {
        return shopRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Couldn't find shop with id: " + id));
    }

    @Override
    public Shop patch(ShopDto dto, Integer id) {
        Optional<Shop> target = shopRepository.findById(id);
        Shop shopToPatch = target.orElseThrow(
                () -> new EntityNotFoundException("Couldn't find the shop to update with id: " + id));
        shopMapper.updateShopFromDto(dto, shopToPatch);
        return shopRepository.save(shopToPatch);
    }

    @Override
    public void delete(Integer id) {
        shopRepository.deleteById(id);
    }
}
