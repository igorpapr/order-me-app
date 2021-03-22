package com.orderme.ordermebackend.repository;

import com.orderme.ordermebackend.model.entity.GoodsAvailabilitiesKey;
import com.orderme.ordermebackend.model.entity.GoodsAvailability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface GoodsAvailabilityRepository extends JpaRepository<GoodsAvailability, GoodsAvailabilitiesKey> {

    Optional<GoodsAvailability> findByGoodsAvailabilitiesIdShopIdAndGoodsAvailabilitiesIdGoodsId(Integer shopId, UUID goodsId);

    Optional<GoodsAvailability> findByGoodsAvailabilitiesId(GoodsAvailabilitiesKey id);

}
